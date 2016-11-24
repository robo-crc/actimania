package com.backend.models.yearly;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.TeamEnum;
import com.framework.helpers.LocalizedString;

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

		Area[] yellowField = GameStateYearly.InitializeField();
		Area[] blueField = GameStateYearly.InitializeField();
		
		ScoreboardUpdateEvent scoreboard = new ScoreboardUpdateEvent(yellowField, blueField, 0, 0, 0, 0, 12, 12, TeamEnum.NONE, DateTime.now());
		
		GameStateYearly gameStateUpdate = new GameStateYearly(gameState, scoreboard);
		validateInitialState(gameState);
		
		scoreboard = new ScoreboardUpdateEvent(yellowField, blueField, 1, 0, 1, 0, 12, 12, TeamEnum.NONE, DateTime.now());
		GameStateYearly gameStateUpdate2 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate2.yellowScore == 50);
		Validate.isTrue(gameStateUpdate2.blueScore == 50);
	}
	
	private void validateInitialState(GameStateYearly gameState)
	{
		Validate.isTrue(gameState.blueScore == 0);
		Validate.isTrue(gameState.yellowScore == 0);
	}
}
