package com.backend.models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolInteger  extends School
{
	public final Integer 	integer;
	
	public SchoolInteger(
			@JsonProperty("_id") 		ObjectId 	_schoolId,
			@JsonProperty("name") 		String 		_name,
			@JsonProperty("integer") 	Integer 	_integer
			)
	{
		super(_schoolId, _name);
		integer 	= _integer;
	}
	
	public SchoolInteger(School school, Integer _integer)
	{
		super(school._id, school.name);
		integer 	= _integer;
	}
}
