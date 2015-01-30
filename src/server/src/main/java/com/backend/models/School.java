package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.framework.models.Essentials;
import com.google.common.collect.Lists;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
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
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null )
			return false;
		
		if(o == this)
			return true;
		
		if(!(o instanceof School))
			return false;
		
		School otherSchool = (School)o;
		
		if(_id == null || otherSchool._id == null)
			return false;
		
		return _id.equals(otherSchool._id);
	}
	
	public static ArrayList<School> getSchools(Essentials essentials)
	{
		return Lists.newArrayList(essentials.database.find(School.class, "{ }"));
	}
	
	public String getCompactName()
	{
		String apostropheRemoval = name.replaceAll("&#146;", "");
		return StringEscapeUtils.unescapeHtml(apostropheRemoval)
				.toLowerCase()
				.replaceAll(" ", "_")
				.replaceAll("'", "")
				.replaceAll("è", "e")
				.replaceAll("é", "e");
	}
}
