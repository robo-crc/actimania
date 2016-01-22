package com.backend.models;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.PointModifierEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TeamEnum;
import com.framework.helpers.LocalizedString;

public class GameStateTests 
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
		GameState gameState = new GameState(null, new StartGameEvent(DateTime.now()));
		
		validateInitialState(gameState);
/*
		// Target is hit, but all actuator still closed.
		GameState gameState2 = new GameState(gameState, new TargetHitEvent(SideEnum.BLUE, TargetEnum.LOW, DateTime.now()));
		validateInitialState(gameState2);
		
		GameState gameState3 = new GameState(gameState2, new TargetHitEvent(SideEnum.YELLOW, TargetEnum.MID, DateTime.now()));
		validateInitialState(gameState3);
		
		GameState gameState4 = new GameState(gameState3, new TargetHitEvent(SideEnum.BLUE, TargetEnum.HIGH, DateTime.now()));
		validateInitialState(gameState4);
		
		// Let's change one actuator
		GameState gameState5 = new GameState(gameState4, new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE, DateTime.now()));
		Validate.isTrue(gameState5.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.LOW.ordinal()] == ActuatorStateEnum.BLUE);
		
		// YELLOW team is turning it again.
		GameState gameState6 = new GameState(gameState5, new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.YELLOW, DateTime.now()));
		Validate.isTrue(gameState6.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.LOW.ordinal()] == ActuatorStateEnum.YELLOW);
		
		// Ok now the target is hit.
		GameState gameState7 = new GameState(gameState6, new TargetHitEvent(SideEnum.BLUE, TargetEnum.LOW, DateTime.now()));
		Validate.isTrue(gameState7.blueScore == 0);
		Validate.isTrue(gameState7.yellowScore == 10);
		
		// And yellow score again!
		GameState gameState8 = new GameState(gameState7, new TargetHitEvent(SideEnum.BLUE, TargetEnum.LOW, DateTime.now()));
		Validate.isTrue(gameState8.blueScore == 0);
		Validate.isTrue(gameState8.yellowScore == 20);
		
		GameState gameState9 = new GameState(gameState8, new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.MID, ActuatorStateEnum.BLUE, DateTime.now()));
		Validate.isTrue(gameState9.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.MID.ordinal()] == ActuatorStateEnum.BLUE);
		
		GameState gameState10 = new GameState(gameState9, new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.HIGH, ActuatorStateEnum.BLUE, DateTime.now()));
		Validate.isTrue(gameState10.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.HIGH.ordinal()] == ActuatorStateEnum.BLUE);
		
		GameState gameState11 = new GameState(gameState10, new TargetHitEvent(SideEnum.BLUE, TargetEnum.HIGH, DateTime.now()));
		Validate.isTrue(gameState11.blueScore == 80);
		Validate.isTrue(gameState11.yellowScore == 20);
		
		GameState gameState12 = new GameState(gameState11, new TargetHitEvent(SideEnum.BLUE, TargetEnum.LOW, DateTime.now()));
		Validate.isTrue(gameState12.blueScore == 80);
		Validate.isTrue(gameState12.yellowScore == 30);
		
		// Yellow side has no actuator changed yet.
		GameState gameState13 = new GameState(gameState12, new TargetHitEvent(SideEnum.YELLOW, TargetEnum.HIGH, DateTime.now()));
		Validate.isTrue(gameState13.blueScore == 80);
		Validate.isTrue(gameState13.yellowScore == 30);
		
		GameState gameState14 = new GameState(gameState13, new ActuatorStateChangedEvent(SideEnum.YELLOW, TargetEnum.MID, ActuatorStateEnum.YELLOW, DateTime.now()));
		Validate.isTrue(gameState14.blueScore == 80);
		Validate.isTrue(gameState14.yellowScore == 30);
		
		GameState gameState15 = new GameState(gameState14, new TargetHitEvent(SideEnum.YELLOW, TargetEnum.HIGH, DateTime.now()));
		Validate.isTrue(gameState15.blueScore == 80);
		Validate.isTrue(gameState15.yellowScore == 30);
		
		GameState gameState16 = new GameState(gameState15, new TargetHitEvent(SideEnum.YELLOW, TargetEnum.MID, DateTime.now()));
		Validate.isTrue(gameState16.blueScore == 80);
		Validate.isTrue(gameState16.yellowScore == 50);
		
		GameState gameState17 = new GameState(gameState16, new ActuatorStateChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE, DateTime.now()));
		Validate.isTrue(gameState17.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.LOW.ordinal()] == ActuatorStateEnum.BLUE);
		
		GameState gameState18 = new GameState(gameState17, new TargetHitEvent(SideEnum.BLUE, TargetEnum.HIGH, DateTime.now()));
		Validate.isTrue(gameState18.blueScore == 280);
		Validate.isTrue(gameState18.yellowScore == 50);
		
		// GameState18 was a false positive, a robot touched the captor.
		GameState gameState19 = new GameState(gameState18, new PointModifierEvent(TeamEnum.BLUE, -200, new LocalizedString(Locale.ENGLISH, "Salwyn's house robot touch captor", "Le robot de salwyn's house a touché le capteur"), DateTime.now()));
		Validate.isTrue(gameState19.blueScore == 80);
		Validate.isTrue(gameState19.yellowScore == 50);
		
		GameState gameState20 = new GameState(gameState19, new EndGameEvent(DateTime.now()));
		Validate.isTrue(gameState20.blueScore == 80);
		Validate.isTrue(gameState20.yellowScore == 50);
	
		// Make sure values are copied and not referenced.
		validateInitialState(gameState2);
*/
	}
	
	private void validateInitialState(GameState gameState)
	{
		Validate.isTrue(gameState.blueScore == 0);
		Validate.isTrue(gameState.yellowScore == 0);
	}
}
