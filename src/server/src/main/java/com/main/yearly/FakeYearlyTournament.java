package com.main.yearly;

import java.util.Random;

import org.joda.time.DateTime;

import com.backend.models.Game;
import com.backend.models.GameState;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.TeamEnum;
import com.backend.models.yearly.GameStateYearly;

public class FakeYearlyTournament 
{
	public static void fillFakeGameEvents(Game currentGame, Random random)
	{
		currentGame.addGameEvent(new StartGameEvent(DateTime.now()));
		
		int nbEvents = random.nextInt(15) + 5;
		
		for(int i = 0; i < nbEvents; i++)
		{
			currentGame.addGameEvent(new ScoreboardUpdateEvent(
					random.nextInt(9),
					random.nextInt(9),
					random.nextInt(9),
					random.nextInt(9),
					random.nextInt(9),
					random.nextInt(9),
					random.nextInt(3),
					random.nextInt(3),
					random.nextInt(9),
					random.nextInt(9),
					DateTime.now()));			
		}
		
		currentGame.addGameEvent(new EndGameEvent(DateTime.now()));
	}
}
