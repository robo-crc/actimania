package com.backend.models;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.framework.helpers.Helpers;

public class SchoolInteger extends School implements ISchoolScore
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
	
	public double getPercentage(ISchoolScore _best)
	{
		SchoolInteger best = (SchoolInteger)_best;
		// Don't divide by 0
		if(best.integer.intValue() == 0)
			return 0;
			
		return (integer.doubleValue() / best.integer.doubleValue());
	}
	
	public String getDisplay()
	{
		 return integer.toString();
	}
	
	public String getDisplayLong()
	{
		 return integer.toString();
	}
}
