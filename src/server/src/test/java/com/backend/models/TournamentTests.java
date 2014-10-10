package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TournamentTests 
{
	@BeforeClass
	public static void setUp()
    {
    }
	
	@AfterClass
	public static void tearDown()
	{
	}
	
	/*
	 *  - No 2 consecutive games
		- Between 1 and 3 games per block, ideally 2
		- 8 games per team
		- Do not play 2 times with the same team and ideally not 2 times against the same team.
	 */
	@Test
	public void testSchedualGeneration()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 0; i < 30; i++)
		{
			String iString = String.valueOf(i);
			schools.add(new School(null, iString));
		}
		
		int gamesPerSchool = 4;
		int schoolsPerTeam = 3;
		ArrayList<Game> games = Tournament.createPreliminaryRoundSchedual(schools, gamesPerSchool, 1, schoolsPerTeam, 0);
		int supposedNbGames = schools.size() * gamesPerSchool / (schoolsPerTeam * 2);
		Validate.isTrue(games.size() == supposedNbGames);
		
		for(int i = 0; i < games.size(); i++)
		{
			Validate.isTrue(games.get(i).blueTeam.size() > 0);
			Validate.isTrue(games.get(i).blueTeam.size() == games.get(i).yellowTeam.size());
//			ArrayList<School> currentGamesSchool = games.get(i).getSchools();
			
			if( i > 0 )
			{
				// No 2 consecutive games for a school
//				for(School previousGameSchool : games.get(i - 1).getSchools())
				{
					//Validate.isTrue(currentGamesSchool.contains(previousGameSchool) == false);
				}
			}
		}
		
		// Between 1 and 3 games per block, ideally 2
		
	}
}
