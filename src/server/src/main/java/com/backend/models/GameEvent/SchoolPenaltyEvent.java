package com.backend.models.GameEvent;

import com.backend.models.School;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolPenaltyEvent extends GameEvent
{
	public final School	 	school;
	public final int 		pointsDeduction;

	public SchoolPenaltyEvent(
			@JsonProperty("school") 				School	 	_school, 
			@JsonProperty("pointsDeduction") 		int 		_pointsDeduction
			)
	{
		super(GameEventEnum.SCHOOL_PENALTY);
		school 				= _school;
		pointsDeduction 	= _pointsDeduction;
	}
}
