package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.Test;

public class PlayoffTests 
{
	/*
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
	*/
	
	@Test
	public void testGetRounds()
	{
		Validate.isTrue(Playoff.getRoundsCount(16) == 3);
		Validate.isTrue(Playoff.getRoundsCount(20) == 4);
		Validate.isTrue(Playoff.getRoundsCount(30) == 4);
		Validate.isTrue(Playoff.getRoundsCount(32) == 4);
		Validate.isTrue(Playoff.getRoundsCount(33) == 5);
		Validate.isTrue(Playoff.getRoundsCount(40) == 5);
	}
	
	@Test
	public void testGenerateFirstRound()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 1; i <= 30; i++)
		{
			schools.add(new School(new ObjectId(), String.valueOf(i)));
		}
		
		PlayoffRound playoffRound = Playoff.generateDraftRound(schools);
		/*
		Expected
		7	13	24	30
		8	14	23	29
		9	15	22	28
		10	16	21	27
		11	17	20	26
		12	18	19	25
		*/
		
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(0).name.equals("7"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(1).name.equals("13"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(2).name.equals("24"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(3).name.equals("30"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(0).name.equals("8"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(1).name.equals("14"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(2).name.equals("23"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(3).name.equals("29"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(0).name.equals("9"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(1).name.equals("15"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(2).name.equals("22"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(3).name.equals("28"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(0).name.equals("10"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(1).name.equals("16"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(2).name.equals("21"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(3).name.equals("27"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(0).name.equals("11"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(1).name.equals("17"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(2).name.equals("20"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(3).name.equals("26"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(0).name.equals("12"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(1).name.equals("18"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(2).name.equals("19"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(3).name.equals("25"));

		schools.add(new School(new ObjectId(), "31"));
		playoffRound = Playoff.generateDraftRound(schools);
		
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(0).name.equals("7"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(1).name.equals("13"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(2).name.equals("25"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(3).name.equals("31"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(0).name.equals("8"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(1).name.equals("14"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(2).name.equals("24"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(3).name.equals("30"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(0).name.equals("9"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(1).name.equals("15"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(2).name.equals("23"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(3).name.equals("29"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(0).name.equals("10"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(1).name.equals("16"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(2).name.equals("22"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(3).name.equals("28"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(0).name.equals("11"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(1).name.equals("17"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(2).name.equals("21"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(3).name.equals("27"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(0).name.equals("12"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(1).name.equals("18"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(2).name.equals("20"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(3).name.equals("26"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(4).name.equals("19"));
		
		schools.add(new School(new ObjectId(), "32"));
		playoffRound = Playoff.generateDraftRound(schools);
		
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(0).name.equals("7"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(1).name.equals("13"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(2).name.equals("26"));
		Validate.isTrue(playoffRound.playoffGroups.get(0).schools.get(3).name.equals("32"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(0).name.equals("8"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(1).name.equals("14"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(2).name.equals("25"));
		Validate.isTrue(playoffRound.playoffGroups.get(1).schools.get(3).name.equals("31"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(0).name.equals("9"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(1).name.equals("15"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(2).name.equals("24"));
		Validate.isTrue(playoffRound.playoffGroups.get(2).schools.get(3).name.equals("30"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(0).name.equals("10"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(1).name.equals("16"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(2).name.equals("23"));
		Validate.isTrue(playoffRound.playoffGroups.get(3).schools.get(3).name.equals("29"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(0).name.equals("11"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(1).name.equals("17"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(2).name.equals("22"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(3).name.equals("28"));
		Validate.isTrue(playoffRound.playoffGroups.get(4).schools.get(4).name.equals("21"));
		
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(0).name.equals("12"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(1).name.equals("18"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(2).name.equals("21"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(3).name.equals("27"));
		Validate.isTrue(playoffRound.playoffGroups.get(5).schools.get(4).name.equals("20"));
	}
	
	@Test
	public void testGenerateGames()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 1; i <= 30; i++)
		{
			schools.add(new School(new ObjectId(), String.valueOf(i)));
		}
		
		PlayoffRound playoffRound = Playoff.generateDraftRound(schools);
		DateTime dateTime = new DateTime(2015,1,1,0,0);
		ArrayList<Game> games = playoffRound.getGames(dateTime);
		Validate.isTrue(games.size() == 18);

		/*
		for(int i = 0; i < games.size(); i++)
		{
			System.out.println("Validate.isTrue(games.get(" + i + ").blueTeam.get(0).name.equals(\"" + games.get(i).blueTeam.get(0).name + "\"));");
			System.out.println("Validate.isTrue(games.get(" + i + ").blueTeam.get(1).name.equals(\"" + games.get(i).blueTeam.get(1).name + "\"));");
			System.out.println("Validate.isTrue(games.get(" + i + ").yellowTeam.get(0).name.equals(\"" + games.get(i).yellowTeam.get(0).name + "\"));");
			System.out.println("Validate.isTrue(games.get(" + i + ").yellowTeam.get(1).name.equals(\"" + games.get(i).yellowTeam.get(1).name + "\"));\n");
		}
		*/
		
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
		
		
		schools.add(new School(new ObjectId(), "31"));
		
		playoffRound 	= Playoff.generateDraftRound(schools);
		dateTime 		= new DateTime(2015,1,1,0,0);
		games 			= playoffRound.getGames(dateTime);
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
}
