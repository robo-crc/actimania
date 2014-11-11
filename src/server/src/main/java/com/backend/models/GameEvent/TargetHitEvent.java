package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetHitEvent implements GameEvent
{
	public final SideEnum 	side;
	public final TargetEnum	target;
	public final DateTime	time;

	public TargetHitEvent(
			@JsonProperty("side") 			SideEnum 			_side, 
			@JsonProperty("target") 		TargetEnum 			_target,
			@JsonProperty("time")			DateTime			_time
			)
	{
		side 	= _side;
		target 	= _target;
		time 	= _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.TARGET_HIT;
	}
}
