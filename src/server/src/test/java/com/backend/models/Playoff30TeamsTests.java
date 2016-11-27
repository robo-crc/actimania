package com.backend.models;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.controllers.yearly.Playoff30Teams;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.enums.Division;
import com.backend.models.enums.GameTypeEnum;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;
import com.main.FakeTournament;
import com.main.yearly.FakeYearlyTournament;
import com.main.yearly.TournamentYearlySetup;

public class Playoff30TeamsTests 
{
	private static Database database;
	
	@BeforeClass
	public static void setUp()
    {
        database = new Database(DatabaseType.TEST_DB);
        database.initializeDatabase();
    }
	
	@AfterClass
	public static void tearDown()
	{
		database.dropDatabase();
		database.close();
	}
	
	@Test
	public void testGetRounds()
	{
		Validate.isTrue(Playoff30Teams.getRoundsCount(16) == 3);
		Validate.isTrue(Playoff30Teams.getRoundsCount(20) == 4);
		Validate.isTrue(Playoff30Teams.getRoundsCount(30) == 4);
		Validate.isTrue(Playoff30Teams.getRoundsCount(32) == 4);
		Validate.isTrue(Playoff30Teams.getRoundsCount(33) == 5);
		Validate.isTrue(Playoff30Teams.getRoundsCount(40) == 5);
	}
	
	
	@Test
	public void testGenerateFirstRound()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 1; i <= 30; i++)
		{
			schools.add(new School(new ObjectId(), String.valueOf(i), Division.ONE));
		}

		ArrayList<PlayoffGroup> playoffGroups = Playoff30Teams.generateRepechageRound(schools);
		/*
		Expected
		7	13	24	30
		8	14	23	29
		9	15	22	28
		10	16	21	27
		11	17	20	26
		12	18	19	25
		*/

		Validate.isTrue(playoffGroups.get(0).schools.get(0).name.equals("7"));
		Validate.isTrue(playoffGroups.get(0).schools.get(1).name.equals("13"));
		Validate.isTrue(playoffGroups.get(0).schools.get(2).name.equals("24"));
		Validate.isTrue(playoffGroups.get(0).schools.get(3).name.equals("30"));
		
		Validate.isTrue(playoffGroups.get(1).schools.get(0).name.equals("8"));
		Validate.isTrue(playoffGroups.get(1).schools.get(1).name.equals("14"));
		Validate.isTrue(playoffGroups.get(1).schools.get(2).name.equals("23"));
		Validate.isTrue(playoffGroups.get(1).schools.get(3).name.equals("29"));
		
		Validate.isTrue(playoffGroups.get(2).schools.get(0).name.equals("9"));
		Validate.isTrue(playoffGroups.get(2).schools.get(1).name.equals("15"));
		Validate.isTrue(playoffGroups.get(2).schools.get(2).name.equals("22"));
		Validate.isTrue(playoffGroups.get(2).schools.get(3).name.equals("28"));
		
		Validate.isTrue(playoffGroups.get(3).schools.get(0).name.equals("10"));
		Validate.isTrue(playoffGroups.get(3).schools.get(1).name.equals("16"));
		Validate.isTrue(playoffGroups.get(3).schools.get(2).name.equals("21"));
		Validate.isTrue(playoffGroups.get(3).schools.get(3).name.equals("27"));
		
		Validate.isTrue(playoffGroups.get(4).schools.get(0).name.equals("11"));
		Validate.isTrue(playoffGroups.get(4).schools.get(1).name.equals("17"));
		Validate.isTrue(playoffGroups.get(4).schools.get(2).name.equals("20"));
		Validate.isTrue(playoffGroups.get(4).schools.get(3).name.equals("26"));
		
		Validate.isTrue(playoffGroups.get(5).schools.get(0).name.equals("12"));
		Validate.isTrue(playoffGroups.get(5).schools.get(1).name.equals("18"));
		Validate.isTrue(playoffGroups.get(5).schools.get(2).name.equals("19"));
		Validate.isTrue(playoffGroups.get(5).schools.get(3).name.equals("25"));

		schools.add(new School(new ObjectId(), "31", Division.ONE));
		playoffGroups = Playoff30Teams.generateRepechageRound(schools);
		
		Validate.isTrue(playoffGroups.get(0).schools.get(0).name.equals("7"));
		Validate.isTrue(playoffGroups.get(0).schools.get(1).name.equals("13"));
		Validate.isTrue(playoffGroups.get(0).schools.get(2).name.equals("25"));
		Validate.isTrue(playoffGroups.get(0).schools.get(3).name.equals("31"));
		
		Validate.isTrue(playoffGroups.get(1).schools.get(0).name.equals("8"));
		Validate.isTrue(playoffGroups.get(1).schools.get(1).name.equals("14"));
		Validate.isTrue(playoffGroups.get(1).schools.get(2).name.equals("24"));
		Validate.isTrue(playoffGroups.get(1).schools.get(3).name.equals("30"));
		
		Validate.isTrue(playoffGroups.get(2).schools.get(0).name.equals("9"));
		Validate.isTrue(playoffGroups.get(2).schools.get(1).name.equals("15"));
		Validate.isTrue(playoffGroups.get(2).schools.get(2).name.equals("23"));
		Validate.isTrue(playoffGroups.get(2).schools.get(3).name.equals("29"));
		
		Validate.isTrue(playoffGroups.get(3).schools.get(0).name.equals("10"));
		Validate.isTrue(playoffGroups.get(3).schools.get(1).name.equals("16"));
		Validate.isTrue(playoffGroups.get(3).schools.get(2).name.equals("22"));
		Validate.isTrue(playoffGroups.get(3).schools.get(3).name.equals("28"));
		
		Validate.isTrue(playoffGroups.get(4).schools.get(0).name.equals("11"));
		Validate.isTrue(playoffGroups.get(4).schools.get(1).name.equals("17"));
		Validate.isTrue(playoffGroups.get(4).schools.get(2).name.equals("21"));
		Validate.isTrue(playoffGroups.get(4).schools.get(3).name.equals("27"));
		
		Validate.isTrue(playoffGroups.get(5).schools.get(0).name.equals("12"));
		Validate.isTrue(playoffGroups.get(5).schools.get(1).name.equals("18"));
		Validate.isTrue(playoffGroups.get(5).schools.get(2).name.equals("20"));
		Validate.isTrue(playoffGroups.get(5).schools.get(3).name.equals("26"));
		Validate.isTrue(playoffGroups.get(5).schools.get(4).name.equals("19"));
		
		schools.add(new School(new ObjectId(), "32", Division.ONE));
		playoffGroups = Playoff30Teams.generateRepechageRound(schools);
		
		Validate.isTrue(playoffGroups.get(0).schools.get(0).name.equals("7"));
		Validate.isTrue(playoffGroups.get(0).schools.get(1).name.equals("13"));
		Validate.isTrue(playoffGroups.get(0).schools.get(2).name.equals("26"));
		Validate.isTrue(playoffGroups.get(0).schools.get(3).name.equals("32"));
		
		Validate.isTrue(playoffGroups.get(1).schools.get(0).name.equals("8"));
		Validate.isTrue(playoffGroups.get(1).schools.get(1).name.equals("14"));
		Validate.isTrue(playoffGroups.get(1).schools.get(2).name.equals("25"));
		Validate.isTrue(playoffGroups.get(1).schools.get(3).name.equals("31"));
		
		Validate.isTrue(playoffGroups.get(2).schools.get(0).name.equals("9"));
		Validate.isTrue(playoffGroups.get(2).schools.get(1).name.equals("15"));
		Validate.isTrue(playoffGroups.get(2).schools.get(2).name.equals("24"));
		Validate.isTrue(playoffGroups.get(2).schools.get(3).name.equals("30"));
		
		Validate.isTrue(playoffGroups.get(3).schools.get(0).name.equals("10"));
		Validate.isTrue(playoffGroups.get(3).schools.get(1).name.equals("16"));
		Validate.isTrue(playoffGroups.get(3).schools.get(2).name.equals("23"));
		Validate.isTrue(playoffGroups.get(3).schools.get(3).name.equals("29"));
		
		Validate.isTrue(playoffGroups.get(4).schools.get(0).name.equals("11"));
		Validate.isTrue(playoffGroups.get(4).schools.get(1).name.equals("17"));
		Validate.isTrue(playoffGroups.get(4).schools.get(2).name.equals("22"));
		Validate.isTrue(playoffGroups.get(4).schools.get(3).name.equals("28"));
		Validate.isTrue(playoffGroups.get(4).schools.get(4).name.equals("21"));
		
		Validate.isTrue(playoffGroups.get(5).schools.get(0).name.equals("12"));
		Validate.isTrue(playoffGroups.get(5).schools.get(1).name.equals("18"));
		Validate.isTrue(playoffGroups.get(5).schools.get(2).name.equals("21"));
		Validate.isTrue(playoffGroups.get(5).schools.get(3).name.equals("27"));
		Validate.isTrue(playoffGroups.get(5).schools.get(4).name.equals("20"));
	}
	
	@Test
	public void testGenerateGames()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 1; i <= 30; i++)
		{
			schools.add(new School(new ObjectId(), String.valueOf(i), Division.ONE));
		}

		ArrayList<PlayoffGroup> repechageGroups = Playoff30Teams.generateRepechageRound(schools);
		DateTime dateTime = new DateTime(2015,1,1,0,0);
		
		PlayoffRound playoffRound = new PlayoffRound(null, repechageGroups, GameTypeEnum.PLAYOFF_REPECHAGE);
		ArrayList<Game> games = playoffRound.getGames(dateTime, 0);
		Validate.isTrue(games.size() == 18);

		Validate.isTrue(games.get(0).blueTeam.get(0).name.equals("7"));
		Validate.isTrue(games.get(0).blueTeam.get(1).name.equals("13"));
		Validate.isTrue(games.get(0).yellowTeam.get(0).name.equals("24"));
		Validate.isTrue(games.get(0).yellowTeam.get(1).name.equals("30"));

		Validate.isTrue(games.get(1).blueTeam.get(0).name.equals("8"));
		Validate.isTrue(games.get(1).blueTeam.get(1).name.equals("14"));
		Validate.isTrue(games.get(1).yellowTeam.get(0).name.equals("23"));
		Validate.isTrue(games.get(1).yellowTeam.get(1).name.equals("29"));

		Validate.isTrue(games.get(2).blueTeam.get(0).name.equals("9"));
		Validate.isTrue(games.get(2).blueTeam.get(1).name.equals("15"));
		Validate.isTrue(games.get(2).yellowTeam.get(0).name.equals("22"));
		Validate.isTrue(games.get(2).yellowTeam.get(1).name.equals("28"));

		Validate.isTrue(games.get(3).blueTeam.get(0).name.equals("10"));
		Validate.isTrue(games.get(3).blueTeam.get(1).name.equals("16"));
		Validate.isTrue(games.get(3).yellowTeam.get(0).name.equals("21"));
		Validate.isTrue(games.get(3).yellowTeam.get(1).name.equals("27"));

		Validate.isTrue(games.get(4).blueTeam.get(0).name.equals("11"));
		Validate.isTrue(games.get(4).blueTeam.get(1).name.equals("17"));
		Validate.isTrue(games.get(4).yellowTeam.get(0).name.equals("20"));
		Validate.isTrue(games.get(4).yellowTeam.get(1).name.equals("26"));

		Validate.isTrue(games.get(5).blueTeam.get(0).name.equals("12"));
		Validate.isTrue(games.get(5).blueTeam.get(1).name.equals("18"));
		Validate.isTrue(games.get(5).yellowTeam.get(0).name.equals("19"));
		Validate.isTrue(games.get(5).yellowTeam.get(1).name.equals("25"));

		Validate.isTrue(games.get(6).blueTeam.get(0).name.equals("7"));
		Validate.isTrue(games.get(6).blueTeam.get(1).name.equals("24"));
		Validate.isTrue(games.get(6).yellowTeam.get(0).name.equals("13"));
		Validate.isTrue(games.get(6).yellowTeam.get(1).name.equals("30"));

		Validate.isTrue(games.get(7).blueTeam.get(0).name.equals("8"));
		Validate.isTrue(games.get(7).blueTeam.get(1).name.equals("23"));
		Validate.isTrue(games.get(7).yellowTeam.get(0).name.equals("14"));
		Validate.isTrue(games.get(7).yellowTeam.get(1).name.equals("29"));

		Validate.isTrue(games.get(8).blueTeam.get(0).name.equals("9"));
		Validate.isTrue(games.get(8).blueTeam.get(1).name.equals("22"));
		Validate.isTrue(games.get(8).yellowTeam.get(0).name.equals("15"));
		Validate.isTrue(games.get(8).yellowTeam.get(1).name.equals("28"));

		Validate.isTrue(games.get(9).blueTeam.get(0).name.equals("10"));
		Validate.isTrue(games.get(9).blueTeam.get(1).name.equals("21"));
		Validate.isTrue(games.get(9).yellowTeam.get(0).name.equals("16"));
		Validate.isTrue(games.get(9).yellowTeam.get(1).name.equals("27"));

		Validate.isTrue(games.get(10).blueTeam.get(0).name.equals("11"));
		Validate.isTrue(games.get(10).blueTeam.get(1).name.equals("20"));
		Validate.isTrue(games.get(10).yellowTeam.get(0).name.equals("17"));
		Validate.isTrue(games.get(10).yellowTeam.get(1).name.equals("26"));

		Validate.isTrue(games.get(11).blueTeam.get(0).name.equals("12"));
		Validate.isTrue(games.get(11).blueTeam.get(1).name.equals("19"));
		Validate.isTrue(games.get(11).yellowTeam.get(0).name.equals("18"));
		Validate.isTrue(games.get(11).yellowTeam.get(1).name.equals("25"));

		Validate.isTrue(games.get(12).blueTeam.get(0).name.equals("7"));
		Validate.isTrue(games.get(12).blueTeam.get(1).name.equals("30"));
		Validate.isTrue(games.get(12).yellowTeam.get(0).name.equals("13"));
		Validate.isTrue(games.get(12).yellowTeam.get(1).name.equals("24"));

		Validate.isTrue(games.get(13).blueTeam.get(0).name.equals("8"));
		Validate.isTrue(games.get(13).blueTeam.get(1).name.equals("29"));
		Validate.isTrue(games.get(13).yellowTeam.get(0).name.equals("14"));
		Validate.isTrue(games.get(13).yellowTeam.get(1).name.equals("23"));

		Validate.isTrue(games.get(14).blueTeam.get(0).name.equals("9"));
		Validate.isTrue(games.get(14).blueTeam.get(1).name.equals("28"));
		Validate.isTrue(games.get(14).yellowTeam.get(0).name.equals("15"));
		Validate.isTrue(games.get(14).yellowTeam.get(1).name.equals("22"));

		Validate.isTrue(games.get(15).blueTeam.get(0).name.equals("10"));
		Validate.isTrue(games.get(15).blueTeam.get(1).name.equals("27"));
		Validate.isTrue(games.get(15).yellowTeam.get(0).name.equals("16"));
		Validate.isTrue(games.get(15).yellowTeam.get(1).name.equals("21"));

		Validate.isTrue(games.get(16).blueTeam.get(0).name.equals("11"));
		Validate.isTrue(games.get(16).blueTeam.get(1).name.equals("26"));
		Validate.isTrue(games.get(16).yellowTeam.get(0).name.equals("17"));
		Validate.isTrue(games.get(16).yellowTeam.get(1).name.equals("20"));

		Validate.isTrue(games.get(17).blueTeam.get(0).name.equals("12"));
		Validate.isTrue(games.get(17).blueTeam.get(1).name.equals("25"));
		Validate.isTrue(games.get(17).yellowTeam.get(0).name.equals("18"));
		Validate.isTrue(games.get(17).yellowTeam.get(1).name.equals("19"));
		
		
		schools.add(new School(new ObjectId(), "31", Division.ONE));
		
		repechageGroups = Playoff30Teams.generateRepechageRound(schools);		
		playoffRound = new PlayoffRound(null, repechageGroups, GameTypeEnum.PLAYOFF_REPECHAGE);

		dateTime 		= new DateTime(2015,1,1,0,0);
		games 			= playoffRound.getGames(dateTime, 0);
		Validate.isTrue(games.size() == 20);
		
		Validate.isTrue(games.get(0).blueTeam.get(0).name.equals("7"));
		Validate.isTrue(games.get(0).blueTeam.get(1).name.equals("13"));
		Validate.isTrue(games.get(0).yellowTeam.get(0).name.equals("25"));
		Validate.isTrue(games.get(0).yellowTeam.get(1).name.equals("31"));

		Validate.isTrue(games.get(1).blueTeam.get(0).name.equals("8"));
		Validate.isTrue(games.get(1).blueTeam.get(1).name.equals("14"));
		Validate.isTrue(games.get(1).yellowTeam.get(0).name.equals("24"));
		Validate.isTrue(games.get(1).yellowTeam.get(1).name.equals("30"));

		Validate.isTrue(games.get(2).blueTeam.get(0).name.equals("12"));
		Validate.isTrue(games.get(2).blueTeam.get(1).name.equals("26"));
		Validate.isTrue(games.get(2).yellowTeam.get(0).name.equals("18"));
		Validate.isTrue(games.get(2).yellowTeam.get(1).name.equals("20"));

		Validate.isTrue(games.get(3).blueTeam.get(0).name.equals("9"));
		Validate.isTrue(games.get(3).blueTeam.get(1).name.equals("15"));
		Validate.isTrue(games.get(3).yellowTeam.get(0).name.equals("23"));
		Validate.isTrue(games.get(3).yellowTeam.get(1).name.equals("29"));

		Validate.isTrue(games.get(4).blueTeam.get(0).name.equals("10"));
		Validate.isTrue(games.get(4).blueTeam.get(1).name.equals("16"));
		Validate.isTrue(games.get(4).yellowTeam.get(0).name.equals("22"));
		Validate.isTrue(games.get(4).yellowTeam.get(1).name.equals("28"));

		Validate.isTrue(games.get(5).blueTeam.get(0).name.equals("11"));
		Validate.isTrue(games.get(5).blueTeam.get(1).name.equals("17"));
		Validate.isTrue(games.get(5).yellowTeam.get(0).name.equals("21"));
		Validate.isTrue(games.get(5).yellowTeam.get(1).name.equals("27"));

		Validate.isTrue(games.get(6).blueTeam.get(0).name.equals("12"));
		Validate.isTrue(games.get(6).blueTeam.get(1).name.equals("19"));
		Validate.isTrue(games.get(6).yellowTeam.get(0).name.equals("18"));
		Validate.isTrue(games.get(6).yellowTeam.get(1).name.equals("26"));

		Validate.isTrue(games.get(7).blueTeam.get(0).name.equals("7"));
		Validate.isTrue(games.get(7).blueTeam.get(1).name.equals("25"));
		Validate.isTrue(games.get(7).yellowTeam.get(0).name.equals("13"));
		Validate.isTrue(games.get(7).yellowTeam.get(1).name.equals("31"));

		Validate.isTrue(games.get(8).blueTeam.get(0).name.equals("8"));
		Validate.isTrue(games.get(8).blueTeam.get(1).name.equals("24"));
		Validate.isTrue(games.get(8).yellowTeam.get(0).name.equals("14"));
		Validate.isTrue(games.get(8).yellowTeam.get(1).name.equals("30"));

		Validate.isTrue(games.get(9).blueTeam.get(0).name.equals("12"));
		Validate.isTrue(games.get(9).blueTeam.get(1).name.equals("20"));
		Validate.isTrue(games.get(9).yellowTeam.get(0).name.equals("26"));
		Validate.isTrue(games.get(9).yellowTeam.get(1).name.equals("19"));

		Validate.isTrue(games.get(10).blueTeam.get(0).name.equals("9"));
		Validate.isTrue(games.get(10).blueTeam.get(1).name.equals("23"));
		Validate.isTrue(games.get(10).yellowTeam.get(0).name.equals("15"));
		Validate.isTrue(games.get(10).yellowTeam.get(1).name.equals("29"));

		Validate.isTrue(games.get(11).blueTeam.get(0).name.equals("10"));
		Validate.isTrue(games.get(11).blueTeam.get(1).name.equals("22"));
		Validate.isTrue(games.get(11).yellowTeam.get(0).name.equals("16"));
		Validate.isTrue(games.get(11).yellowTeam.get(1).name.equals("28"));

		Validate.isTrue(games.get(12).blueTeam.get(0).name.equals("11"));
		Validate.isTrue(games.get(12).blueTeam.get(1).name.equals("21"));
		Validate.isTrue(games.get(12).yellowTeam.get(0).name.equals("17"));
		Validate.isTrue(games.get(12).yellowTeam.get(1).name.equals("27"));

		Validate.isTrue(games.get(13).blueTeam.get(0).name.equals("18"));
		Validate.isTrue(games.get(13).blueTeam.get(1).name.equals("19"));
		Validate.isTrue(games.get(13).yellowTeam.get(0).name.equals("20"));
		Validate.isTrue(games.get(13).yellowTeam.get(1).name.equals("26"));

		Validate.isTrue(games.get(14).blueTeam.get(0).name.equals("7"));
		Validate.isTrue(games.get(14).blueTeam.get(1).name.equals("31"));
		Validate.isTrue(games.get(14).yellowTeam.get(0).name.equals("13"));
		Validate.isTrue(games.get(14).yellowTeam.get(1).name.equals("25"));

		Validate.isTrue(games.get(15).blueTeam.get(0).name.equals("8"));
		Validate.isTrue(games.get(15).blueTeam.get(1).name.equals("30"));
		Validate.isTrue(games.get(15).yellowTeam.get(0).name.equals("14"));
		Validate.isTrue(games.get(15).yellowTeam.get(1).name.equals("24"));

		Validate.isTrue(games.get(16).blueTeam.get(0).name.equals("12"));
		Validate.isTrue(games.get(16).blueTeam.get(1).name.equals("18"));
		Validate.isTrue(games.get(16).yellowTeam.get(0).name.equals("20"));
		Validate.isTrue(games.get(16).yellowTeam.get(1).name.equals("19"));

		Validate.isTrue(games.get(17).blueTeam.get(0).name.equals("9"));
		Validate.isTrue(games.get(17).blueTeam.get(1).name.equals("29"));
		Validate.isTrue(games.get(17).yellowTeam.get(0).name.equals("15"));
		Validate.isTrue(games.get(17).yellowTeam.get(1).name.equals("23"));

		Validate.isTrue(games.get(18).blueTeam.get(0).name.equals("10"));
		Validate.isTrue(games.get(18).blueTeam.get(1).name.equals("28"));
		Validate.isTrue(games.get(18).yellowTeam.get(0).name.equals("16"));
		Validate.isTrue(games.get(18).yellowTeam.get(1).name.equals("22"));

		Validate.isTrue(games.get(19).blueTeam.get(0).name.equals("11"));
		Validate.isTrue(games.get(19).blueTeam.get(1).name.equals("27"));
		Validate.isTrue(games.get(19).yellowTeam.get(0).name.equals("17"));
		Validate.isTrue(games.get(19).yellowTeam.get(1).name.equals("21"));
	}
	
	
	@Test
	// Test the expected flow.
	public void testPlayoffSteps()
	{
		ArrayList<School> schools = new ArrayList<School>();
				
		for(int i = 1; i <= 31; i++)
		{
			String id = "";
			for(int j = 0; j < 12; j++)
			{
				if(i < 10)
				{
					id += 0;
				}
				id += String.valueOf(i);
			}
			School school = new School(new ObjectId(id), String.valueOf(i), Division.ONE);
			schools.add(school);
		}
		
		Random random = new Random(0);
		SkillsCompetition skillsCompetition = TournamentYearlySetup.setupSkillCompetition(null, schools, false);
		
		ArrayList<Game> games = new ArrayList<Game>();
		for(int i = 0; i < 64; i++)
		{
			ArrayList<School> blueTeam = new ArrayList<School>();
			blueTeam.add(schools.get(random.nextInt(31)));
			blueTeam.add(schools.get(random.nextInt(31)));
			blueTeam.add(schools.get(random.nextInt(31)));
			
			ArrayList<School> yellowTeam = new ArrayList<School>();
			blueTeam.add(schools.get(random.nextInt(31)));
			blueTeam.add(schools.get(random.nextInt(31)));
			blueTeam.add(schools.get(random.nextInt(31)));
			
			Game game = new Game(new ObjectId(), i, "", new DateTime(), GameTypeEnum.PRELIMINARY, blueTeam, yellowTeam, new ArrayList<GameEvent>(), false);
			FakeYearlyTournament.fillFakeGameEvents(game, random);
			games.add(game);
		}
		
		Tournament tournament = new Tournament(schools, games);
		ArrayList<School> preliminaryRank = tournament.getPreliminaryRanking(skillsCompetition);
		ArrayList<School> excludedSchools = new ArrayList<School>();
		School excludedSchool = schools.get(2);
		excludedSchools.add(excludedSchool);
		
		Playoff playoff = new Playoff(null, excludedSchools, new Playoff30Teams());
		/*
		System.out.println("PRELIMINARY RANKING");
		for(School school : playoff.preliminaryRanking)
		{
			System.out.println(school.name);
		}
		*/
		PlayoffRound repechageRound = FakeTournament.processRound(null, playoff, tournament, null, random, GameTypeEnum.PLAYOFF_REPECHAGE);
		PlayoffRound quarterRound = FakeTournament.processRound(null, playoff, tournament, repechageRound, random, GameTypeEnum.PLAYOFF_QUARTER);
		PlayoffRound demiRound = FakeTournament.processRound(null, playoff, tournament, quarterRound, random, GameTypeEnum.PLAYOFF_DEMI);
		PlayoffRound finalRound = FakeTournament.processRound(null, playoff, tournament, demiRound, random, GameTypeEnum.PLAYOFF_FINAL);
		
		//System.out.println("FINAL RANKING");
		ArrayList<SchoolInteger> finalRanking = tournament.getPlayoffRanking();
		for(School school : finalRanking)
		{
			//System.out.println(school.name);
			// Make sure no duplicates happens.
			Validate.isTrue(finalRanking.indexOf(school) == finalRanking.lastIndexOf(school));
		}
		
		ArrayList<School> finalSchools = finalRound.getSchools();
		ArrayList<School> demiSchools = demiRound.getSchools();
		ArrayList<School> semiSchools = quarterRound.getSchools();
		ArrayList<School> draftSchools = repechageRound.getSchools();

		Validate.isTrue(finalSchools.size() == 4);
		Validate.isTrue(demiSchools.size() == 10);
		Validate.isTrue(semiSchools.size() == 16);
		Validate.isTrue(draftSchools.size() == 24);
		Validate.isTrue(draftSchools.contains(excludedSchool) == false);
		Validate.isTrue(finalRanking.indexOf(excludedSchool) == 30);
		
		for(School school : finalSchools)
		{
			// Make sure the school was in previous round.
			Validate.isTrue(demiSchools.contains(school));
			// Make sure this school is unique.
			Validate.isTrue(finalSchools.indexOf(school) == finalSchools.lastIndexOf(school));
		}

		
		for(School school : demiSchools)
		{
			boolean freePass = school.equals(preliminaryRank.get(0));
			freePass |= school.equals(preliminaryRank.get(1));
			Validate.isTrue(semiSchools.contains(school) || freePass);
			
			Validate.isTrue(demiSchools.indexOf(school) == demiSchools.lastIndexOf(school));
		}
		
		for(School school : semiSchools)
		{
			boolean freePass = school.equals(preliminaryRank.get(2));
			freePass |= school.equals(preliminaryRank.get(3));
			freePass |= school.equals(preliminaryRank.get(4));
			freePass |= school.equals(preliminaryRank.get(5));
			
			Validate.isTrue(draftSchools.contains(school) || freePass);
			
			Validate.isTrue(semiSchools.indexOf(school) == semiSchools.lastIndexOf(school));
		}
		
		for(School school : draftSchools)
		{
			Validate.isTrue(draftSchools.indexOf(school) == draftSchools.lastIndexOf(school));
		}
	}
	
	void testDatabase()
	{
		
	}
}
