package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.Hole;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class ScoreboardUpdateEvent implements GameEvent
{
	public final Hole[]	 	triangleLeft;
	public final Hole[]	 	triangleRight;
	public final DateTime	time;

	public ScoreboardUpdateEvent(
			@JsonProperty("triangleLeft") 	Hole[]	 	_triangleLeft, 
			@JsonProperty("triangleRight") 	Hole[]	 	_triangleRight,
			@JsonProperty("time")			DateTime	_time
			)
	{
		triangleLeft		= _triangleLeft;
		triangleRight 		= _triangleRight;
		time 				= _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.SCOREBOARD_UPDATED;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		return new LocalizedString(locale,
				"Scoreboard update",
				"Pointage mis-à-jour"
				);
	}
}
