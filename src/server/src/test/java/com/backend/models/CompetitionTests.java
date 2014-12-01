package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;

public class CompetitionTests 
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
	public void compeitionTest()
	{
		School school1 = new School(new ObjectId("111111111111111111111111"), "1");
		School school2 = new School(new ObjectId("222222222222222222222222"), "2");
		School school3 = new School(new ObjectId("333333333333333333333333"), "3");
		School school4 = new School(new ObjectId("444444444444444444444444"), "4");
		
		ArrayList<School> robotConstruction = new ArrayList<School>();
		robotConstruction.add(school1);
		robotConstruction.add(school2);
		robotConstruction.add(school3);
		robotConstruction.add(school4);
		
		ArrayList<School> robotDesign = new ArrayList<School>();
		robotDesign.add(school4);
		robotDesign.add(school3);
		robotDesign.add(school2);
		robotDesign.add(school1);
		
		ArrayList<School> video = new ArrayList<School>();
		video.add(school2);
		video.add(school3);
		video.add(school1);
		video.add(school4);
		
		ArrayList<School> schools = new ArrayList<School>();
		schools.add(school1);
		schools.add(school2);
		schools.add(school3);
		schools.add(school4);
		
		Validate.isTrue(Competition.getAspectPoints(robotConstruction, school1) == 4);
		Validate.isTrue(Competition.getAspectPoints(robotConstruction, school2) == 3);
		Validate.isTrue(Competition.getAspectPoints(robotConstruction, school3) == 2);
		Validate.isTrue(Competition.getAspectPoints(robotConstruction, school4) == 1);
		
		Validate.isTrue(Competition.getAspectPoints(robotDesign, school1) == 1);
		Validate.isTrue(Competition.getAspectPoints(robotDesign, school2) == 2);
		Validate.isTrue(Competition.getAspectPoints(robotDesign, school3) == 3);
		Validate.isTrue(Competition.getAspectPoints(robotDesign, school4) == 4);
		
		Validate.isTrue(Competition.getAspectPoints(video, school1) == 2);
		Validate.isTrue(Competition.getAspectPoints(video, school2) == 4);
		Validate.isTrue(Competition.getAspectPoints(video, school3) == 3);
		Validate.isTrue(Competition.getAspectPoints(video, school4) == 1);
		
		Competition competition = new Competition(null, 
				new ArrayList<School>(schools), 
				new ArrayList<School>(schools), 
				robotConstruction, 
				robotDesign, 
				new ArrayList<School>(schools), 
				video, 
				new ArrayList<School>(schools), 
				new ArrayList<School>(schools));
		
		// 4 * 2 + 1 + 2 + 4 * 6 = 35
		Validate.isTrue(competition.getSchoolScore(schools, school1) == 35);
		
		// 3 * 2 + 2 + 4 + 3 * 6 = 30
		Validate.isTrue(competition.getSchoolScore(schools, school2) == 30);
		
		// 2 * 2 + 3 + 3 + 2 * 6 = 22
		Validate.isTrue(competition.getSchoolScore(schools, school3) == 22);
		
		// 1 * 2 + 4 + 1 + 1 * 6 = 13
		Validate.isTrue(competition.getSchoolScore(schools, school4) == 13);
		
		// Database test
		database.save(competition);
		
		Competition competition2 = database.findOne(Competition.class, "{ }");
		Validate.isTrue(competition2.getSchoolScore(schools, school1) == 35);
		Validate.isTrue(competition2.getSchoolScore(schools, school2) == 30);
		Validate.isTrue(competition2.getSchoolScore(schools, school3) == 22);
		Validate.isTrue(competition2.getSchoolScore(schools, school4) == 13);
	}
}
