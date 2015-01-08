package com.backend.models;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayoffGroup 
{
	public static final int KEEP_SCHOOLS = 2;
	public final ArrayList<School> schools;
	
	public PlayoffGroup(
			@JsonProperty("schools") 	ArrayList<School> _schools)
	{
		schools = _schools;
	}
	
	public ArrayList<School> getSchoolsForNextRound(ArrayList<School> rankedSchools)
	{
		ArrayList<School> toNextRound = new ArrayList<School>();
		for(School school : rankedSchools)
		{
			if(schools.contains(school))
			{
				toNextRound.add(school);
				if(toNextRound.size() >= KEEP_SCHOOLS)
				{
					break;
				}
			}
		}
		
		return toNextRound;
	}
}
