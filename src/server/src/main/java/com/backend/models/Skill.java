package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.LocalizedString;

public class Skill
{
	public final ArrayList<ISchoolScore> schoolsScore;
	
	public final LocalizedString 	displayName;
	
	public final LocalizedString 	displayNameUpperCompact;

	public final String 			shortName;

	@JsonIgnore
	private final ISchoolScore 		bestSchoolScore;
	
	public Skill(
			@JsonProperty("schoolsScore")				ArrayList<ISchoolScore> _schoolsScore, 
			@JsonProperty("displayName")				LocalizedString _displayName,
			@JsonProperty("displayNameUpperCompact") 	LocalizedString _displayNameUpperCompact,
			@JsonProperty("shortName")					String _shortName)
	{
		schoolsScore 			= _schoolsScore;
		displayName 			= _displayName;
		displayNameUpperCompact = _displayNameUpperCompact;
		shortName				= _shortName;
		
		bestSchoolScore = getOrdered(schoolsScore).get(0);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends ISchoolScore> ArrayList<T> getOrdered(ArrayList<T> skillSchools)
	{
		if(skillSchools.get(0) instanceof SchoolInteger)
		{
			return (ArrayList<T>) getOrderedInteger((ArrayList<SchoolInteger>)skillSchools);
		}
		else if(skillSchools.get(0) instanceof SchoolDuration)
		{
			return (ArrayList<T>) getOrderedDuration((ArrayList<SchoolDuration>)skillSchools);
		}
		return null;
	}
	
	private static ArrayList<SchoolDuration> getOrderedDuration(final ArrayList<SchoolDuration> schoolsScore)
	{
		ArrayList<SchoolDuration> schoolsRanking = new ArrayList<SchoolDuration>(schoolsScore);
		
		Collections.sort(schoolsRanking, new Comparator<SchoolDuration>() {
	        @Override
	        public int compare(SchoolDuration school1, SchoolDuration school2)
	        {
	            return (int) (school1.duration.getMillis() - school2.duration.getMillis());
	        }
	    });
		
		return schoolsRanking;
	}
	
	private static ArrayList<SchoolInteger> getOrderedInteger(final ArrayList<SchoolInteger> schoolsScore)
	{
		ArrayList<SchoolInteger> schoolsRanking = new ArrayList<SchoolInteger>(schoolsScore);
		
		Collections.sort(schoolsRanking, new Comparator<SchoolInteger>() {
	        @Override
	        public int compare(SchoolInteger school1, SchoolInteger school2)
	        {
	            return school2.integer - school1.integer;
	        }
	    });
		
		return schoolsRanking;
	}
	
	public double getPercentage(School school)
	{
		return getSchoolScore(school).getPercentage(bestSchoolScore);
	}
	
	public ISchoolScore getSchoolScore(School school)
	{
		return schoolsScore.get(schoolsScore.indexOf(school));
	}
}
