package com.backend.models;

import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
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
		GameState gameState = new GameState(null, new GameEvent(GameEventEnum.START_GAME));
		
		validateInitialState(gameState);

		// Target is hit, but all actuator still closed.
		GameState gameState2 = new GameState(gameState, GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.LOW));
		validateInitialState(gameState2);
		
		GameState gameState3 = new GameState(gameState2, GameEvent.targetHitEvent(SideEnum.YELLOW, TargetEnum.MID));
		validateInitialState(gameState3);
		
		GameState gameState4 = new GameState(gameState3, GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.HIGH));
		validateInitialState(gameState4);
		
		// Let's change one actuator
		GameState gameState5 = new GameState(gameState4, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE));
		Validate.isTrue(gameState5.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.LOW.ordinal()] == ActuatorStateEnum.BLUE);
		
		// YELLOW team is turning it again.
		GameState gameState6 = new GameState(gameState5, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.YELLOW));
		Validate.isTrue(gameState6.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.LOW.ordinal()] == ActuatorStateEnum.YELLOW);
		
		// Ok now the target is hit.
		GameState gameState7 = new GameState(gameState6, GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.LOW));
		Validate.isTrue(gameState7.blueScore == 0);
		Validate.isTrue(gameState7.yellowScore == 10);
		
		// And yellow score again!
		GameState gameState8 = new GameState(gameState7, GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.LOW));
		Validate.isTrue(gameState8.blueScore == 0);
		Validate.isTrue(gameState8.yellowScore == 20);
		
		GameState gameState9 = new GameState(gameState8, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.MID, ActuatorStateEnum.BLUE));
		Validate.isTrue(gameState9.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.MID.ordinal()] == ActuatorStateEnum.BLUE);
		
		GameState gameState10 = new GameState(gameState9, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.HIGH, ActuatorStateEnum.BLUE));
		Validate.isTrue(gameState10.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.HIGH.ordinal()] == ActuatorStateEnum.BLUE);
		
		GameState gameState11 = new GameState(gameState10, GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.HIGH));
		Validate.isTrue(gameState11.blueScore == 80);
		Validate.isTrue(gameState11.yellowScore == 20);
		
		GameState gameState12 = new GameState(gameState11, GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.LOW));
		Validate.isTrue(gameState12.blueScore == 80);
		Validate.isTrue(gameState12.yellowScore == 30);
		
		// Yellow side has no actuator changed yet.
		GameState gameState13 = new GameState(gameState12, GameEvent.targetHitEvent(SideEnum.YELLOW, TargetEnum.HIGH));
		Validate.isTrue(gameState13.blueScore == 80);
		Validate.isTrue(gameState13.yellowScore == 30);
		
		GameState gameState14 = new GameState(gameState13, GameEvent.actuatorChangedEvent(SideEnum.YELLOW, TargetEnum.MID, ActuatorStateEnum.YELLOW));
		Validate.isTrue(gameState14.blueScore == 80);
		Validate.isTrue(gameState14.yellowScore == 30);
		
		GameState gameState15 = new GameState(gameState14, GameEvent.targetHitEvent(SideEnum.YELLOW, TargetEnum.HIGH));
		Validate.isTrue(gameState15.blueScore == 80);
		Validate.isTrue(gameState15.yellowScore == 30);
		
		GameState gameState16 = new GameState(gameState15, GameEvent.targetHitEvent(SideEnum.YELLOW, TargetEnum.MID));
		Validate.isTrue(gameState16.blueScore == 80);
		Validate.isTrue(gameState16.yellowScore == 50);
		
		GameState gameState17 = new GameState(gameState16, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE));
		Validate.isTrue(gameState17.actuatorsStates[SideEnum.BLUE.ordinal()][TargetEnum.LOW.ordinal()] == ActuatorStateEnum.BLUE);
		
		GameState gameState18 = new GameState(gameState17, GameEvent.targetHitEvent(SideEnum.BLUE, TargetEnum.HIGH));
		Validate.isTrue(gameState18.blueScore == 280);
		Validate.isTrue(gameState18.yellowScore == 50);
		
		// GameState18 was a false positive, a robot touched the captor.
		PointModifier pointModifier = new PointModifier(TeamEnum.BLUE, -200, new LocalizedString(Locale.ENGLISH, "Salwyn's house robot touch captor", "Le robot de salwyn's house a touché le capteur"));
		GameState gameState19 = new GameState(gameState18, GameEvent.pointModifierEvent(pointModifier));
		Validate.isTrue(gameState19.blueScore == 80);
		Validate.isTrue(gameState19.yellowScore == 50);
		
		GameState gameState20 = new GameState(gameState19, new GameEvent(GameEventEnum.END_GAME));
		Validate.isTrue(gameState20.blueScore == 80);
		Validate.isTrue(gameState20.yellowScore == 50);
		
		// Make sure values are copied and not referenced.
		validateInitialState(gameState2);
	}
	
	private void validateInitialState(GameState gameState)
	{
		Validate.isTrue(gameState.blueScore == 0);
		Validate.isTrue(gameState.yellowScore == 0);
		for( SideEnum side : SideEnum.values() )
		{
			for( TargetEnum target : TargetEnum.values() )
			{
				ActuatorStateEnum actuator = gameState.actuatorsStates[side.ordinal()][target.ordinal()];
				Validate.isTrue(actuator == ActuatorStateEnum.CLOSED);
			}
		}
	}
	
	public void testAreAllActuatorSameColor()
	{
		GameState gameState = new GameState(null, new GameEvent(GameEventEnum.START_GAME));
		
		// Make sure that when all actuators are closed, we still get false.
		Validate.isTrue(GameState.areAllActuatorSameColor(gameState.actuatorsStates) == false);
		
		GameState previousGameState = gameState;
		gameState = new GameState(previousGameState, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.LOW, ActuatorStateEnum.BLUE));
		Validate.isTrue(GameState.areAllActuatorSameColor(previousGameState.actuatorsStates) == false);

		previousGameState = gameState;
		gameState = new GameState(previousGameState, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.MID, ActuatorStateEnum.BLUE));
		Validate.isTrue(GameState.areAllActuatorSameColor(previousGameState.actuatorsStates) == false);
		
		previousGameState = gameState;
		gameState = new GameState(previousGameState, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.HIGH, ActuatorStateEnum.BLUE));
		Validate.isTrue(GameState.areAllActuatorSameColor(previousGameState.actuatorsStates) == false);
		
		previousGameState = gameState;
		gameState = new GameState(previousGameState, GameEvent.actuatorChangedEvent(SideEnum.YELLOW, TargetEnum.LOW, ActuatorStateEnum.BLUE));
		Validate.isTrue(GameState.areAllActuatorSameColor(previousGameState.actuatorsStates) == false);
		
		previousGameState = gameState;
		gameState = new GameState(previousGameState, GameEvent.actuatorChangedEvent(SideEnum.YELLOW, TargetEnum.MID, ActuatorStateEnum.BLUE));
		Validate.isTrue(GameState.areAllActuatorSameColor(previousGameState.actuatorsStates) == false);
		
		// Yippi all actuator are the same color!
		previousGameState = gameState;
		gameState = new GameState(previousGameState, GameEvent.actuatorChangedEvent(SideEnum.YELLOW, TargetEnum.HIGH, ActuatorStateEnum.BLUE));
		Validate.isTrue(GameState.areAllActuatorSameColor(previousGameState.actuatorsStates) == true);
		
		previousGameState = gameState;
		gameState = new GameState(previousGameState, GameEvent.actuatorChangedEvent(SideEnum.BLUE, TargetEnum.MID, ActuatorStateEnum.YELLOW));
		Validate.isTrue(GameState.areAllActuatorSameColor(previousGameState.actuatorsStates) == false);
	}
}
