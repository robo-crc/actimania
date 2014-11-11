package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class GameEvent 
{
	public final GameEventEnum 		gameEvent;
	public final DateTime			time;

	// For database save.
	public GameEvent(
			@JsonProperty("gameEvent")		GameEventEnum		_gameEvent, 
			@JsonProperty("time")			DateTime			_time)
	{
		gameEvent 		= _gameEvent;
		time 			= _time;
	}
	
	public GameEvent(GameEventEnum _gameEventEnum)
	{
		gameEvent 		= _gameEventEnum;
		time			= DateTime.now();
	}
}
