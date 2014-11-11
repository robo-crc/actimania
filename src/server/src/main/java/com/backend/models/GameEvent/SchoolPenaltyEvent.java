package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.School;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
