package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.enums.GameTypeEnum;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;

public class PlayoffRoundTests 
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
	public void playoffRoundTest()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 1; i <= 30; i++)
		{
			schools.add(new School(new ObjectId(), String.valueOf(i)));
		}
		
		PlayoffRound playoffRound = Playoff.generateDraftRound(schools);
		
		database.save(playoffRound);
		
		PlayoffRound fromDatabase = PlayoffRound.get(database, GameTypeEnum.PLAYOFF_DRAFT);
		
		Validate.isTrue(fromDatabase.playoffGroups.size() == 6);
	}
}
