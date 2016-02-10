package com.backend.models;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.GameTypeEnum;
import com.backend.models.enums.TeamEnum;
import com.framework.helpers.Database;
import com.framework.helpers.LocalizedString;
import com.framework.helpers.Database.DatabaseType;
import com.main.TournamentSetup;

public class PlayoffRoundTests 
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
	public void playoffRoundTest()
	{
		Playoff playoff = Playoff.get(database);
		
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 1; i <= 23; i++)
		{
			schools.add(new School(new ObjectId(), String.valueOf(i)));
		}
		
		Tournament tournament = new Tournament(schools, new ArrayList<Game>());
		SkillsCompetition skillsCompetition = TournamentSetup.setupSkillCompetition(schools);
		database.save(skillsCompetition);
		
		PlayoffRound playoffRound = playoff.generatePlayoffRound(database, tournament, null, GameTypeEnum.PLAYOFF_REPECHAGE);

		database.save(playoffRound);
		
		PlayoffRound fromDatabase = PlayoffRound.get(database, GameTypeEnum.PLAYOFF_REPECHAGE);
		
		Validate.isTrue(fromDatabase.playoffGroups.size() == 3);
		Validate.isTrue(fromDatabase.playoffGroups.get(0).schools.size() == 5);
		Validate.isTrue(fromDatabase.playoffGroups.get(0).schools.get(0).name.equals("9"));
		Validate.isTrue(fromDatabase.playoffGroups.get(0).schools.get(1).name.equals("12"));
		Validate.isTrue(fromDatabase.playoffGroups.get(0).schools.get(2).name.equals("17"));
		Validate.isTrue(fromDatabase.playoffGroups.get(0).schools.get(3).name.equals("20"));
		Validate.isTrue(fromDatabase.playoffGroups.get(0).schools.get(4).name.equals("23"));
		
		Validate.isTrue(fromDatabase.playoffGroups.get(1).schools.size() == 5);
		Validate.isTrue(fromDatabase.playoffGroups.get(1).schools.get(0).name.equals("10"));
		Validate.isTrue(fromDatabase.playoffGroups.get(1).schools.get(1).name.equals("13"));
		Validate.isTrue(fromDatabase.playoffGroups.get(1).schools.get(2).name.equals("15"));
		Validate.isTrue(fromDatabase.playoffGroups.get(1).schools.get(3).name.equals("19"));
		Validate.isTrue(fromDatabase.playoffGroups.get(1).schools.get(4).name.equals("22"));
		
		Validate.isTrue(fromDatabase.playoffGroups.get(2).schools.size() == 5);
		Validate.isTrue(fromDatabase.playoffGroups.get(2).schools.get(0).name.equals("11"));
		Validate.isTrue(fromDatabase.playoffGroups.get(2).schools.get(1).name.equals("14"));
		Validate.isTrue(fromDatabase.playoffGroups.get(2).schools.get(2).name.equals("16"));
		Validate.isTrue(fromDatabase.playoffGroups.get(2).schools.get(3).name.equals("18"));
		Validate.isTrue(fromDatabase.playoffGroups.get(2).schools.get(4).name.equals("21"));
		
		tournament.games.addAll(fromDatabase.getGames(new DateTime(), 0));
		PlayoffRound playoffQuarter = playoff.generatePlayoffRound(database, tournament, playoffRound, GameTypeEnum.PLAYOFF_QUARTER);
		Validate.isTrue(playoffQuarter.playoffGroups.size() == 3);
		Validate.isTrue(playoffQuarter.playoffGroups.get(0).schools.size() == 5);
		Validate.isTrue(playoffQuarter.playoffGroups.get(0).schools.get(0).name.equals("3"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(0).schools.get(1).name.equals("6"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(0).schools.get(2).name.equals("11"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(0).schools.get(3).name.equals("12"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(0).schools.get(4).name.equals("15"));
		
		Validate.isTrue(playoffQuarter.playoffGroups.get(1).schools.size() == 5);
		Validate.isTrue(playoffQuarter.playoffGroups.get(1).schools.get(0).name.equals("4"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(1).schools.get(1).name.equals("7"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(1).schools.get(2).name.equals("9"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(1).schools.get(3).name.equals("13"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(1).schools.get(4).name.equals("16"));
		
		Validate.isTrue(playoffQuarter.playoffGroups.get(2).schools.size() == 5);
		Validate.isTrue(playoffQuarter.playoffGroups.get(2).schools.get(0).name.equals("5"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(2).schools.get(1).name.equals("8"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(2).schools.get(2).name.equals("10"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(2).schools.get(3).name.equals("14"));
		Validate.isTrue(playoffQuarter.playoffGroups.get(2).schools.get(4).name.equals("17"));

		tournament.games.addAll(playoffQuarter.getGames(new DateTime(), 0));
		PlayoffRound playoffDemi = playoff.generatePlayoffRound(database, tournament, playoffQuarter, GameTypeEnum.PLAYOFF_DEMI);
		Validate.isTrue(playoffDemi.playoffGroups.size() == 2);
		Validate.isTrue(playoffDemi.playoffGroups.get(0).schools.size() == 4);
		Validate.isTrue(playoffDemi.playoffGroups.get(0).schools.get(0).name.equals("1"));
		Validate.isTrue(playoffDemi.playoffGroups.get(0).schools.get(1).name.equals("6"));
		Validate.isTrue(playoffDemi.playoffGroups.get(0).schools.get(2).name.equals("4"));
		Validate.isTrue(playoffDemi.playoffGroups.get(0).schools.get(3).name.equals("8"));
		
		Validate.isTrue(playoffDemi.playoffGroups.get(1).schools.size() == 4);
		Validate.isTrue(playoffDemi.playoffGroups.get(1).schools.get(0).name.equals("2"));
		Validate.isTrue(playoffDemi.playoffGroups.get(1).schools.get(1).name.equals("3"));
		Validate.isTrue(playoffDemi.playoffGroups.get(1).schools.get(2).name.equals("7"));
		Validate.isTrue(playoffDemi.playoffGroups.get(1).schools.get(3).name.equals("5"));
		
		tournament.games.addAll(playoffDemi.getGames(new DateTime(), 0));
		// Making sure 1 and 6 have the highest score.
		tournament.games.get(30).addGameEvent(new StartGameEvent(DateTime.now()));
		tournament.games.get(30).addGameEvent(new PointModifierEvent(TeamEnum.BLUE, 100, null, DateTime.now()));
		
		// Making sure 5 and 7 have the highest score.
		tournament.games.get(31).addGameEvent(new StartGameEvent(DateTime.now()));
		tournament.games.get(31).addGameEvent(new PointModifierEvent(TeamEnum.YELLOW, 50, null, DateTime.now()));
				
		PlayoffRound playoffFinal = playoff.generatePlayoffRound(database, tournament, playoffDemi, GameTypeEnum.PLAYOFF_FINAL);
		Validate.isTrue(playoffFinal.playoffGroups.size() == 1);
		Validate.isTrue(playoffFinal.playoffGroups.get(0).schools.size() == 4);
		Validate.isTrue(playoffFinal.playoffGroups.get(0).schools.get(0).name.equals("1"));
		Validate.isTrue(playoffFinal.playoffGroups.get(0).schools.get(1).name.equals("6"));
		Validate.isTrue(playoffFinal.playoffGroups.get(0).schools.get(2).name.equals("5"));
		Validate.isTrue(playoffFinal.playoffGroups.get(0).schools.get(3).name.equals("7"));
	}
}
