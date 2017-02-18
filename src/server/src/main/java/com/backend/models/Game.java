package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.GameTypeEnum;
import com.backend.models.yearly.GameStateYearly;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;
import com.framework.models.Essentials;
import com.frontend.controllers.GameRefreshController;
import com.google.common.collect.Lists;

public class Game implements Comparable<Game>
{
	public final ObjectId 					_id;
	public final int						gameNumber;
	public final String						playoffGroup;
	public final DateTime					scheduledTime;
	public final GameTypeEnum				gameType;
	public final ArrayList<School> 			blueTeam;
	public final ArrayList<School> 			yellowTeam;
	private final ArrayList<GameEvent> 		gameEvents;
	public final boolean					isLive;

	public final static Duration END_LIVE_DELAY	= new Duration(4 * 60 * 1000);
	
	public Game(
			@JsonProperty("_id")					ObjectId 					_gameId,
			@JsonProperty("gameNumber")				int 						_gameNumber,
			@JsonProperty("playoffGroup")			String 						_playoffGroup,
			@JsonProperty("scheduledTime")			DateTime					_scheduledTime,
			@JsonProperty("gameType")				GameTypeEnum				_gameType,
			@JsonProperty("blueTeam")				ArrayList<School> 			_blueTeam,
			@JsonProperty("yellowTeam")				ArrayList<School>			_yellowTeam,
			@JsonProperty("gameEvents")				ArrayList<GameEvent>		_gameEvents,
			@JsonProperty("isLive")					boolean						_isLive
			)
	{
		_id 				= _gameId;
		gameNumber			= _gameNumber;
		playoffGroup		= _playoffGroup;
		scheduledTime		= _scheduledTime;
		gameType			= _gameType;
		yellowTeam 			= _yellowTeam;
		blueTeam 			= _blueTeam;
		gameEvents 			= _gameEvents;
		isLive				= _isLive;
	}
	
	public ArrayList<School> getSchools()
	{
		ArrayList<School> schools = new ArrayList<School>(blueTeam);
		schools.addAll(yellowTeam);
		
		return schools;
	}
	
	public ArrayList<GameState> getGameStates()
	{
		ArrayList<GameState> gameStates = new ArrayList<GameState>();
		GameState previousGameState = null;
		
		for( GameEvent gameEvent : gameEvents )
		{
			GameState actualGameState = new GameStateYearly(previousGameState, gameEvent);
			gameStates.add(actualGameState);
			previousGameState = actualGameState;
		}
		
		return gameStates;
	}
	
	public ArrayList<GameEvent> getGameEvents()
	{
		return new ArrayList<GameEvent>(gameEvents);
	}
	
	public boolean addGameEvent(GameEvent gameEvent)
	{
		return addGameEvent(gameEvents.size(), gameEvent);
	}
	
	public boolean containsStartGameEvent()
	{
		return gameEvents.size() > 0 && gameEvents.get(0).getGameEventEnum() == GameEventEnum.START_GAME;
	}
	
	public boolean containsEndGameEvent()
	{
		return gameEvents.size() > 0 && gameEvents.get(gameEvents.size() - 1).getGameEventEnum() == GameEventEnum.END_GAME;
	}
	
	public boolean addGameEvent(int pos, GameEvent gameEvent)
	{
		boolean added = false;
		switch(gameEvent.getGameEventEnum())
		{
		case START_GAME:
			if( pos == 0 && gameEvents.size() == 0 )
			{
				gameEvents.add(pos, gameEvent);
				added = true;
			}
			break;
		case END_GAME:
			if( pos == gameEvents.size() && !containsEndGameEvent() )
			{
				gameEvents.add(pos, gameEvent);
				createEndLiveCallback(_id, DatabaseType.PRODUCTION);
				added = true;
			}
			break;
		default:
			if( pos > 0 && pos < gameEvents.size()
				// Add game event to last element only if there's no END_GAME already present.
				|| pos > 0 && pos == gameEvents.size() && !containsEndGameEvent())
			{
				gameEvents.add(pos, gameEvent);
				added = true;
			}
			break;
		}
		
		return added;
	}
	
	
	static ScheduledExecutorService liveScheduledExecutorService = null;

	public static void createEndLiveCallback(final ObjectId gameId, final Database.DatabaseType databaseType)
	{
		if(liveScheduledExecutorService != null)
		{
			liveScheduledExecutorService.shutdownNow();
		}
		liveScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		liveScheduledExecutorService.schedule(
			new Callable<Boolean>() 
			{
				public Boolean call() throws Exception 
			    {
					Database database = new Database(databaseType);
					try
					{
						Game.setLiveGame(database, gameId, false);
						GameRefreshController.setRefreshNeeded(true);
					}
					finally
					{
						database.close();
					}
					
					return Boolean.TRUE;
			    }
			},
			END_LIVE_DELAY.getStandardSeconds(),
		    TimeUnit.SECONDS);

		liveScheduledExecutorService.shutdown();
	}
	
	static ScheduledExecutorService endScheduledExecutorService = null;
	
	public static void createEndGameCallback(final ObjectId gameId)
	{
		if(endScheduledExecutorService != null)
		{
			endScheduledExecutorService.shutdownNow();
		}
		if(liveScheduledExecutorService != null)
		{
			liveScheduledExecutorService.shutdownNow();
			liveScheduledExecutorService = null;
		}
		endScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

		endScheduledExecutorService.schedule(
			new Callable<GameEvent>() 
			{
				public GameEvent call() throws Exception 
			    {
					EndGameEvent endGameEvent = new EndGameEvent(DateTime.now());
					Database database = new Database(Database.DatabaseType.PRODUCTION);
					try
					{
						Game game = database.findOne(Game.class, gameId);
						game.addGameEvent(endGameEvent);
						GameRefreshController.setRefreshNeeded(true);
						database.save(game);
					}
					finally
					{
						database.close();
					}
					return endGameEvent;
			    }
			},
		    getGameLength().getStandardSeconds(),
		    TimeUnit.SECONDS);

		endScheduledExecutorService.shutdown();
	}
	
