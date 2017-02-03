package com.backend.models;

import org.bson.types.ObjectId;

import com.backend.models.enums.Division;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolFloat extends School
{
	public final Float 	floatValue;
	
	public SchoolFloat(
			@JsonProperty("_id") 		ObjectId 	_schoolId,
			@JsonProperty("name") 		String 		_name,
			@JsonProperty("floatValue") Float 	_floatValue
			)
	{
		super(_schoolId, _name);
		floatValue = _floatValue;
	}
	
	public SchoolFloat(School school, Float _floatValue)
	{
		super(school._id, school.name);
		floatValue = _floatValue;
	}
	
	public String getDisplay()
	{
		 return floatValue.toString();
	}
	
	public String getDisplayLong()
	{
		 return floatValue.toString();
	}
}
