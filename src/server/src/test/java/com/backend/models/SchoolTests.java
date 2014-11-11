package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;
import com.framework.models.Essentials;

public class SchoolTests 
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
	public void testSchool()
	{
		Essentials essentials = new Essentials(database, null, null, null, null);

		ArrayList<School> schools = School.getSchools(essentials);
		Validate.isTrue(schools.size() == 0);
		
		for(int i = 0; i < 5; i++)
		{
			database.save(new School(null, String.valueOf(i)));
		}
		
		schools = School.getSchools(essentials);
		Validate.isTrue(schools.size() == 5);
	}
}
