package com.backend.models;

import org.bson.types.ObjectId;

import com.backend.models.enums.ActuatorEnum;
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
	public final ActuatorEnum[][] 	actuatorsStates;
	public final int 				blueScore;
	public final int 				yellowScore;
	
	public GameState(
			@JsonProperty("_id")				ObjectId 			_gameEventId,
			@JsonProperty("actuatorsStates")	ActuatorEnum[][]	_actuatorsStates,
			@JsonProperty("blueScore")			int 				_blueScore,
			@JsonProperty("yellowScore")		int 				_yellowScore
			)
	{
		_id 			= _gameEventId;
		actuatorsStates = _actuatorsStates;
		blueScore 		= _blueScore;
		yellowScore 	= _yellowScore;
	}
	
	public GameState(GameState previousState, GameEvent gameEvent)
	{
		_id = null;

		ActuatorEnum[][] localActuatorState = null;
		int localBlueScore = 0;
		int localYellowScore = 0;
		
		if(gameEvent.gameEvent == GameEventEnum.START_GAME)
		{
			localActuatorState = new ActuatorEnum[SideEnum.values().length][TargetEnum.values().length];
			for( SideEnum side : SideEnum.values() )
			{
				for( TargetEnum target : TargetEnum.values() )
				{
					localActuatorState[side.ordinal()][target.ordinal()] = ActuatorEnum.CLOSED;
				}
			}
			localBlueScore = 0;
			localYellowScore = 0;
		}
		else
		{
			// We need to make a copy of the array so that the array is not a reference of each
			localActuatorState = new ActuatorEnum[SideEnum.values().length][TargetEnum.values().length];
			for( SideEnum side : SideEnum.values() )
			{
				for( TargetEnum target : TargetEnum.values() )
				{
					localActuatorState[side.ordinal()][target.ordinal()] = previousState.actuatorsStates[side.ordinal()][target.ordinal()];
				}
			}
			localBlueScore = previousState.blueScore;
			localYellowScore = previousState.yellowScore;

			if(gameEvent.gameEvent == GameEventEnum.TARGET_HIT)
			{
				ActuatorEnum currentActuator = localActuatorState[gameEvent.side.ordinal()][gameEvent.target.ordinal()];
				if(currentActuator == ActuatorEnum.BLUE)
				{
					localBlueScore += calculateTargetHitValue(localActuatorState, gameEvent.side, gameEvent.target);
				}
				else if(currentActuator == ActuatorEnum.YELLOW)
				{
					localYellowScore += calculateTargetHitValue(localActuatorState, gameEvent.side, gameEvent.target);
				}
			}
			else if(gameEvent.gameEvent == GameEventEnum.ACTUATOR_CHANGED)
			{
				localActuatorState[gameEvent.side.ordinal()][gameEvent.target.ordinal()] = gameEvent.actuator;
			}
			else if(gameEvent.gameEvent == GameEventEnum.POINT_MODIFIER)
			{
				if(gameEvent.pointModifier.team == TeamEnum.BLUE)
				{
					localBlueScore += gameEvent.pointModifier.points;
				}
				else if(gameEvent.pointModifier.team == TeamEnum.YELLOW)
				{
					localYellowScore += gameEvent.pointModifier.points;
				}
			}
			else if(gameEvent.gameEvent == GameEventEnum.END_GAME)
			{
				// Nothing to do for now.
			}
		}
				
		actuatorsStates = localActuatorState;
		blueScore 		= localBlueScore;
		yellowScore 	= localYellowScore;
	}
	
	public static int calculateTargetHitValue(ActuatorEnum[][] localActuatorStates, SideEnum side, TargetEnum targetHit)
	{
		ActuatorEnum actuatorValue = localActuatorStates[side.ordinal()][targetHit.ordinal()];
		
		int numMultiplier = 0;
		
		for(ActuatorEnum actuator : localActuatorStates[side.ordinal()])
		{
			if(actuatorValue == actuator)
			{
				numMultiplier++;
			}
		}
		
		return ACTUATOR_MULTIPLIERS[numMultiplier] * TARGET_VALUE[targetHit.ordinal()];
	}
	
	public static boolean areAllActuatorSameColor(ActuatorEnum[][] localActuatorStates)
	{
		ActuatorEnum actuatorState = localActuatorStates[0][0];
		
		for( SideEnum side : SideEnum.values() )
		{
			for( TargetEnum target : TargetEnum.values() )
			{
				ActuatorEnum currentState = localActuatorStates[side.ordinal()][target.ordinal()];
				if(currentState == ActuatorEnum.CLOSED || actuatorState != currentState)
				{
					return false;
				}
			}
		}
		
		return true;
	}
}
