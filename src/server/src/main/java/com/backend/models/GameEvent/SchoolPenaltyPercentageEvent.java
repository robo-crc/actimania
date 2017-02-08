package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.School;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class SchoolPenaltyPercentageEvent implements GameEvent
{
	public final School	 	school;
	public final float 		percentageDeduction;
	public final DateTime	time;

	public SchoolPenaltyPercentageEvent(
			@JsonProperty("school") 				School	 	_school, 
			@JsonProperty("percentageDeduction") 	float 		_percentageDeduction,
			@JsonProperty("time")					DateTime	_time
			)
	{
		school 				= _school;
		percentageDeduction = _percentageDeduction;
		time 				= _time;
	}

	public DateTime getTime()
	{
		return time;
	}
	
	public GameEventEnum getGameEventEnum()
	{
		return GameEventEnum.SCHOOL_PENALTY_PERCENTAGE;
	}
	
	public LocalizedString getLocalizedString(Locale locale)
	{
		return new LocalizedString(locale,
				String.valueOf(percentageDeduction * 100) + "% penalty for school " + school.name,
				 "Pénalitée de " + String.valueOf(percentageDeduction * 100) + "% pour " + school.name
				);
	}
}
