package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EndGameEvent implements GameEvent 
{
	public final DateTime			time;
	
	public EndGameEvent(
			@JsonProperty("time")		DateTime		_time
			)
	{
		time = _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.END_GAME;
	}
}
