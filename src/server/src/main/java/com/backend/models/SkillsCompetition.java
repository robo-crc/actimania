package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.joda.time.Duration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;
import com.framework.models.Essentials;

public class SkillsCompetition 
{
	public final ObjectId 					_id;
	
	public final ArrayList<SchoolInteger> 	pickBalls;
	public final ArrayList<SchoolDuration> 	twoTargetHits;
	public final ArrayList<SchoolDuration> 	twoActuatorChanged;
	
	public SkillsCompetition(
			@JsonProperty("_id") 				ObjectId 					_skillCompetitionId,
			@JsonProperty("pickBalls") 			ArrayList<SchoolInteger> 	_pickBalls,
			@JsonProperty("twoTargetHits") 		ArrayList<SchoolDuration> 	_twoTargetHits,
			@JsonProperty("twoActuatorChanged") ArrayList<SchoolDuration> 	_twoActuatorChanged)
	{
		_id 				=_skillCompetitionId;
		pickBalls 			= _pickBalls;
		twoTargetHits 		= _twoTargetHits;
		twoActuatorChanged 	= _twoActuatorChanged;
	}
	
	public static int getSkillPoints(final ArrayList<SchoolInteger> schoolsScore, School school)
	{
		ArrayList<SchoolInteger> schoolsRanking = new ArrayList<SchoolInteger>(schoolsScore);
		
		Collections.sort(schoolsRanking, new Comparator<SchoolInteger>() {
	        @Override
	        public int compare(SchoolInteger school1, SchoolInteger school2)
	        {
	            return school2.integer - school1.integer;
	        }
	    });
		
		int position = schoolsRanking.indexOf(school);
		Integer score = schoolsRanking.get(schoolsRanking.indexOf(school)).integer;
		
		// In case of equality, we give the best points
		while(position > 0 && score.equals(schoolsRanking.get(position - 1).integer))
		{
			position--;
		}
		
		return schoolsRanking.size() - position;
	}
	
	public static int getSkillPointsDuration(final ArrayList<SchoolDuration> schoolsScore, School school)
	{
		ArrayList<SchoolDuration> schoolsRanking = new ArrayList<SchoolDuration>(schoolsScore);
		
		Collections.sort(schoolsRanking, new Comparator<SchoolDuration>() {
	        @Override
	        public int compare(SchoolDuration school1, SchoolDuration school2)
	        {
	            return (int) (school1.duration.getMillis() - school2.duration.getMillis());
	        }
	    });
		
		int position = schoolsRanking.indexOf(school);
		Duration duration = schoolsRanking.get(schoolsRanking.indexOf(school)).duration;
		
		// In case of equality, we give the best points
		while(position > 0 && duration.equals(schoolsRanking.get(position - 1).duration))
		{
			position--;
		}
		
		return schoolsRanking.size() - position;
	}
	
	public int getSchoolScore(School school)
	{
		int score = getSkillPoints(pickBalls, school);
		score += getSkillPointsDuration(twoTargetHits, school);
		score += getSkillPointsDuration(twoActuatorChanged, school);
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
