package com.backend.models.yearly;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.backend.models.GameState;
import com.backend.models.School;
import com.backend.models.SchoolFloat;
import com.backend.models.SchoolInteger;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameStateYearly extends GameState
{
	public final ScoreboardUpdateEvent currentScoreboard;
	
	@JsonIgnore
	public final int CYLINDER_GP = 30;

	@JsonIgnore
	public final int PRISM_GP = 10;
	
	@JsonIgnore
	public final int VSHAPE_GP = 20;
	
	public GameStateYearly(
			@JsonProperty("_id")					ObjectId 					_gameEventId,
			@JsonProperty("lastGameEvent")			GameEvent					_lastGameEvent,
			@JsonProperty("blueScore")				int 						_blueScore,
			@JsonProperty("yellowScore")			int 						_yellowScore,
			@JsonProperty("penalties")				ArrayList<SchoolInteger>	_penalties,
			@JsonProperty("penaltiesPercentage")	ArrayList<SchoolFloat>		_penaltiesPercentage,
			@JsonProperty("misconductPenalties")	ArrayList<School>			_misconductPenalties,
			@JsonProperty("didNotScore")			ArrayList<School>			_didNotScore,
			@JsonProperty("pointModifierBlue")		int							_pointModifierBlue,
			@JsonProperty("pointModifierYellow")	int							_pointModifierYellow,
			@JsonProperty("currentScoreboard")		ScoreboardUpdateEvent		_currentScoreboard
			)
	{
		super(_gameEventId, _lastGameEvent, _blueScore, _yellowScore, _penalties, _penaltiesPercentage, _misconductPenalties, _didNotScore, _pointModifierBlue, _pointModifierYellow);
		currentScoreboard = _currentScoreboard;
	}
	
	public GameStateYearly(GameState previousState, GameEvent gameEvent)
	{
		super(previousState, gameEvent);
		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			// Initialize the score board with a score board update event
			currentScoreboard = new ScoreboardUpdateEvent(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DateTime.now());
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
		
		int score = scoreboard.cylinderBlue * CYLINDER_GP + scoreboard.prismBlue * PRISM_GP + scoreboard.vShapeBlue * VSHAPE_GP * scoreboard.threeLevelBlue;
		float divisionUse = scoreboard.gameMultiplierYellow == 0 ? 0.0000001f : scoreboard.gameMultiplierYellow;
		float multiplierRatio = ((float)scoreboard.gameMultiplierBlue) / divisionUse;
		score *= Math.max(1, Math.min(multiplierRatio, 2));
		return score;
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

		int score = scoreboard.cylinderYellow * CYLINDER_GP + scoreboard.prismYellow * PRISM_GP + scoreboard.vShapeYellow * VSHAPE_GP * scoreboard.threeLevelYellow;
		float divisionUse = scoreboard.gameMultiplierBlue == 0 ? 0.0000001f : scoreboard.gameMultiplierBlue;
		float multiplierRatio = ((float)scoreboard.gameMultiplierYellow) / divisionUse;
		score *= Math.max(1, Math.min(multiplierRatio, 2));
		return score;
	}
}
