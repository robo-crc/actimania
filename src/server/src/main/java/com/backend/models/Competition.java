package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;
import com.framework.models.Essentials;

public class Competition 
{
	public final ObjectId 					_id;
	public final ArrayList<SchoolInteger> 	kiosk;
	public final ArrayList<SchoolInteger> 	programming;
	public final ArrayList<SchoolInteger> 	robotConstruction;
	public final ArrayList<SchoolInteger> 	robotDesign;
	public final ArrayList<SchoolInteger> 	sportsmanship;
	public final ArrayList<SchoolInteger> 	video;
	public final ArrayList<SchoolInteger> 	websiteDesign;
	public final ArrayList<SchoolInteger> 	websiteJournalism;
	
	public Competition(
			@JsonProperty("_id") 				ObjectId					_competitionId,
			@JsonProperty("kiosk") 				ArrayList<SchoolInteger> 	_kiosk,
			@JsonProperty("programming") 		ArrayList<SchoolInteger> 	_programming,
			@JsonProperty("robotConstruction") 	ArrayList<SchoolInteger> 	_robotConstruction,
			@JsonProperty("robotDesign") 		ArrayList<SchoolInteger> 	_robotDesign,
			@JsonProperty("sportsmanship") 		ArrayList<SchoolInteger> 	_sportsmanship,
			@JsonProperty("video") 				ArrayList<SchoolInteger> 	_video,
			@JsonProperty("websiteDesign") 		ArrayList<SchoolInteger> 	_websiteDesign,
			@JsonProperty("websiteJournalism") 	ArrayList<SchoolInteger> 	_websiteJournalism
			)
	{
		_id 				= _competitionId;
		kiosk 				= _kiosk;
		programming 		= _programming;
		robotConstruction 	= _robotConstruction;
		robotDesign 		= _robotDesign;
		sportsmanship		= _sportsmanship;
		video 				= _video;
		websiteDesign 		= _websiteDesign;
		websiteJournalism 	= _websiteJournalism;
	}
	
	private static int getAspectPoints(ArrayList<School> aspect, School school)
	{
		if(aspect.indexOf(school) == -1)
		{
			return 0;
		}
		return aspect.size() - aspect.indexOf(school);
	}
	
	public static int getSchoolInteger(ArrayList<SchoolInteger> schoolIntegerList, School school)
	{
		return schoolIntegerList.get(schoolIntegerList.indexOf(school)).integer;
	}
	
	public static int getAspectPointInteger(ArrayList<SchoolInteger> schoolIntegerList, School school)
	{
		int intValue = schoolIntegerList.get(schoolIntegerList.indexOf(school)).integer;
		if(intValue == 0)
		{
			return 0;
		}
		return schoolIntegerList.size() - intValue + 1;
	}
	
	
	public int getSchoolScore(ArrayList<School> playoffRanking, School school)
	{
		int score = getAspectPoints(playoffRanking, school) * 2;
		score += getAspectPointInteger(robotDesign, school);
		score += getAspectPointInteger(robotConstruction, school);
		score += getAspectPointInteger(video, school);
		score += getAspectPointInteger(websiteDesign, school);
		score += getAspectPointInteger(websiteJournalism, school);
		score += getAspectPointInteger(kiosk, school);
		score += getAspectPointInteger(sportsmanship, school);
		score += getAspectPointInteger(programming, school);
		
		return score;
	}
	
	public ArrayList<School> getCompetitionRanking(Essentials essentials)
	{
		ArrayList<School> schoolsRanking = new ArrayList<School>(School.getSchools(essentials));
		Tournament tournament = Tournament.getTournament(essentials);
		
		final TreeMap<School, Integer> schoolsScore = new TreeMap<School, Integer>();
		
		for(School school : schoolsRanking)
		{
			schoolsScore.put(school, getSchoolScore(tournament.getPlayoffRanking(), school));
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
	
	public static Competition get(Database database)
	{
		return database.findOne(Competition.class, "{ }");
	}
}
