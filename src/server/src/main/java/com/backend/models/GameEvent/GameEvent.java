package com.backend.models.GameEvent;

import java.util.Locale;

import org.joda.time.DateTime;

import com.backend.models.enums.GameEventEnum;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.framework.helpers.LocalizedString;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface GameEvent 
{
	public DateTime 		getTime();
	public GameEventEnum 	getGameEventEnum();
	public LocalizedString	getLocalizedString(Locale locale);
}
