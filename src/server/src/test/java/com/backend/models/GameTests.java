package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.GameTypeEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;
import com.framework.models.Essentials;

public class GameTests 
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
	
	private Game getFakeGame()
	{
		ArrayList<School> blueTeam = new ArrayList<School>();
		ArrayList<School> yellowTeam = new ArrayList<School>();
		
		blueTeam.add(new School(null, "A"));
		blueTeam.add(new School(null, "B"));
		blueTeam.add(new School(null, "C"));
		
		yellowTeam.add(new School(null, "D"));
		yellowTeam.add(new School(null, "E"));
		yellowTeam.add(new School(null, "F"));
		
		ArrayList<GameEvent> gameEvents = new ArrayList<GameEvent>();
		gameEvents.add(new GameEvent(GameEventEnum.START_GAME));
		gameEvents.add(GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE));
		gameEvents.add(GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.LOW));
		gameEvents.add(new GameEvent(GameEventEnum.END_GAME));
		return new Game(null, 1, DateTime.now(), GameTypeEnum.PRELIMINARY, blueTeam, yellowTeam, gameEvents, false, new ArrayList<SchoolPenalty>(), new ArrayList<School>());
	}
	
	@Test
	public void testSchool()
	{
		Essentials essentials = new Essentials(database, null, null, null, null);
		{
			database.save(getFakeGame());
			
			Validate.isTrue(Game.getGames(essentials).size() == 1);
		}
	}
}
