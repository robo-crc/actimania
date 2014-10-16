package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Game 
{
	public final ObjectId 					_id;
	public final GameTypeEnum				gameType;
	public final ArrayList<School> 			blueTeam;
	public final ArrayList<School> 			yellowTeam;
	public final ArrayList<GameEvent> 		gameEvents;
	
	public final ArrayList<SchoolPenalty>	penalties;
	public final ArrayList<School>			misconductPenalties;

	public Game(
			@JsonProperty("_id")					ObjectId 					_gameId,
			@JsonProperty("gameType")				GameTypeEnum				_gameType,
			@JsonProperty("blueTeam")				ArrayList<School> 			_blueTeam,
			@JsonProperty("yellowTeam")				ArrayList<School>			_yellowTeam,
			@JsonProperty("gameEvents")				ArrayList<GameEvent>		_gameEvents,
			@JsonProperty("penalties")				ArrayList<SchoolPenalty>	_penalties,
			@JsonProperty("misconductPenalties")	ArrayList<School>			_misconductPenalties
			)
	{
		_id 				= _gameId;
		gameType			= _gameType;
		yellowTeam 			= _yellowTeam;
		blueTeam 			= _blueTeam;
		gameEvents 			= _gameEvents;
		penalties 			= _penalties;
		misconductPenalties = _misconductPenalties;
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
			GameState actualGameState = new GameState(previousGameState, gameEvent);
			gameStates.add(actualGameState);
			previousGameState = actualGameState;
		}
		
		return gameStates;
	}
	
	// Score does not take into account misconduct penalties since they are global penalties.
	public int getScore(School school)
	{
		boolean isBlueTeam 		= blueTeam.contains(school);
		boolean isYellowTeam 	= yellowTeam.contains(school);
		
		if(!isBlueTeam && !isYellowTeam)
		{
			// The school is not in this game
			return 0;
		}
		
		// This might be a performance bottleneck ...
		ArrayList<GameState> gameStates = getGameStates();
		GameState gameState = gameStates.get(gameStates.size() - 1);
		
		int score = 0;
		if( isBlueTeam )
		{
			score = gameState.blueScore;
		}
		else if( isYellowTeam )
		{
			score = gameState.yellowScore;
		}
		
		for(SchoolPenalty penalty : penalties)
		{
			// Misconduct penalty is calculated globally
			if(penalty.school.equals(school))
			{
				score -= penalty.pointsDeduction;
			}
		}
		
		// A 0 score is given if we give a misconduct penalty.
		if(misconductPenalties.contains(school))
		{
			score = 0;
		}
		
		return score;
	}
}
