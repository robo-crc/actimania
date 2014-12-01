package com.backend.models;

import org.bson.types.ObjectId;
import org.joda.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolDuration extends School
{
	public final Duration 	duration;
	
	public SchoolDuration(
			@JsonProperty("_id") 		ObjectId 		_schoolId,
			@JsonProperty("name") 		String 			_name,
			@JsonProperty("duration") 	Duration _duration
			)
	{
		super(_schoolId, _name);
		duration 	= _duration;
	}
	
	public SchoolDuration(School school, Duration _duration)
	{
		super(school._id, school.name);
		duration 	= _duration;
	}
}
