package com.backend.models.yearly;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.backend.models.GameState;
import com.backend.models.School;
import com.backend.models.SchoolInteger;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameStateYearly extends GameState
{
	public final ScoreboardUpdateEvent currentScoreboard;
	
	public GameStateYearly(
			@JsonProperty("_id")					ObjectId 					_gameEventId,
			@JsonProperty("lastGameEvent")			GameEvent					_lastGameEvent,
			@JsonProperty("blueScore")				int 						_blueScore,
			@JsonProperty("yellowScore")			int 						_yellowScore,
			@JsonProperty("penalties")				ArrayList<SchoolInteger>	_penalties,
			@JsonProperty("misconductPenalties")	ArrayList<School>			_misconductPenalties,
			@JsonProperty("didNotScore")			ArrayList<School>			_didNotScore,
			@JsonProperty("pointModifierBlue")		int							_pointModifierBlue,
			@JsonProperty("pointModifierYellow")	int							_pointModifierYellow,
			@JsonProperty("currentScoreboard")		ScoreboardUpdateEvent		_currentScoreboard
			)
	{
		super(_gameEventId, _lastGameEvent, _blueScore, _yellowScore, _penalties, _misconductPenalties, _didNotScore, _pointModifierBlue, _pointModifierYellow);
		currentScoreboard = _currentScoreboard;
	}
	
	public GameStateYearly(GameState previousState, GameEvent gameEvent)
	{
		super(previousState, gameEvent);
		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			currentScoreboard = null;
		}
		else if(gameEvent.getGameEventEnum() == GameEventEnum.SCOREBOARD_UPDATED)
		{
			currentScoreboard = (ScoreboardUpdateEvent)gameEvent;
		}
		else
		{
			currentScoreboard = ((GameStateYearly)previousState).currentScoreboard;
		}
	}
	
	public static Area[] InitializeField()
	{
		Area[] field = new Area[9];
		
		// Important to be sorted from biggest to smallest
		// For penalty calculations
		field[0] = new Area(100, 0);
		field[1] = new Area(40, 0);
		field[2] = new Area(30, 0);
		field[3] = new Area(20, 0);
		field[4] = new Area(20, 0);
		field[5] = new Area(10, 0);
		field[6] = new Area(10, 0);
		field[7] = new Area(5, 0);
		field[8] = new Area(5, 0);
		
		return field;
	}
	
	public static int GetScore(Area[] field, int spoolsUsed, int spoolsAllowed, boolean hasMultiplier)
	{
		int spoolsOverAllowed = spoolsUsed - spoolsAllowed;
		final int SPOOL_DISPENSED_VALUE = 50;
		
		int score = SPOOL_DISPENSED_VALUE * spoolsUsed;
		
		for(int i = 0; i < field.length; i++)
		{
			// Penalty for using too much spools
			// Value is sorted from biggest to smallest so 
			// the penalty is applied properly
			if(spoolsOverAllowed > 0)
			{
				spoolsOverAllowed--;
				score -= SPOOL_DISPENSED_VALUE;
			}
			else
			{
				score += field[i].spoolCount * field[i].value;
			}
		}
		
		if(hasMultiplier)
		{
			score *= 2;
		}
		
		return score;
	}

	@Override
	protected int GetBlueScore(GameState previousState, GameEvent gameEvent) 
	{
		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			return 0;
		}
		else if(gameEvent.getGameEventEnum() != GameEventEnum.SCOREBOARD_UPDATED)
		{
			return previousState.blueScore;
		}
		
		ScoreboardUpdateEvent scoreboard = (ScoreboardUpdateEvent) gameEvent;
		
		return GetScore(scoreboard.blueField, scoreboard.yellowDispenser1 + scoreboard.yellowDispenser2, scoreboard.blueTeamAllowedSpools, scoreboard.hasMultiplier == TeamEnum.BLUE);
	}

	@Override
	protected int GetYellowScore(GameState previousState, GameEvent gameEvent) 
	{
		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			return 0;
		}
		else if(gameEvent.getGameEventEnum() != GameEventEnum.SCOREBOARD_UPDATED)
		{
			return previousState.yellowScore;
		}
		
		ScoreboardUpdateEvent scoreboard = (ScoreboardUpdateEvent) gameEvent;
		
		return GetScore(scoreboard.yellowField, scoreboard.blueDispenser1 + scoreboard.blueDispenser2, scoreboard.yellowTeamAllowedSpools, scoreboard.hasMultiplier == TeamEnum.YELLOW);
	}
}
