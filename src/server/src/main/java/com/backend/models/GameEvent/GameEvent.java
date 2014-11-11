package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

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
