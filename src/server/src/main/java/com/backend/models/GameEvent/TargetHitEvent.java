package com.backend.models.GameEvent;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetHitEvent extends GameEvent
{
	public final SideEnum 			side;
	public final TargetEnum 		target;

	public TargetHitEvent(
			@JsonProperty("side") 			SideEnum 			_side, 
			@JsonProperty("target") 		TargetEnum 			_target )
			{
				super(GameEventEnum.TARGET_HIT);
				side = _side;
				target = _target;
			}
}
