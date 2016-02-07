package com.backend.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayoffGroup 
{
	public final ArrayList<School> schools;
	public final int groupNo;
	
	public PlayoffGroup(
			@JsonProperty("schools") 	ArrayList<School> 	_schools,
			@JsonProperty("groupNo") 	int					_groupNo)
	{
		schools = _schools;
		groupNo = _groupNo;
	}
	
	public ArrayList<School> getSchoolsForNextRound(ArrayList<SchoolInteger> rankedSchools, int nbOfSchoolsToKepp)
	{
		ArrayList<School> toNextRound = new ArrayList<School>();
		for(School school : rankedSchools)
		{
			if(schools.contains(school))
			{
				toNextRound.add(school);
				if(toNextRound.size() >= nbOfSchoolsToKepp)
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
