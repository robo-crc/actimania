package com.backend.models.yearly;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;

public class GameStateYearlyTests 
{
	@BeforeClass
	public static void setUp()
    {
    }
	
	@AfterClass
	public static void tearDown()
	{
	}
	
	@Test
	public void testGameState()
	{
		// Start of a game
		GameStateYearly gameState = new GameStateYearly(null, new StartGameEvent(DateTime.now()));
		
		validateInitialState(gameState);
		
		ScoreboardUpdateEvent scoreboard = new ScoreboardUpdateEvent(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, DateTime.now());
		
		GameStateYearly gameStateUpdate = new GameStateYearly(gameState, scoreboard);
		validateInitialState(gameState);
		validateInitialState(gameStateUpdate);
		
		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 0, 0, 0, 0, DateTime.now());
		GameStateYearly gameStateUpdate2 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate2.blueScore == 50 );
		Validate.isTrue(gameStateUpdate2.yellowScore == 50);

		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 0, 0, 0, DateTime.now());
		GameStateYearly gameStateUpdate3 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate3.blueScore == 110);
		Validate.isTrue(gameStateUpdate3.yellowScore == 50);

		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 2, 0, 0, DateTime.now());
		GameStateYearly gameStateUpdate4 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate4.blueScore == 110);
		Validate.isTrue(gameStateUpdate4.yellowScore == 170);

		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 2, 1, 0, DateTime.now());
		GameStateYearly gameStateUpdate5 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate5.blueScore == 220);
		Validate.isTrue(gameStateUpdate5.yellowScore == 170);
		
		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 2, 0, 1, DateTime.now());
		GameStateYearly gameStateUpdate6 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate6.blueScore == 110);
		Validate.isTrue(gameStateUpdate6.yellowScore == 340);

		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 2, 1, 1, DateTime.now());
		GameStateYearly gameStateUpdate7 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate7.blueScore == 110);
		Validate.isTrue(gameStateUpdate7.yellowScore == 170);

		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 2, 2, 1, DateTime.now());
		GameStateYearly gameStateUpdate8 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate8.blueScore == 220);
		Validate.isTrue(gameStateUpdate8.yellowScore == 170);
		
		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 2, 3, 2, DateTime.now());
		GameStateYearly gameStateUpdate9 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate9.blueScore == 165);
		Validate.isTrue(gameStateUpdate9.yellowScore == 170);
		
		scoreboard = new ScoreboardUpdateEvent(1, 1, 2, 2, 3, 3, 1, 2, 2, 3, DateTime.now());
		GameStateYearly gameStateUpdate10 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate10.blueScore == 110);
		Validate.isTrue(gameStateUpdate10.yellowScore == 255);
	}
	
	private void validateInitialState(GameStateYearly gameState)
	{
		Validate.isTrue(gameState.blueScore == 0);
		Validate.isTrue(gameState.yellowScore == 0);
	}
}
