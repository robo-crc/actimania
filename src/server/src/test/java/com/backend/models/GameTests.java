package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.GameTypeEnum;
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
		gameEvents.add(new StartGameEvent(DateTime.now()));
	/*	gameEvents.add(new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE, DateTime.now()));
		gameEvents.add(new TargetHitEvent(SideEnum.BLUE, TargetEnum.LOW, DateTime.now()));
	*/
		gameEvents.add(new EndGameEvent(DateTime.now()));
		return new Game(null, 1, "", DateTime.now(), GameTypeEnum.PRELIMINARY, blueTeam, yellowTeam, gameEvents, false);
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
	
	@Test
	public void testGameEvent()
	{
		Game game = new Game(null, 0, "", DateTime.now(), GameTypeEnum.PRELIMINARY, new ArrayList<School>(), new ArrayList<School>(), new ArrayList<GameEvent>(), false);
		game.addGameEvent(new StartGameEvent(DateTime.now()));
		
		Validate.isTrue(game.getGameEvents().size() == 1);
		
		game.addGameEvent(new EndGameEvent(DateTime.now()));
		Validate.isTrue(game.getGameEvents().get(0).getGameEventEnum() == GameEventEnum.START_GAME);
		Validate.isTrue(game.getGameEvents().get(1).getGameEventEnum() == GameEventEnum.END_GAME);
		//game.addGameEvent(new TargetHitEvent(SideEnum.BLUE, TargetEnum.MID, DateTime.now()));
		
		Validate.isTrue(game.getGameEvents().size() == 2);
		
		//game.addGameEvent(1, new TargetHitEvent(SideEnum.BLUE, TargetEnum.MID, DateTime.now()));
		
		Validate.isTrue(game.getGameEvents().get(0).getGameEventEnum() == GameEventEnum.START_GAME);
		//Validate.isTrue(game.getGameEvents().get(1).getGameEventEnum() == GameEventEnum.TARGET_HIT);
		Validate.isTrue(game.getGameEvents().get(2).getGameEventEnum() == GameEventEnum.END_GAME);
		
		Game game2 = new Game(null, 0, "", DateTime.now(), GameTypeEnum.PRELIMINARY, new ArrayList<School>(), new ArrayList<School>(), new ArrayList<GameEvent>(), false);
	
		//game2.addGameEvent(new TargetHitEvent(SideEnum.BLUE, TargetEnum.MID, DateTime.now()));
		Validate.isTrue(game2.getGameEvents().size() == 0);
		//game2.addGameEvent(new StartGameEvent(DateTime.now()));
		//game2.addGameEvent(new TargetHitEvent(SideEnum.BLUE, TargetEnum.MID, DateTime.now()));
		Validate.isTrue(game2.getGameEvents().size() == 2);
	}
	
	@Test
	public void testEndGameEvent()
	{
		Game game = new Game(null, 0, "", DateTime.now(), GameTypeEnum.PRELIMINARY, new ArrayList<School>(), new ArrayList<School>(), new ArrayList<GameEvent>(), false);
		game.addGameEvent(new StartGameEvent(DateTime.now()));
		
		Validate.isTrue(!game.containsEndGameEvent());
		//game.addGameEvent(new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE, DateTime.now()));
		Validate.isTrue(!game.containsEndGameEvent());
		//game.addGameEvent(new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.MID, ActuatorStateEnum.BLUE, DateTime.now()));
		Validate.isTrue(!game.containsEndGameEvent());
		//game.addGameEvent(new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.HIGH, ActuatorStateEnum.BLUE, DateTime.now()));
		//game.addGameEvent(new ActuatorStateChangedEvent(SideEnum.YELLOW, TargetEnum.LOW, ActuatorStateEnum.BLUE, DateTime.now()));
		//game.addGameEvent(new ActuatorStateChangedEvent(SideEnum.YELLOW, TargetEnum.MID, ActuatorStateEnum.BLUE, DateTime.now()));
		Validate.isTrue(!game.containsEndGameEvent());
		//game.addGameEvent(new ActuatorStateChangedEvent(SideEnum.YELLOW, TargetEnum.HIGH, ActuatorStateEnum.BLUE, DateTime.now()));
		
		Validate.isTrue(game.containsEndGameEvent());
	}
}
