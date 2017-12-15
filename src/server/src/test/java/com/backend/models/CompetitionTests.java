package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.enums.Division;
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
		SchoolInteger school1 = new SchoolInteger(new School(new ObjectId("111111111111111111111111"), "1"), 1);
		SchoolInteger school2 = new SchoolInteger(new School(new ObjectId("222222222222222222222222"), "2"), 2);
		SchoolInteger school3 = new SchoolInteger(new School(new ObjectId("333333333333333333333333"), "3"), 3);
		SchoolInteger school4 = new SchoolInteger(new School(new ObjectId("444444444444444444444444"), "4"), 4);
		
		ArrayList<SchoolInteger> defaultOrder = new ArrayList<SchoolInteger>();
		defaultOrder.add(school1);
		defaultOrder.add(school2);
		defaultOrder.add(school3);
		defaultOrder.add(school4);
		
		school1 = new SchoolInteger(new School(new ObjectId("111111111111111111111111"), "1"), 4);
		school2 = new SchoolInteger(new School(new ObjectId("222222222222222222222222"), "2"), 3);
		school3 = new SchoolInteger(new School(new ObjectId("333333333333333333333333"), "3"), 2);
		school4 = new SchoolInteger(new School(new ObjectId("444444444444444444444444"), "4"), 1);
		
		ArrayList<SchoolInteger> robotDesign = new ArrayList<SchoolInteger>();
		robotDesign.add(school1);
		robotDesign.add(school2);
		robotDesign.add(school3);
		robotDesign.add(school4);
		
		school1 = new SchoolInteger(new School(new ObjectId("111111111111111111111111"), "1"), 3);
		school2 = new SchoolInteger(new School(new ObjectId("222222222222222222222222"), "2"), 1);
		school3 = new SchoolInteger(new School(new ObjectId("333333333333333333333333"), "3"), 2);
		school4 = new SchoolInteger(new School(new ObjectId("444444444444444444444444"), "4"), 4);
		
		ArrayList<SchoolInteger> video = new ArrayList<SchoolInteger>();
		video.add(school2);
		video.add(school3);
		video.add(school1);
		video.add(school4);
		
		school1 = new SchoolInteger(new School(new ObjectId("111111111111111111111111"), "1"), 1);
		school2 = new SchoolInteger(new School(new ObjectId("222222222222222222222222"), "2"), 1);
		school3 = new SchoolInteger(new School(new ObjectId("333333333333333333333333"), "3"), 3);
		school4 = new SchoolInteger(new School(new ObjectId("444444444444444444444444"), "4"), 0);
		
		ArrayList<SchoolInteger> kiosk = new ArrayList<SchoolInteger>();
		kiosk.add(school1);
		kiosk.add(school2);
		kiosk.add(school3);
		kiosk.add(school4);
		
		ArrayList<SchoolInteger> schools = new ArrayList<SchoolInteger>();
		schools.add(school1);
		schools.add(school2);
		schools.add(school3);
		schools.add(school4);
		
		Validate.isTrue(Competition.getAspectPointInteger(defaultOrder, school1) == 4);
		Validate.isTrue(Competition.getAspectPointInteger(defaultOrder, school2) == 3);
		Validate.isTrue(Competition.getAspectPointInteger(defaultOrder, school3) == 2);
		Validate.isTrue(Competition.getAspectPointInteger(defaultOrder, school4) == 1);
		
		Validate.isTrue(Competition.getAspectPointInteger(robotDesign, school1) == 1);
		Validate.isTrue(Competition.getAspectPointInteger(robotDesign, school2) == 2);
		Validate.isTrue(Competition.getAspectPointInteger(robotDesign, school3) == 3);
		Validate.isTrue(Competition.getAspectPointInteger(robotDesign, school4) == 4);
		
		Validate.isTrue(Competition.getAspectPointInteger(video, school1) == 2);
		Validate.isTrue(Competition.getAspectPointInteger(video, school2) == 4);
		Validate.isTrue(Competition.getAspectPointInteger(video, school3) == 3);
		Validate.isTrue(Competition.getAspectPointInteger(video, school4) == 1);
		
		Competition competition = new Competition(null, 
				kiosk, 
				new ArrayList<SchoolInteger>(defaultOrder), 
				defaultOrder, 
				robotDesign, 
				new ArrayList<SchoolInteger>(defaultOrder), 
				video, 
				new ArrayList<SchoolInteger>(defaultOrder));
		
		// 4 * 2 + 1 + 2 + 4 * 6 = 35
		Validate.isTrue(competition.getSchoolScore(schools, school1) == 35);
		
		// 3 * 2 + 2 + 4 * 2 + 3 * 5 = 31
		Validate.isTrue(competition.getSchoolScore(schools, school2) == 31);
		
		// 2 * 2 + 3 + 3 + 2 * 6 = 22
		Validate.isTrue(competition.getSchoolScore(schools, school3) == 22);
		
		// 1 * 2 + 4 + 1 + 1 * 5 = 12
		Validate.isTrue(competition.getSchoolScore(schools, school4) == 12);
		
		// Database test
		database.save(competition);
		
		Competition competition2 = database.findOne(Competition.class, "{ }");
		Validate.isTrue(competition2.getSchoolScore(schools, school1) == 35);
		Validate.isTrue(competition2.getSchoolScore(schools, school2) == 31);
		Validate.isTrue(competition2.getSchoolScore(schools, school3) == 22);
		Validate.isTrue(competition2.getSchoolScore(schools, school4) == 12);
	}
}
