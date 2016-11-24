package com.backend.models.yearly;

import org.bson.types.ObjectId;

import com.backend.models.GameEvent.GameEvent;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Area
{
	public final int value;
	public final int spoolCount;
	
	public Area(
			@JsonProperty("value")		int	_value,
			@JsonProperty("spoolCount")	int _spoolCount)
	{
		value = _value;
		spoolCount = _spoolCount;
	}
	
	public Area(Area area)
	{
		value = area.value;
		spoolCount = area.spoolCount;
	}
			
}
