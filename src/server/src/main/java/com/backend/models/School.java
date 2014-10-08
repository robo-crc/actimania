package com.backend.models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class School 
{
	public final ObjectId 			_id;
	public final String 			name;
	
	public School(	@JsonProperty("_id") 	ObjectId 		_schoolId,
					@JsonProperty("name") 	String 			_name
					)
	{
		_id 	= _schoolId;
		name 	= _name;
	}
}
