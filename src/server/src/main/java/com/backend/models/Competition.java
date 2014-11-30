package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import org.bson.types.ObjectId;

import com.backend.models.enums.GameTypeEnum;
import com.framework.models.Essentials;

public class Competition 
{
	public final ObjectId 			_id;
	public final ArrayList<School> 	robotConstruction;
	public final ArrayList<School> 	robotDesign;
	public final ArrayList<School> 	video;
	public final ArrayList<School> 	websiteDesign;
	public final ArrayList<School> 	websiteJournalism;
	public final ArrayList<School> 	kiosk;
	public final ArrayList<School> 	sportsmanship;
	public final ArrayList<School> 	programming;
	
	public Competition(
			ObjectId			_competitionId,
			ArrayList<School> 	_robotConstruction,
			ArrayList<School> 	_robotDesign,
			ArrayList<School> 	_video,
			ArrayList<School> 	_websiteDesign,
			ArrayList<School> 	_websiteJournalism,
			ArrayList<School> 	_kiosk,
			ArrayList<School> 	_sportsmanship,
			ArrayList<School> 	_programming
			)
	{
		_id 				= _competitionId;
		robotDesign 		= _robotDesign;
		robotConstruction 	= _robotConstruction;
		video 				= _video;
		websiteDesign 		= _websiteDesign;
		websiteJournalism 	= _websiteJournalism;
		kiosk 				= _kiosk;
		sportsmanship		= _sportsmanship;
		programming 		= _programming;
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
			schoolsScore.put(school, getSchoolScore(tournament.getRanking(GameTypeEnum.PLAYOFF), school));
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
