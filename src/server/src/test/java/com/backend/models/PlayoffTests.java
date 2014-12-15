package com.backend.models;

import org.apache.commons.lang.Validate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;

public class PlayoffTests 
{
	/*
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
	*/
	
	@Test
	public void testGetRounds()
	{
		Validate.isTrue(Playoff.getRoundsCount(24) == 3);
		Validate.isTrue(Playoff.getRoundsCount(30) == 4);
		Validate.isTrue(Playoff.getRoundsCount(32) == 4);
		Validate.isTrue(Playoff.getRoundsCount(38) == 5);
		Validate.isTrue(Playoff.getRoundsCount(40) == 5);
	}
}
