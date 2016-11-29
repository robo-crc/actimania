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
import com.backend.models.enums.yearly.AreaPoints;
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
		
		yellowField[AreaPoints.ONE_HUNDRED.ordinal()].spoolCount 	= 1;
		yellowField[AreaPoints.FORTY.ordinal()].spoolCount 			= 1;
		yellowField[AreaPoints.THIRTHY.ordinal()].spoolCount 		= 3;
		yellowField[AreaPoints.TWENTY_BIG.ordinal()].spoolCount 	= 1;
		yellowField[AreaPoints.TWENTY_SMALL.ordinal()].spoolCount 	= 1;
		yellowField[AreaPoints.TEN_TOP.ordinal()].spoolCount 		= 2;
		yellowField[AreaPoints.TEN_BOTTOM.ordinal()].spoolCount 	= 2;
		yellowField[AreaPoints.FIVE_TOP.ordinal()].spoolCount 		= 2;
		yellowField[AreaPoints.FIVE_BOTTOM.ordinal()].spoolCount 	= 0;

		blueField[AreaPoints.ONE_HUNDRED.ordinal()].spoolCount 		= 0;
		blueField[AreaPoints.FORTY.ordinal()].spoolCount 			= 1;
		blueField[AreaPoints.THIRTHY.ordinal()].spoolCount 			= 1;
		blueField[AreaPoints.TWENTY_BIG.ordinal()].spoolCount 		= 0;
		blueField[AreaPoints.TWENTY_SMALL.ordinal()].spoolCount 	= 1;
		blueField[AreaPoints.TEN_TOP.ordinal()].spoolCount 			= 3;
		blueField[AreaPoints.TEN_BOTTOM.ordinal()].spoolCount 		= 2;
		blueField[AreaPoints.FIVE_TOP.ordinal()].spoolCount 		= 1;
		blueField[AreaPoints.FIVE_BOTTOM.ordinal()].spoolCount 		= 4;

		scoreboard = new ScoreboardUpdateEvent(yellowField, blueField, 6, 6, 10, 0, 12, 12, TeamEnum.BLUE, DateTime.now());
		GameStateYearly gameStateUpdate3 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate3.yellowScore == 765);
		Validate.isTrue(gameStateUpdate3.blueScore == 1640);
		
		yellowField[AreaPoints.ONE_HUNDRED.ordinal()].spoolCount 	= 1;
		yellowField[AreaPoints.FORTY.ordinal()].spoolCount 			= 2;
		yellowField[AreaPoints.THIRTHY.ordinal()].spoolCount 		= 3;
		yellowField[AreaPoints.TWENTY_BIG.ordinal()].spoolCount 	= 1;
		yellowField[AreaPoints.TWENTY_SMALL.ordinal()].spoolCount 	= 1;
		yellowField[AreaPoints.TEN_TOP.ordinal()].spoolCount 		= 2;
		yellowField[AreaPoints.TEN_BOTTOM.ordinal()].spoolCount 	= 2;
		yellowField[AreaPoints.FIVE_TOP.ordinal()].spoolCount 		= 1;
		yellowField[AreaPoints.FIVE_BOTTOM.ordinal()].spoolCount 	= 0;

		blueField[AreaPoints.ONE_HUNDRED.ordinal()].spoolCount 		= 4;
		blueField[AreaPoints.FORTY.ordinal()].spoolCount 			= 2;
		blueField[AreaPoints.THIRTHY.ordinal()].spoolCount 			= 2;
		blueField[AreaPoints.TWENTY_BIG.ordinal()].spoolCount 		= 0;
		blueField[AreaPoints.TWENTY_SMALL.ordinal()].spoolCount 	= 1;
		blueField[AreaPoints.TEN_TOP.ordinal()].spoolCount 			= 3;
		blueField[AreaPoints.TEN_BOTTOM.ordinal()].spoolCount 		= 1;
		blueField[AreaPoints.FIVE_TOP.ordinal()].spoolCount 		= 1;
		blueField[AreaPoints.FIVE_BOTTOM.ordinal()].spoolCount 		= 1;

		scoreboard = new ScoreboardUpdateEvent(yellowField, blueField, 8, 6, 4, 6, 14, 12, TeamEnum.YELLOW, DateTime.now());
		GameStateYearly gameStateUpdate4 = new GameStateYearly(gameStateUpdate, scoreboard);
		Validate.isTrue(gameStateUpdate4.yellowScore == 2620);
		Validate.isTrue(gameStateUpdate4.blueScore == 855);

	}
	
	private void validateInitialState(GameStateYearly gameState)
	{
		Validate.isTrue(gameState.blueScore == 0);
		Validate.isTrue(gameState.yellowScore == 0);
	}
}
