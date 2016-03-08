package com.backend.models.yearly;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.ScoreboardUpdateEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.enums.TeamEnum;
import com.backend.models.enums.TriangleStateEnum;
import com.backend.models.yearly.GameStateYearly;
import com.backend.models.yearly.Hole;
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

		Hole[] triangleLeft = GameStateYearly.InitializeTriangle();
		Hole[] triangleRight = GameStateYearly.InitializeTriangle();

		// Target is hit, but all actuator still closed.
		GameStateYearly gameState2 = new GameStateYearly(gameState, new ScoreboardUpdateEvent(triangleLeft, triangleRight, DateTime.now()));
		validateInitialState(gameState2);
		
		triangleLeft = GameStateYearly.InitializeTriangle();
		triangleRight = GameStateYearly.InitializeTriangle();
		triangleLeft[0].triangleStates[0] = TriangleStateEnum.BLUE;
		triangleRight[1].triangleStates[0] = TriangleStateEnum.YELLOW;
		
		GameStateYearly gameState3 = new GameStateYearly(gameState2, new ScoreboardUpdateEvent(triangleLeft, triangleRight, DateTime.now()));		
		Validate.isTrue(gameState3.blueScore == 40);
		Validate.isTrue(gameState3.yellowScore == 30);
		
		triangleLeft = GameStateYearly.InitializeTriangle();
		triangleRight = GameStateYearly.InitializeTriangle();
		triangleLeft[0].triangleStates[0] = TriangleStateEnum.BLUE;
		triangleLeft[0].triangleStates[1] = TriangleStateEnum.BLUE;
		triangleLeft[0].triangleStates[2] = TriangleStateEnum.BLUE;
		triangleLeft[1].triangleStates[2] = TriangleStateEnum.BLUE;
		triangleLeft[2].triangleStates[2] = TriangleStateEnum.BLUE;
		triangleRight[1].triangleStates[0] = TriangleStateEnum.YELLOW;
		
		GameStateYearly gameState4 = new GameStateYearly(gameState3, new ScoreboardUpdateEvent(triangleLeft, triangleRight, DateTime.now()));	
		Validate.isTrue(gameState4.blueScore == (40 + 40 + 40 + 30 + 30) * 2);
		Validate.isTrue(gameState4.yellowScore == 30);

		// Make sure right triangle don't get the left triangle.
		triangleLeft = GameStateYearly.InitializeTriangle();
		triangleRight = GameStateYearly.InitializeTriangle();
		triangleLeft[0].triangleStates[0] = TriangleStateEnum.BLUE;
		triangleLeft[1].triangleStates[0] = TriangleStateEnum.BLUE;
		triangleLeft[2].triangleStates[0] = TriangleStateEnum.BLUE;
		triangleRight[0].triangleStates[0] = TriangleStateEnum.BLUE;
		
		GameStateYearly gameState5 = new GameStateYearly(gameState4, new ScoreboardUpdateEvent(triangleLeft, triangleRight, DateTime.now()));	
		Validate.isTrue(gameState5.blueScore == (40 + 30 + 30) * 2 + 40);
		Validate.isTrue(gameState5.yellowScore == 0);

		GameStateYearly gameState19 = new GameStateYearly(gameState4, new PointModifierEvent(TeamEnum.BLUE, -200, new LocalizedString(Locale.ENGLISH, "Salwyn's house robot touch captor", "Le robot de salwyn's house a touché le capteur"), DateTime.now()));
		Validate.isTrue(gameState19.blueScore == 360 - 200);
		Validate.isTrue(gameState19.yellowScore == 30);
		
		GameStateYearly gameState20 = new GameStateYearly(gameState19, new EndGameEvent(DateTime.now()));
		Validate.isTrue(gameState20.blueScore == 160);
		Validate.isTrue(gameState20.yellowScore == 30);
	
		// Make sure values are copied and not referenced.
		validateInitialState(gameState2);
		
	}
	
	@Test
	public void ValidateScore()
	{
		Hole[] triangle = GameStateYearly.InitializeTriangle();
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == 0);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 0);
		
		triangle[0].triangleStates[0] = TriangleStateEnum.BLUE;
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == 40);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 0);

		/*
		       0
			  1 2
			 3 4 5
			6 7 8 9
		 */
		triangle[1].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[2].triangleStates[0] = TriangleStateEnum.BLUE;
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (30 + 30 + 40) * 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 0);
		
		triangle[4].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[5].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[8].triangleStates[0] = TriangleStateEnum.YELLOW;
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (30 + 30 + 40) * 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == (20 + 20 + 10) * 2);
		
		triangle[4].triangleStates[1] = TriangleStateEnum.BLUE;

		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (30 + 30 + 40 + 20) * 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == (20 + 20 + 10) * 2);

		triangle[2].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[7].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[9].triangleStates[0] = TriangleStateEnum.YELLOW;

		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 3);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (30 + 40 + 20));
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == (20 + 20 + 10 + 30 + 10 + 10) * 3);

		triangle = GameStateYearly.InitializeTriangle();
		
		/*
	       0
		  1 2
		 3 4 5
		6 7 8 9
	 */

		triangle[0].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[1].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[3].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[4].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[5].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[2].triangleStates[0] = TriangleStateEnum.BLUE;
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 3);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (40 + 30 + 20 + 20 + 20 + 30) * 3);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 0);

		triangle = GameStateYearly.InitializeTriangle();
		triangle[0].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[1].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[3].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[6].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[7].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[8].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[4].triangleStates[0] = TriangleStateEnum.BLUE;
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 3);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (30 + 20 + 10 + 10 + 10 + 20) * 3);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 40);

		// Reset triangle
		triangle = GameStateYearly.InitializeTriangle();
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		
		
		/*
		       0
			  1 2
			 3 4 5
			6 7 8 9
		 */
		// Samples from 2016_Pythagorium_FR.docx case 1
		triangle[6].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[6].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[6].triangleStates[2] = TriangleStateEnum.YELLOW;
		
		triangle[3].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[3].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[3].triangleStates[2] = TriangleStateEnum.BLUE;
		
		triangle[7].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[7].triangleStates[1] = TriangleStateEnum.YELLOW;
		triangle[7].triangleStates[2] = TriangleStateEnum.BLUE;
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == 60);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == (40 + 20) * 2);
		
		// Samples from 2016_Pythagorium_FR.docx case 2
		triangle = GameStateYearly.InitializeTriangle();
		triangle[0].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[0].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[0].triangleStates[2] = TriangleStateEnum.BLUE;

		triangle[1].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[1].triangleStates[1] = TriangleStateEnum.YELLOW;
		triangle[1].triangleStates[2] = TriangleStateEnum.YELLOW;
		
		triangle[2].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[2].triangleStates[1] = TriangleStateEnum.YELLOW;
		triangle[2].triangleStates[2] = TriangleStateEnum.YELLOW;
		
		triangle[3].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[3].triangleStates[1] = TriangleStateEnum.YELLOW;
		
		triangle[4].triangleStates[0] = TriangleStateEnum.YELLOW;
		triangle[4].triangleStates[1] = TriangleStateEnum.YELLOW;
		triangle[4].triangleStates[2] = TriangleStateEnum.BLUE;
		
		triangle[5].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[5].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[5].triangleStates[2] = TriangleStateEnum.YELLOW;
		
		triangle[6].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[6].triangleStates[1] = TriangleStateEnum.YELLOW;
		
		triangle[7].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[7].triangleStates[1] = TriangleStateEnum.YELLOW;
		
		triangle[8].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[8].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[8].triangleStates[2] = TriangleStateEnum.YELLOW;
		
		triangle[9].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[9].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[9].triangleStates[2] = TriangleStateEnum.BLUE;
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 4);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == 1320);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 230);
		
		// Testing edge case
		triangle = GameStateYearly.InitializeTriangle();
		triangle[0].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[0].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[0].triangleStates[2] = TriangleStateEnum.BLUE;
		triangle[0].triangleStates[3] = TriangleStateEnum.BLUE;
		triangle[0].triangleStates[4] = TriangleStateEnum.YELLOW;
		
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == 40 * 4);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 40);
		
		triangle = GameStateYearly.InitializeTriangle();
		triangle[3].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[6].triangleStates[1] = TriangleStateEnum.BLUE;
		triangle[7].triangleStates[1] = TriangleStateEnum.BLUE;

		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (20 + 10 + 10) * 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 0);
		
		triangle = GameStateYearly.InitializeTriangle();
		triangle[3].triangleStates[2] = TriangleStateEnum.BLUE;
		triangle[6].triangleStates[3] = TriangleStateEnum.BLUE;
		triangle[7].triangleStates[4] = TriangleStateEnum.BLUE;

		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (20 + 10 + 10) * 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 0);
		
		triangle[3].triangleStates[1] = TriangleStateEnum.YELLOW;
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == 20 + 10 + 10);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 20);
		
		// 
		triangle = GameStateYearly.InitializeTriangle();
		triangle[4].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[5].triangleStates[0] = TriangleStateEnum.BLUE;
		triangle[8].triangleStates[0] = TriangleStateEnum.BLUE;
		
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.BLUE, triangle) == 2);
		Validate.isTrue(GameStateYearly.GetMultiplier(TriangleStateEnum.YELLOW, triangle) == 1);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.BLUE, triangle) == (20 + 20 + 10) * 2);
		Validate.isTrue(GameStateYearly.GetScore(TeamEnum.YELLOW, triangle) == 0);
	}
	
	private void validateInitialState(GameStateYearly gameState)
	{
		Validate.isTrue(gameState.blueScore == 0);
		Validate.isTrue(gameState.yellowScore == 0);
	}
}
