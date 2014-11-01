package com.backend.models;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.models.Essentials;
import com.google.common.collect.Lists;

public class School implements Comparable<School>
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

	@Override
	public int compareTo(School o) 
	{
		return _id.compareTo(o._id);
	}
	
	public static ArrayList<School> getSchools(Essentials essentials)
	{
		return Lists.newArrayList(essentials.database.find(School.class, "{ }"));
	}
}
