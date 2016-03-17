package com.main.yearly;

import java.util.Random;

import org.joda.time.DateTime;

import com.backend.models.Game;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.yearly.TriangleStateEnum;
import com.backend.models.yearly.GameStateYearly;
import com.backend.models.yearly.Hole;

public class FakeYearlyTournament 
{
	private static Hole[] fillTriangle(Random random)
	{
		Hole[] triangle = GameStateYearly.InitializeTriangle();
		for(Hole hole : triangle)
		{
			for(int i = 0; i < hole.triangleStates.length; i++)
			{
				hole.triangleStates[i] = TriangleStateEnum.values()[random.nextInt(3)];
				if(hole.triangleStates[i] == TriangleStateEnum.EMPTY)
				{
					break;
				}
			}
		}
		return triangle;
	}
	
	public static void fillFakeGameEvents(Game currentGame, Random random)
	{
		currentGame.addGameEvent(new StartGameEvent(DateTime.now()));
		
		int nbEvents = random.nextInt(30) + 10;
		
		for(int eventNo = 0; eventNo < nbEvents; eventNo++)
		{
			Hole[] triangleLeft = fillTriangle(random);
			Hole[] triangleRight = fillTriangle(random);
			
			currentGame.addGameEvent(new ScoreboardUpdateEvent(triangleLeft, triangleRight, DateTime.now()));
		}
		
		currentGame.addGameEvent(new EndGameEvent(DateTime.now()));
	}
}
