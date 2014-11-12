package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class StartGameEvent implements GameEvent
{
	public final DateTime			time;
	
	public StartGameEvent(
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
		return GameEventEnum.START_GAME;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		return new LocalizedString(locale,
				"Game start",
				"Début de la partie"
				);
	}
}
