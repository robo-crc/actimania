package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActuatorStateChangedEvent implements GameEvent
{
	public final SideEnum 			side;
	public final TargetEnum 		target;
	public final ActuatorStateEnum	actuatorState;
	public final DateTime			time;

	public ActuatorStateChangedEvent(
			@JsonProperty("side") 			SideEnum 			_side, 
			@JsonProperty("target") 		TargetEnum 			_target,
			@JsonProperty("actuatorState") 	ActuatorStateEnum 	_actuatorState,
			@JsonProperty("time")			DateTime			_time)
			{
				side = _side;
				target = _target;
				actuatorState = _actuatorState;
				time = _time;
			}
	
	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.ACTUATOR_STATE_CHANGED;
	}
}