	public void removeGameEvent(int pos)
	{
		// Accept to remove start event only
		// if it's the only event present.
		if(pos > 0 && pos < gameEvents.size()
		|| pos == 0 && gameEvents.size() == 1 )
		{
			gameEvents.remove(pos);
		}
	}
	
	public boolean hasMisconductPenalty(School school)
	{
		boolean isBlueTeam 		= blueTeam.contains(school);
		boolean isYellowTeam 	= yellowTeam.contains(school);
		
		if(!isBlueTeam && !isYellowTeam)
		{
			// The school is not in this game
			return false;
		}
		
		// This might be a performance bottleneck ...
		ArrayList<GameState> gameStates = getGameStates();
		if(gameStates.size() == 0)
			return false;
		
		GameState gameState = gameStates.get(gameStates.size() - 1);
		
		// A 0 score is given if we give a misconduct penalty.
		if(gameState.misconductPenalties.contains(school))
		{
			return true;
		}
		
		return false;
	}
	
	public int getScore(School school, GameState gameState)
	{
		boolean isBlueTeam 		= blueTeam.contains(school);
		boolean isYellowTeam 	= yellowTeam.contains(school);
		
		if(!isBlueTeam && !isYellowTeam)
		{
			// The school is not in this game
			return 0;
		}
		
		if(gameState == null)
		{
			// This might be a performance bottleneck ...
			ArrayList<GameState> gameStates = getGameStates();
			if(gameStates.size() == 0)
				return 0;
			
			gameState = gameStates.get(gameStates.size() - 1);
		}
		
		// A 0 score is given if we give a misconduct penalty.
		if(gameState.misconductPenalties.contains(school))
		{
			return 0;
		}
		
		// A 0 score is given if the school couldn't score any points.
		if(gameState.didNotScore.contains(school))
		{
			return 0;
		}

		int score = 0;
		if( isBlueTeam )
		{
			score = gameState.blueScore + gameState.pointModifierBlue;
		}
		else if( isYellowTeam )
		{
			score = gameState.yellowScore + gameState.pointModifierYellow;
		}
		
		for(SchoolInteger penalty : gameState.penalties)
		{
			// Misconduct penalty is calculated globally
			if(penalty.equals(school))
			{
				score -= penalty.integer;
			}
		}
		
		float penaltyPercentageTotal = 0;
		for(SchoolFloat penaltyPercentage : gameState.penaltiesPercentage)
		{
			// Misconduct penalty is calculated globally
			if(penaltyPercentage.equals(school))
			{
				penaltyPercentageTotal += penaltyPercentage.floatValue;
			}
		}
		score = (int)Math.floor(score - (score * penaltyPercentageTotal));
		
		return score;
	}
	
	// Score does not take into account misconduct penalties since they are global penalties.
	public int getScore(School school)
	{
		return getScore(school, null);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		
		if(o == this)
			return true;
		
		if(!(o instanceof Game))
			return false;
		
		return _id.equals(((Game)o)._id);
	}
	
	@Override
	public int compareTo(Game o) 
	{
		return this.gameNumber - o.gameNumber;
	}
	
	// This functions is slow (400ms) on laptop with 105 games.
	public static ArrayList<Game> getGames(Essentials essentials)
	{
		ArrayList<Game> games = Lists.newArrayList(essentials.database.find(Game.class, "{ }"));
		Collections.sort(games);
		return games;
	}
	
	public static Game getLiveGame(Database database)
	{
		return database.findOne(Game.class, "{ isLive : true }");
	}
	
	public static Game setLiveGame(Database database, ObjectId gameId, boolean isLive)
	{
		if(isLive)
		{
			// Cannot have 2 simultaneous live game.
			Game currentLive = getLiveGame(database);
			if(currentLive != null)
			{
				Game nonLive = setLiveGame(currentLive, false);
				database.save(nonLive);
			}
		}

		Game game = database.findOne(Game.class, gameId);
		if(game != null)
		{
			Game newLive = setLiveGame(game, isLive);
			database.save(newLive);
			return newLive;
		}
	
		return null;
	}
	
	private static Game setLiveGame(Game game, boolean isLive)
	{
		return new Game(game._id, game.gameNumber, game.playoffGroup, game.scheduledTime, game.gameType, game.blueTeam, game.yellowTeam, game.gameEvents, isLive);
	}
	
	public Game getInitialState()
	{
		return new Game(_id, gameNumber, playoffGroup, scheduledTime, gameType, blueTeam, yellowTeam, new ArrayList<GameEvent>(), isLive);
	}
	
	public static Duration getGameLength()
	{
		return new Duration(5 * 60 * 1000);
	}
	
	public DateTime getTimeInGame(GameState gameState)
	{
		if(gameEvents.size() == 0)
			return new DateTime(0);
		
		Duration timeSinceStart = new Duration(gameEvents.get(0).getTime(), gameState.lastGameEvent.getTime());
		long timeInGameMillis = getGameLength().getMillis() - timeSinceStart.getMillis();
		timeInGameMillis = timeInGameMillis > 0 ? timeInGameMillis : 0;
		return new DateTime(timeInGameMillis);
	}
}
