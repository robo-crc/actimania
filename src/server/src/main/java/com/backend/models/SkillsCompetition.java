package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.joda.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;
import com.framework.models.Essentials;

public class SkillsCompetition 
{
	public final ObjectId 					_id;
	
	public final TreeMap<School, Integer> 	pickBalls;
	public final TreeMap<School, Duration> 	twoTargetHits;
	public final TreeMap<School, Duration> 	twoActuatorChanged;
	
	public SkillsCompetition(
			@JsonProperty("_id") 				ObjectId 					_skillCompetitionId,
			@JsonProperty("pickBalls") 			TreeMap<School, Integer> 	_pickBalls,
			@JsonProperty("twoTargetHits") 		TreeMap<School, Duration> 	_twoTargetHits,
			@JsonProperty("twoActuatorChanged") TreeMap<School, Duration> 	_twoActuatorChanged)
	{
		_id 				=_skillCompetitionId;
		pickBalls 			= _pickBalls;
		twoTargetHits 		= _twoTargetHits;
		twoActuatorChanged 	= _twoActuatorChanged;
	}
	
	public static <T> int getSkillPoints(final TreeMap<School, T> schoolsScore, School school)
	{
		ArrayList<School> schoolsRanking = new ArrayList<School>();
		for(Map.Entry<School, T> schoolValue : schoolsScore.entrySet() )
		{
			schoolsRanking.add(schoolValue.getKey());
		}
		
		final Class<?> entityType = schoolsScore.firstEntry().getValue().getClass();
		
		Collections.sort(schoolsRanking, new Comparator<School>() {
	        @Override
	        public int compare(School school1, School school2)
	        {
	        	if(entityType == Integer.class)
	        	{
	        		return (Integer)schoolsScore.get(school2) - (Integer)schoolsScore.get(school1);
	        	}
	        	else if(entityType == Duration.class)
	        	{
	        		// Lowest time is better
	        		return (int) (((Duration)schoolsScore.get(school1)).getMillis() - ((Duration)schoolsScore.get(school2)).getMillis());
	        	}
	        	else
	        	{
	        		return -1;
	        	}
	        }
	    });
		
		int position = schoolsRanking.indexOf(school);
		T score = schoolsScore.get(school);
		
		// In case of equality, we give the best points
		while(position > 0 && score.equals(schoolsScore.get(schoolsRanking.get(position - 1))))
		{
			position--;
		}
		
		return schoolsRanking.size() - position;
	}
	
	public int getSchoolScore(School school)
	{
		int score = getSkillPoints(pickBalls, school);
		score += getSkillPoints(twoTargetHits, school);
		score += getSkillPoints(twoActuatorChanged, school);
		return score;
	}
	
	public ArrayList<School> getCompetitionRanking(Essentials essentials)
	{
		ArrayList<School> schoolsRanking = new ArrayList<School>(School.getSchools(essentials));

		final TreeMap<School, Integer> schoolsScore = new TreeMap<School, Integer>();
		
		for(School school : schoolsRanking)
		{
			schoolsScore.put(school, getSchoolScore(school));
		}
		
		Collections.sort(schoolsRanking, new Comparator<School>() {
	        @Override
	        public int compare(School school1, School school2)
	        {
	            return schoolsScore.get(school2) - schoolsScore.get(school1);
	        }
	    });
		
		return schoolsRanking;
	}
	
	public static SkillsCompetition get(Database database)
	{
		return database.findOne(SkillsCompetition.class, "{ }");
	}
}
