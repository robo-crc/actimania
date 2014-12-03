package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.bson.types.ObjectId;

import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.models.Essentials;

public class Competition 
{
	public final ObjectId 			_id;
	public final ArrayList<School> 	kiosk;
	public final ArrayList<School> 	programming;
	public final ArrayList<School> 	robotConstruction;
	public final ArrayList<School> 	robotDesign;
	public final ArrayList<School> 	sportsmanship;
	public final ArrayList<School> 	video;
	public final ArrayList<School> 	websiteDesign;
	public final ArrayList<School> 	websiteJournalism;
	
	public Competition(
			@JsonProperty("_id") 				ObjectId			_competitionId,
			@JsonProperty("kiosk") 				ArrayList<School> 	_kiosk,
			@JsonProperty("programming") 		ArrayList<School> 	_programming,
			@JsonProperty("robotConstruction") 	ArrayList<School> 	_robotConstruction,
			@JsonProperty("robotDesign") 		ArrayList<School> 	_robotDesign,
			@JsonProperty("sportsmanship") 		ArrayList<School> 	_sportsmanship,
			@JsonProperty("video") 				ArrayList<School> 	_video,
			@JsonProperty("websiteDesign") 		ArrayList<School> 	_websiteDesign,
			@JsonProperty("websiteJournalism") 	ArrayList<School> 	_websiteJournalism
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
	
	public static int getAspectPoints(ArrayList<School> aspect, School school)
	{
		return aspect.size() - aspect.indexOf(school);
	}
	
	public int getSchoolScore(ArrayList<School> playoffRanking, School school)
	{
		int score = getAspectPoints(playoffRanking, school) * 2;
		score += getAspectPoints(robotDesign, school);
		score += getAspectPoints(robotConstruction, school);
		score += getAspectPoints(video, school);
		score += getAspectPoints(websiteDesign, school);
		score += getAspectPoints(websiteJournalism, school);
		score += getAspectPoints(kiosk, school);
		score += getAspectPoints(sportsmanship, school);
		score += getAspectPoints(programming, school);
		
		return score;
	}
	
	public ArrayList<School> getCompetitionRanking(Essentials essentials)
	{
		ArrayList<School> schoolsRanking = new ArrayList<School>(School.getSchools(essentials));
		Tournament tournament = Tournament.getTournament(essentials);
		
		final TreeMap<School, Integer> schoolsScore = new TreeMap<School, Integer>();
		
		for(School school : schoolsRanking)
		{
			schoolsScore.put(school, getSchoolScore(tournament.getHeatRanking(GameTypeEnum.PLAYOFF), school));
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
	
	public static Competition get(Essentials essentials)
	{
		return essentials.database.findOne(Competition.class, "{ }");
	}
}
