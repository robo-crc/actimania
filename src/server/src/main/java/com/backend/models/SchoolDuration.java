package com.backend.models;

import org.bson.types.ObjectId;
import org.joda.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Helpers;

public class SchoolDuration extends School implements ISchoolScore
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
	

	public double getPercentage(ISchoolScore best)
	{
		// Don't divide by 0
		if(duration.getMillis() == 0)
			return 0;
		
		return ((double)((SchoolDuration)best).duration.getMillis() / (double)duration.getMillis());
	}
	
	public String getDisplay()
	{
		 return Helpers.stopwatchFormatter.print(duration.toPeriod()).toString();
	}
	
	public String getDisplayLong()
	{
		 return Helpers.stopwatchFormatterFull.print(duration.toPeriod()).toString();
	}
}
