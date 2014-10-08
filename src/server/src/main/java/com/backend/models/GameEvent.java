package com.backend.models;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.backend.models.enums.ActuatorEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameEvent 
{
	public final ObjectId 		_id;
	
	public final GameEventEnum 	gameEvent;
	public final TargetEnum 	target;
	public final SideEnum 		side;
	public final ActuatorEnum	actuator;
	public final PointModifier	pointModifier;
	public final DateTime		time;

	// For database save.
	public GameEvent(
			@JsonProperty("_id") 			ObjectId		_gameEventId,
			@JsonProperty("gameEvent")		GameEventEnum	_gameEvent, 
			@JsonProperty("side") 			SideEnum 		_side, 
			@JsonProperty("target") 		TargetEnum 		_target, 
			@JsonProperty("actuator") 		ActuatorEnum	_actuator,
			@JsonProperty("pointModifier")	PointModifier 	_pointModifier,
			@JsonProperty("time")			DateTime		_time)
	{
		_id 			= _gameEventId;
		gameEvent 		= _gameEvent;
		side			= _side;
		target			= _target;
		actuator		= _actuator;
		pointModifier 	= _pointModifier;
		time 			= _time;
	}
	
	public GameEvent(GameEventEnum _gameEventEnum)
	{
		gameEvent 		= _gameEventEnum;
		time			= DateTime.now();
		
		_id 			= null;
		actuator		= null;
		target 			= null;
		side 			= null;
		pointModifier	= null;
	}
		
	public static GameEvent targetHitEvent(SideEnum _side, TargetEnum _target)
	{
		return new GameEvent(
				null,
				GameEventEnum.TARGET_HIT,
				_side,
				_target,
				null,
				null,
				DateTime.now()
				);
	}
	
	public static GameEvent actuatorChangedEvent(SideEnum _side, TargetEnum _target, ActuatorEnum _actuator)
	{
		return new GameEvent(
				null,
				GameEventEnum.ACTUATOR_CHANGED,
				_side,
				_target,
				_actuator,
				null,
				DateTime.now()
				);
	}
	
	public static GameEvent pointModifierEvent(PointModifier _pointModifier)
	{
		return new GameEvent(
				null,
				GameEventEnum.POINT_MODIFIER,
				null,
				null,
				null,
				_pointModifier,
				DateTime.now()
				);
	}
}
