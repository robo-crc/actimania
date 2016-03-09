package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.backend.models.GameEvent.DidNotScoreEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.MisconductPenaltyEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.SchoolPenaltyEvent;
import com.backend.models.GameEvent.ScoreboardUpdateEvent;
import com.backend.models.GameEvent.TeamPenaltyEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.yearly.TriangleStateEnum;
import com.backend.models.yearly.Hole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GameState 
{
	public final ObjectId 					_id;
	public final GameEvent					lastGameEvent;
	public final int 						blueScore;
	public final int 						yellowScore;
	
	public final ArrayList<SchoolInteger>	penalties;
	public final ArrayList<School>			misconductPenalties;
	public final ArrayList<School>			didNotScore;
	public final int						pointModifierBlue;
	public final int						pointModifierYellow;
	
	protected GameState(			
			ObjectId 					_gameEventId,
			GameEvent					_lastGameEvent,
			int 						_blueScore,
			int 						_yellowScore,
			ArrayList<SchoolInteger>	_penalties,
			ArrayList<School>			_misconductPenalties,
			ArrayList<School>			_didNotScore,
			int							_pointModifierBlue,
			int							_pointModifierYellow
			)
	{
		_id 				= _gameEventId;
		lastGameEvent		= _lastGameEvent;
		blueScore 			= _blueScore;
		yellowScore 		= _yellowScore;
		penalties 			= _penalties;
		misconductPenalties	= _misconductPenalties;
		didNotScore			= _didNotScore;
		pointModifierBlue	= _pointModifierBlue;
		pointModifierYellow	= _pointModifierYellow;
	}
	
	protected GameState(GameState previousState, GameEvent gameEvent)
	{
		_id = null;
		lastGameEvent = gameEvent;

		Hole[] localTriangleLeft = null;
		Hole[] localTriangleRight = null;
		
		int localBlueScore = 0;
		int localYellowScore = 0;
		
		int localModifierBlue = 0;
		int localModifierYellow = 0;
		
		ArrayList<SchoolInteger> 	localPenalties = null;
		ArrayList<School> 			localMisconductPenalties = null;
		ArrayList<School> 			localDidNotScore = null;
		
		if(gameEvent.getGameEventEnum() == GameEventEnum.START_GAME)
		{
			localPenalties = new ArrayList<SchoolInteger>();
			localMisconductPenalties = new ArrayList<School>();
			localDidNotScore = new ArrayList<School>();
		}
		else
		{
			localModifierBlue = previousState.pointModifierBlue;
			localModifierYellow = previousState.pointModifierYellow;
			
			localPenalties 				= new ArrayList<SchoolInteger>(previousState.penalties);
			localMisconductPenalties	= new ArrayList<School>(previousState.misconductPenalties);
			localDidNotScore			= new ArrayList<School>(previousState.didNotScore);

			if(gameEvent.getGameEventEnum() == GameEventEnum.POINT_MODIFIER)
			{
				PointModifierEvent pointModifierEvent = (PointModifierEvent) gameEvent;
				if(pointModifierEvent.team == TeamEnum.BLUE)
				{
					localModifierBlue += pointModifierEvent.points;
				}
				else if(pointModifierEvent.team == TeamEnum.YELLOW)
				{
					localModifierYellow += pointModifierEvent.points;
				}
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.SCHOOL_PENALTY)
			{
				SchoolPenaltyEvent schoolPenaltyEvent = (SchoolPenaltyEvent) gameEvent;
				localPenalties.add(new SchoolInteger(schoolPenaltyEvent.school, schoolPenaltyEvent.pointsDeduction));
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.TEAM_PENALTY)
			{
				TeamPenaltyEvent teamPenaltyEvent = (TeamPenaltyEvent) gameEvent;
				if(teamPenaltyEvent.team == TeamEnum.BLUE)
				{
					localModifierBlue -= teamPenaltyEvent.pointsDeduction;
				}
				else if(teamPenaltyEvent.team == TeamEnum.YELLOW)
				{
					localModifierYellow -= teamPenaltyEvent.pointsDeduction;
				}
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.MISCONDUCT_PENALTY)
			{
				MisconductPenaltyEvent misconductPenaltyEvent = (MisconductPenaltyEvent) gameEvent;
				localMisconductPenalties.add(misconductPenaltyEvent.school);
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.DID_NOT_SCORE)
			{
				DidNotScoreEvent didNotScoreEvent = (DidNotScoreEvent) gameEvent;
				localDidNotScore.add(didNotScoreEvent.school);
			}
			else if(gameEvent.getGameEventEnum() == GameEventEnum.END_GAME)
			{
				// Nothing to do for now.
			}
		}

		pointModifierBlue	= localModifierBlue;
		pointModifierYellow	= localModifierYellow;
		penalties			= localPenalties;
		misconductPenalties	= localMisconductPenalties;
		didNotScore			= localDidNotScore;
		
		blueScore 			= GetBlueScore(previousState, gameEvent);
		yellowScore			= GetYellowScore(previousState, gameEvent);
	}
	
	protected abstract int GetBlueScore(GameState previousState, GameEvent gameEvent);
	protected abstract int GetYellowScore(GameState previousState, GameEvent gameEvent);
}
