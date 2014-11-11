package com.backend.models.GameEvent;

import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActuatorStateChangedEvent  extends GameEvent
{
	public final SideEnum 			side;
	public final TargetEnum 		target;
	public final ActuatorStateEnum	actuatorState;

	public ActuatorStateChangedEvent(
			@JsonProperty("side") 			SideEnum 			_side, 
			@JsonProperty("target") 		TargetEnum 			_target,
			@JsonProperty("actuatorState") 	ActuatorStateEnum 	_actuatorState )
			{
				super(GameEventEnum.ACTUATOR_CHANGED);
				side = _side;
				target = _target;
				actuatorState = _actuatorState;
			}
}
