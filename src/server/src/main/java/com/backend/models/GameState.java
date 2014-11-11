package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.backend.models.GameEvent.ActuatorStateChangedEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.MisconductPenaltyEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.SchoolPenaltyEvent;
import com.backend.models.GameEvent.TargetHitEvent;
import com.backend.models.GameEvent.TeamPenaltyEvent;
import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.backend.models.enums.TeamEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameState 
{
	@JsonIgnore
	public static final int[] ACTUATOR_MULTIPLIERS = { 0, 1, 2, 5 };
	@JsonIgnore
	public static final int[] TARGET_VALUE = { 10, 20, 40 };
	
	public final ObjectId 			_id;
	public final ActuatorStateEnum[][] 	actuatorsStates;
	public final GameEvent			lastGameEvent;
	public final int 				blueScore;
	public final int 				yellowScore;
	
	public final ArrayList<SchoolPenalty>	penalties;
	public final ArrayList<School>	misconductPenalties;
	
	public GameState(
			@JsonProperty("_id")					ObjectId 				_gameEventId,
			@JsonProperty("actuatorsStates")		ActuatorStateEnum[][]	_actuatorsStates,
			@JsonProperty("lastGameEvent")			GameEvent				_lastGameEvent,
			@JsonProperty("blueScore")				int 					_blueScore,
			@JsonProperty("yellowScore")			int 					_yellowScore,
			@JsonProperty("penalties")				ArrayList<SchoolPenalty>		_penalties,
			@JsonProperty("misconductPenalties")	ArrayList<School>		_misconductPenalties
			)
	{
		_id 				= _gameEventId;
		actuatorsStates 	= _actuatorsStates;
		lastGameEvent		= _lastGameEvent;
		blueScore 			= _blueScore;
		yellowScore 		= _yellowScore;
		penalties 			= _penalties;
		misconductPenalties	= _misconductPenalties;
	}
	
	public GameState(GameState previousState, GameEvent gameEvent)
	{
		_id = null;
		lastGameEvent = gameEvent;

		ActuatorStateEnum[][] localActuatorState = null;
		int localBlueScore = 0;
		int localYellowScore = 0;
		ArrayList<SchoolPenalty> 	localPenalties = null;
		ArrayList<School> 	localMisconductPenalties = null;
		
		if(gameEvent.gameEvent == GameEventEnum.START_GAME)
		{
			localActuatorState = new ActuatorStateEnum[SideEnum.values().length][TargetEnum.values().length];
			for( SideEnum side : SideEnum.values() )
			{
				for( TargetEnum target : TargetEnum.values() )
				{
					localActuatorState[side.ordinal()][target.ordinal()] = ActuatorStateEnum.CLOSED;
				}
			}
			localBlueScore = 0;
			localYellowScore = 0;
			
			localPenalties = new ArrayList<SchoolPenalty>();
			localMisconductPenalties = new ArrayList<School>();
		}
		else
		{
			// We need to make a copy of the array so that the array is not a reference of each
			localActuatorState = new ActuatorStateEnum[SideEnum.values().length][TargetEnum.values().length];
			for( SideEnum side : SideEnum.values() )
			{
				for( TargetEnum target : TargetEnum.values() )
				{
					localActuatorState[side.ordinal()][target.ordinal()] = previousState.actuatorsStates[side.ordinal()][target.ordinal()];
				}
			}
			
			localBlueScore	 = previousState.blueScore;
			localYellowScore = previousState.yellowScore;
			
			localPenalties 				= previousState.penalties;
			localMisconductPenalties	= previousState.misconductPenalties;

			if(gameEvent.gameEvent == GameEventEnum.TARGET_HIT)
			{
				TargetHitEvent targetHitevent = (TargetHitEvent)gameEvent;
				ActuatorStateEnum currentActuator = localActuatorState[targetHitevent.side.ordinal()][targetHitevent.target.ordinal()];
				if(currentActuator == ActuatorStateEnum.BLUE)
				{
					localBlueScore += calculateTargetHitValue(localActuatorState, targetHitevent.side, targetHitevent.target);
				}
				else if(currentActuator == ActuatorStateEnum.YELLOW)
				{
					localYellowScore += calculateTargetHitValue(localActuatorState, targetHitevent.side, targetHitevent.target);
				}
			}
			else if(gameEvent.gameEvent == GameEventEnum.ACTUATOR_CHANGED)
			{
				ActuatorStateChangedEvent actuatorStateChangedEvent = (ActuatorStateChangedEvent)gameEvent;
				localActuatorState[actuatorStateChangedEvent.side.ordinal()][actuatorStateChangedEvent.target.ordinal()] = actuatorStateChangedEvent.actuatorState;
			}
			else if(gameEvent.gameEvent == GameEventEnum.POINT_MODIFIER)
			{
				PointModifierEvent pointModifierEvent = (PointModifierEvent) gameEvent;
				if(pointModifierEvent.team == TeamEnum.BLUE)
				{
					localBlueScore += pointModifierEvent.points;
				}
				else if(pointModifierEvent.team == TeamEnum.YELLOW)
				{
					localYellowScore += pointModifierEvent.points;
				}
			}
			else if(gameEvent.gameEvent == GameEventEnum.SCHOOL_PENALTY)
			{
				SchoolPenaltyEvent schoolPenaltyEvent = (SchoolPenaltyEvent) gameEvent;
				localPenalties.add(new SchoolPenalty(schoolPenaltyEvent.school, schoolPenaltyEvent.pointsDeduction));
			}
			else if(gameEvent.gameEvent == GameEventEnum.TEAM_PENALTY)
			{
				TeamPenaltyEvent teamPenaltyEvent = (TeamPenaltyEvent) gameEvent;
				if(teamPenaltyEvent.team == TeamEnum.BLUE)
				{
					localBlueScore += teamPenaltyEvent.pointsDeduction;
				}
				else if(teamPenaltyEvent.team == TeamEnum.YELLOW)
				{
					localYellowScore += teamPenaltyEvent.pointsDeduction;
				}
			}
			else if(gameEvent.gameEvent == GameEventEnum.MISCONDUCT_PENALTY)
			{
				MisconductPenaltyEvent misconductPenaltyEvent = (MisconductPenaltyEvent) gameEvent;
				localMisconductPenalties.add(misconductPenaltyEvent.school);
			}
			else if(gameEvent.gameEvent == GameEventEnum.END_GAME)
			{
				// Nothing to do for now.
			}
		}

		actuatorsStates 	= localActuatorState;
		blueScore 			= localBlueScore;
		yellowScore 		= localYellowScore;
		penalties			= localPenalties;
		misconductPenalties	= localMisconductPenalties;
	}
	
	public static int calculateTargetHitValue(ActuatorStateEnum[][] localActuatorStates, SideEnum side, TargetEnum targetHit)
	{
		ActuatorStateEnum actuatorValue = localActuatorStates[side.ordinal()][targetHit.ordinal()];
		
		int numMultiplier = 0;
		
		for(ActuatorStateEnum actuator : localActuatorStates[side.ordinal()])
		{
			if(actuatorValue == actuator)
			{
				numMultiplier++;
			}
		}
		
		return ACTUATOR_MULTIPLIERS[numMultiplier] * TARGET_VALUE[targetHit.ordinal()];
	}
	
	public static boolean areAllActuatorSameColor(ActuatorStateEnum[][] localActuatorStates)
	{
		ActuatorStateEnum actuatorState = localActuatorStates[0][0];
		
		for( SideEnum side : SideEnum.values() )
		{
			for( TargetEnum target : TargetEnum.values() )
			{
				ActuatorStateEnum currentState = localActuatorStates[side.ordinal()][target.ordinal()];
				if(currentState == ActuatorStateEnum.CLOSED || actuatorState != currentState)
				{
					return false;
				}
			}
		}
		
		return true;
	}
}
