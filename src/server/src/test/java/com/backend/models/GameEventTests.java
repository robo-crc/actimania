package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.TargetHitEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.GameTypeEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;

public class GameEventTests 
{
	private static Database database;
	
	@BeforeClass
	public static void setUp()
    {
        database = new Database(DatabaseType.TEST_DB);
        database.initializeDatabase();
    }
	
	@AfterClass
	public static void tearDown()
	{
		database.dropDatabase();
		database.close();
	}
	
	@Test
	public void gameEventTest()
	{
		ArrayList<GameEvent> gameEvents = new ArrayList<GameEvent>();
		TargetHitEvent targetHit = new TargetHitEvent(SideEnum.YELLOW, TargetEnum.MID, DateTime.now());
		gameEvents.add(targetHit);
		Game game = new Game(null, 0, "", DateTime.now(), GameTypeEnum.PLAYOFF_REPECHAGE, new ArrayList<School>(), new ArrayList<School>(), gameEvents, false);
		database.save(game);
		
		Game gameRetrieved = database.findOne(Game.class, game._id);
		
		Validate.isTrue(gameRetrieved.getGameEvents().size() == 1);
		
		Validate.isTrue(gameRetrieved.getGameEvents().get(0).getGameEventEnum() == GameEventEnum.TARGET_HIT);
		
		TargetHitEvent targetHitRetrieved = (TargetHitEvent)gameRetrieved.getGameEvents().get(0);
		
		Validate.isTrue(targetHitRetrieved.side.equals(targetHit.side));
		Validate.isTrue(targetHitRetrieved.target.equals(targetHit.target));
		Validate.isTrue(targetHitRetrieved.time.getMillis() == targetHit.time.getMillis());
	}
}
