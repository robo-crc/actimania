package com.backend.models.GameEvent;

import com.backend.models.School;
import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MisconductPenaltyEvent  extends GameEvent
{
	public final School	 	school;

	public MisconductPenaltyEvent(
			@JsonProperty("school") 				School	 	_school
			)
	{
		super(GameEventEnum.MISCONDUCT_PENALTY);
		school 				= _school;
	}
}
