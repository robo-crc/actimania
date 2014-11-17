package com.backend.models;

import java.util.Random;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.ActuatorStateChangedEvent;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.TargetHitEvent;
import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;
import com.framework.models.Essentials;

public class FakeTournament 
{
	private static Database database;
	
	@BeforeClass
	public static void setUp()
    {
        database = new Database(DatabaseType.PRODUCTION);
        database.initializeDatabase();
    }
	
	@AfterClass
	public static void tearDown()
	{
		//database.dropDatabase();
		database.close();
	}
	
	public static void resetMatches()
	{
		try(Essentials essentials = new Essentials(database, null, null, null, null))
		{
			Tournament tournament = Tournament.getTournament(essentials);
			for(int i = 0; i < tournament.games.size(); i++)
			{
				Game currentGame = tournament.games.get(i).getGameInitialState();
				database.save(currentGame);
			}
		}
	}
	
	@Test
	public void PopulateDatabase()
	{
		Random random = new Random(0);
		Essentials essentials = new Essentials(database, null, null, null, null);

		Tournament tournament = Tournament.getTournament(essentials);
		for(int i = 0; i < tournament.games.size() / 2; i++)
		{
			Game currentGame = tournament.games.get(i).getGameInitialState();
			currentGame.addGameEvent(new StartGameEvent(DateTime.now()));
			
			int nbEvents = random.nextInt(30) + 20;
			
			for(int eventNo = 0; eventNo < nbEvents; eventNo++)
			{
				SideEnum side = SideEnum.values()[random.nextInt(SideEnum.values().length)];
				TargetEnum target = TargetEnum.values()[random.nextInt(TargetEnum.values().length)];
				
				boolean isTargetHit = random.nextBoolean();
				if(isTargetHit)
				{
					currentGame.addGameEvent(new TargetHitEvent(side, target, DateTime.now()));
				}
				else
				{
					ActuatorStateEnum actuator = ActuatorStateEnum.values()[random.nextInt(ActuatorStateEnum.values().length)];
					currentGame.addGameEvent(new ActuatorStateChangedEvent(side, target, actuator, DateTime.now()));
				}
			}
			
			currentGame.addGameEvent(new EndGameEvent(DateTime.now()));
			
			essentials.database.save(currentGame);
		}		
	}
}
