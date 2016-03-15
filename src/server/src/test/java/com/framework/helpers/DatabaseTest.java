package com.framework.helpers;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.framework.helpers.Database.DatabaseType;

public class DatabaseTest
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
}
