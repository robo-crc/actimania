package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.School;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class DidNotScoreEvent implements GameEvent
{
	public final School	 	school;
	public final DateTime	time;
	
	public DidNotScoreEvent(
			@JsonProperty("school")	School	 	_school,
			@JsonProperty("time")	DateTime	_time
			)
	{
		school 				= _school;
		time = _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.DID_NOT_SCORE;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		return new LocalizedString(locale,
				school.name + " did not score any point",
				school.name + " n'a pas compté de point"
				);
	}
}
