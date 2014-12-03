package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.joda.time.Duration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;

public class SkillsCompetition 
{
	public final ObjectId 					_id;
	
	private final ArrayList<SchoolInteger> 	pickBalls;
	private final ArrayList<SchoolDuration> twoTargetHits;
	private final ArrayList<SchoolDuration> twoActuatorChanged;
	
	@JsonIgnore
	private final TreeMap<School, Integer> pickBallsPosition;
	@JsonIgnore
	private final TreeMap<School, Integer> twoTargetHitsPosition;
	@JsonIgnore
	private final TreeMap<School, Integer> twoActuatorChangedPosition;
	
	@JsonIgnore
	public final static double SKILL_WEIGTH = 0.1; // 10% per skill competition in the final note
	
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
		
		pickBallsPosition = new TreeMap<School, Integer>();
		twoTargetHitsPosition = new TreeMap<School, Integer>();
		twoActuatorChangedPosition = new TreeMap<School, Integer>();
		
		ArrayList<SchoolInteger> pickBallsRanking = getOrderedInteger(pickBalls);
		ArrayList<SchoolDuration> twoTargetsRanking = getOrderedDuration(twoTargetHits);
		ArrayList<SchoolDuration> twoActuatorsRanking = getOrderedDuration(twoActuatorChanged);
		
		for(School school : pickBalls)
		{
			pickBallsPosition.put(school, getPositionInteger(pickBallsRanking, school));
			twoTargetHitsPosition.put(school, getPositionDuration(twoTargetsRanking, school));
			twoActuatorChangedPosition.put(school, getPositionDuration(twoActuatorsRanking, school));
		}
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
	
	private static int getPositionInteger(final ArrayList<SchoolInteger> schoolsRanking, School school)
	{
		int position = schoolsRanking.indexOf(school);
		Integer score = schoolsRanking.get(schoolsRanking.indexOf(school)).integer;
		
		// In case of equality, we give the best points
		while(position > 0 && score.equals(schoolsRanking.get(position - 1).integer))
		{
			position--;
		}
		
		return position;
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
	
	private static int getPositionDuration(final ArrayList<SchoolDuration> schoolsRanking, School school)
	{
		int position = schoolsRanking.indexOf(school);
		Duration duration = schoolsRanking.get(schoolsRanking.indexOf(school)).duration;
		
		// In case of equality, we give the best points
		while(position > 0 && duration.equals(schoolsRanking.get(position - 1).duration))
		{
			position--;
		}
		
		return position;
	}
	
	public int getPickballsPosition(School school)
	{
		return pickBallsPosition.get(school);
	}
	
	public int getTwoTargetHitsPosition(School school)
	{
		return twoTargetHitsPosition.get(school);
	}
	
	public int getTwoActuatorChangedPosition(School school)
	{
		return twoActuatorChangedPosition.get(school);
	}
	
	public ArrayList<SchoolInteger> getPickballsOrdered()
	{
		return getOrderedInteger(pickBalls);
	}
	
	public ArrayList<SchoolDuration> getTwoTargetHitsOrdered()
	{
		return getOrderedDuration(twoTargetHits);
	}
	
	public ArrayList<SchoolDuration> getTwoActuatorChangedOrdered()
	{
		return getOrderedDuration(twoActuatorChanged);
	}
	
	public double getPickballsPoints(School school)
	{
		return (pickBallsPosition.size() - pickBallsPosition.get(school)) * SKILL_WEIGTH;
	}
	
	public double getTwoTargetHitsPoints(School school)
	{
		return (twoTargetHitsPosition.size() - twoTargetHitsPosition.get(school)) * SKILL_WEIGTH;
	}
	
	public double getTwoActuatorChangedPoints(School school)
	{
		return (twoActuatorChangedPosition.size() - twoActuatorChangedPosition.get(school)) * SKILL_WEIGTH;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends School> ArrayList<T> getOrdered(ArrayList<T> skillSchools)
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
	
	public double getSchoolScore(School school)
	{
		double score = getPickballsPoints(school);
		score += getTwoTargetHitsPoints(school);
		score += getTwoActuatorChangedPoints(school);
		return score;
	}
	
	public static SkillsCompetition get(Database database)
	{
		return database.findOne(SkillsCompetition.class, "{ }");
	}
}
