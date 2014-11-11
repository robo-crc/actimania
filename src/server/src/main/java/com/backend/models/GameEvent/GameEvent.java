package com.backend.models.GameEvent;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface GameEvent 
{
	public DateTime 		getTime();
	public GameEventEnum 	getGameEventEnum();
}
