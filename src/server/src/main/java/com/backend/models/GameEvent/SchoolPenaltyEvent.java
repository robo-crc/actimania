package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.School;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class SchoolPenaltyEvent implements GameEvent
{
	public final School	 	school;
	public final int 		pointsDeduction;
	public final DateTime	time;

	public SchoolPenaltyEvent(
			@JsonProperty("school") 			School	 	_school, 
			@JsonProperty("pointsDeduction") 	int 		_pointsDeduction,
			@JsonProperty("time")				DateTime	_time
			)
	{
		school 				= _school;
		pointsDeduction 	= _pointsDeduction;
		time 				= _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.SCHOOL_PENALTY;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		return new LocalizedString(locale,
				String.valueOf(pointsDeduction) + " points penalty for school " + school.name,
				 "Pénalitée de " + String.valueOf(pointsDeduction) + " points pour " + school.name
				);
	}
}
