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
	 *  - Pas 2 game d'affil�
		- Entre 1 et 3 game par block * 4 block. Id�al 2
		- 8 games par �quipe
		- Pas 2 fois en �quipe ou contre 2 fois le m�me robot. Surtout le avec
	 */
	@Test
	public void testSchedualGeneration()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 0; i < 32; i++)
		{
			String iString = String.valueOf(i);
			schools.add(new School(null, iString));
		}
		
		ArrayList<Game> games = Tournament.createPreliminaryRoundSchedual(schools, 3, 4, 3, 0);
		
		for(int i = 0; i < games.size(); i++)
		{
			Validate.isTrue(games.get(i).blueTeam.size() > 0);
			Validate.isTrue(games.get(i).blueTeam.size() == games.get(i).yellowTeam.size());
			ArrayList<School> currentGamesSchool = games.get(i).getSchools();
			
			if( i > 0 )
			{
				// No 2 consecutive games for a school
				for(School previousGameSchool : games.get(i - 1).getSchools())
				{
					Validate.isTrue(currentGamesSchool.contains(previousGameSchool) == false);
				}
			}
		}
		
		// Between 1 and 3 games per block, ideally 2
		
	}
}
