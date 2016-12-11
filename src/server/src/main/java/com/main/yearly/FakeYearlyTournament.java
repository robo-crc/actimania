package com.main.yearly;

import java.util.Random;

import org.joda.time.DateTime;

import com.backend.models.Game;
import com.backend.models.GameState;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.TeamEnum;
import com.backend.models.yearly.Area;
import com.backend.models.yearly.GameStateYearly;

public class FakeYearlyTournament 
{
	private static Area[] fillArea(Random random)
	{
		Area[] field = GameStateYearly.InitializeField();
		int nbAreaToFill = random.nextInt(8);
		for(int i = 0; i < nbAreaToFill; i++)
		{
			field[random.nextInt(field.length)].spoolCount = random.nextInt(3);
		}
		
		return field;
	}
	
	public static void fillFakeGameEvents(Game currentGame, Random random)
	{
		currentGame.addGameEvent(new StartGameEvent(DateTime.now()));
		
		int nbEvents = random.nextInt(15) + 5;
		
		for(int i = 0; i < nbEvents; i++)
		{
			Area[] yellowField = fillArea(random);
			Area[] blueField = fillArea(random);
			
			int yellowDispenserTop = random.nextInt(9);
			int yellowDispenserBottom = random.nextInt(9);
			int blueDispenserTop = random.nextInt(9);
			int blueDispenserBottom = random.nextInt(9);
			
			int yellowAllowed = 12 + random.nextInt(2);
			int blueAllowed = 12 + random.nextInt(2);
			
			TeamEnum multiplier = TeamEnum.values()[random.nextInt(TeamEnum.values().length)];
			
			currentGame.addGameEvent(new ScoreboardUpdateEvent(yellowField, blueField, yellowDispenserTop, yellowDispenserBottom, blueDispenserTop, blueDispenserBottom, yellowAllowed, blueAllowed, multiplier, DateTime.now()));			
		}
		
		currentGame.addGameEvent(new EndGameEvent(DateTime.now()));
	}
}
