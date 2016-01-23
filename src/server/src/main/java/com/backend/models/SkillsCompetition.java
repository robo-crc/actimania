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
	
	private final ArrayList<SchoolDuration> takeAllPieces;
	private final ArrayList<SchoolDuration> placeThreePieces;
	private final ArrayList<SchoolDuration> placeHighest;
	
	@JsonIgnore
	private final TreeMap<School, Integer> takeAllPiecesPosition;
	@JsonIgnore
	private final TreeMap<School, Integer> placeThreePiecesPosition;
	@JsonIgnore
	private final TreeMap<School, Integer> placeHighestPosition;
	
	@JsonIgnore
	private final SchoolDuration takeAllPiecesBest;
	@JsonIgnore
	private final SchoolDuration placeThreePiecesBest;
	@JsonIgnore
	private final SchoolDuration placeHighestBest;
	
	@JsonIgnore
	public final static double SKILL_WEIGTH = 0.1; // 10% per skill competition in the final note
	
	public SkillsCompetition(
			@JsonProperty("_id") 				ObjectId 					_skillCompetitionId,
			@JsonProperty("takeAllPieces") 		ArrayList<SchoolDuration> 	_takeAllPieces,
			@JsonProperty("placeThreePieces") 	ArrayList<SchoolDuration> 	_placeThreePieces,
			@JsonProperty("placeHighest") 		ArrayList<SchoolDuration> 	_placeHighest)
	{
		_id 				=_skillCompetitionId;
		takeAllPieces 		= _takeAllPieces;
		placeThreePieces 	= _placeThreePieces;
		placeHighest 		= _placeHighest;
		
		takeAllPiecesPosition = new TreeMap<School, Integer>();
		placeThreePiecesPosition = new TreeMap<School, Integer>();
		placeHighestPosition = new TreeMap<School, Integer>();
		
		ArrayList<SchoolDuration> pickBallsRanking = getOrderedDuration(takeAllPieces);
		ArrayList<SchoolDuration> twoTargetsRanking = getOrderedDuration(placeThreePieces);
		ArrayList<SchoolDuration> twoActuatorsRanking = getOrderedDuration(placeHighest);
		
		takeAllPiecesBest = pickBallsRanking.get(0);
		placeThreePiecesBest = twoTargetsRanking.get(0);
		placeHighestBest = twoActuatorsRanking.get(0);
		
		for(School school : takeAllPieces)
		{
			takeAllPiecesPosition.put(school, getPositionDuration(pickBallsRanking, school));
			placeThreePiecesPosition.put(school, getPositionDuration(twoTargetsRanking, school));
			placeHighestPosition.put(school, getPositionDuration(twoActuatorsRanking, school));
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
	
	public int getTakeAllPiecesPosition(School school)
	{
		return takeAllPiecesPosition.get(school);
	}
	
	public int getPlaceThreePiecesPosition(School school)
	{
		return placeThreePiecesPosition.get(school);
	}
	
	public int getPlaceHighestPosition(School school)
	{
		return placeHighestPosition.get(school);
	}
	
	public SchoolDuration getTakeAllPieces(School school)
	{
		return takeAllPieces.get(takeAllPieces.indexOf(school));
	}
	
	public SchoolDuration getPlaceThreePieces(School school)
	{
		return placeThreePieces.get(placeThreePieces.indexOf(school));
	}
	
	public SchoolDuration getPlaceHighest(School school)
	{
		return placeHighest.get(placeHighest.indexOf(school));
	}
	
	public ArrayList<SchoolDuration> getTakeAllPiecesOrdered()
	{
		return getOrderedDuration(takeAllPieces);
	}
	
	public ArrayList<SchoolDuration> getPlaceThreePiecesOrdered()
	{
		return getOrderedDuration(placeThreePieces);
	}
	
	public ArrayList<SchoolDuration> getPlaceHighestOrdered()
	{
		return getOrderedDuration(placeHighest);
	}
	
	public double getTakeAllPiecesBestPoints(School school)
	{
		return getPointsDuration(takeAllPiecesBest, getTakeAllPieces(school));
	}
	
	public double getPlaceThreePiecesBestPoints(School school)
	{
		return getPointsDuration(placeThreePiecesBest, getPlaceThreePieces(school));
	}
	
	public double getPlaceHighestBestPoints(School school)
	{
		return getPointsDuration(placeHighestBest, getPlaceHighest(school));
	}
	
	public static double getPointsInteger(SchoolInteger best, SchoolInteger current)
	{
		// Don't divide by 0
		if(best.integer.intValue() == 0)
			return 0;
			
		return (current.integer.doubleValue() / best.integer.doubleValue()) * SKILL_WEIGTH;
	}
	
	public static double getPointsDuration(SchoolDuration best, SchoolDuration current)
	{
		// Don't divide by 0
		if(current.duration.getMillis() == 0)
			return 0;
		
		return ((double)best.duration.getMillis() / (double)current.duration.getMillis()) * SKILL_WEIGTH;
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
		double score = getTakeAllPiecesBestPoints(school);
		score += getPlaceThreePiecesBestPoints(school);
		score += getPlaceHighestBestPoints(school);
		return score;
	}
	
	public static SkillsCompetition get(Database database)
	{
		return database.findOne(SkillsCompetition.class, "{ }");
	}
}
