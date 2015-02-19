package com.backend.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayoffGroup 
{
	@JsonIgnore
	public static final int KEEP_SCHOOLS = 2;
	public final ArrayList<School> schools;
	public final int groupNo;
	
	public PlayoffGroup(
			@JsonProperty("schools") 	ArrayList<School> 	_schools,
			@JsonProperty("groupNo") 	int					_groupNo)
	{
		schools = _schools;
		groupNo = _groupNo;
	}
	
	public ArrayList<School> getSchoolsForNextRound(ArrayList<SchoolInteger> rankedSchools)
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
	
	public ArrayList<SchoolInteger> getSchoolsScore(ArrayList<SchoolInteger> rankedSchools)
	{
		ArrayList<SchoolInteger> schoolScores = new ArrayList<SchoolInteger>();
		for(School school : rankedSchools)
		{
			if(schools.contains(school))
			{
				int indexOf = rankedSchools.indexOf(school);
				if(indexOf != -1)
				{
					schoolScores.add(rankedSchools.get(indexOf));
				}
			}
		}
		return schoolScores;
	}
}
