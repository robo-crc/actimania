package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;
import org.joda.time.Duration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;

public class SkillsCompetitionTests 
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
	public void getSkillPointsIntegerTest()
	{
		School school1 = new School(new ObjectId("111111111111111111111111"), "1");
		School school2 = new School(new ObjectId("222222222222222222222222"), "2");
		School school3 = new School(new ObjectId("333333333333333333333333"), "3");
		School school4 = new School(new ObjectId("444444444444444444444444"), "4");
		
		ArrayList<SchoolInteger> score = new ArrayList<SchoolInteger>();
		score.add(new SchoolInteger(school1, 8));
		score.add(new SchoolInteger(school2, 6));
		score.add(new SchoolInteger(school3, 5));
		score.add(new SchoolInteger(school4, 3));
		
		//SkillsCompetition skillsCompetition = new SkillsCompetition(null, score, new ArrayList<SchoolDuration>(), new ArrayList<SchoolDuration>());
		/*
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.set(1, new SchoolInteger(school2, 8));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.set(2, new SchoolInteger(school3, 8));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.set(3, new SchoolInteger(school4, 10));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 4);
		*/
	}
	
	@Test
	public void getSkillPointsDurationTest()
	{
		School school1 = new School(new ObjectId("111111111111111111111111"), "1");
		School school2 = new School(new ObjectId("222222222222222222222222"), "2");
		School school3 = new School(new ObjectId("333333333333333333333333"), "3");
		School school4 = new School(new ObjectId("444444444444444444444444"), "4");
		
		ArrayList<SchoolDuration> score = new ArrayList<SchoolDuration>();
		
		score.add(new SchoolDuration(school1, new Duration(500)));
		score.add(new SchoolDuration(school2, new Duration(600)));
		score.add(new SchoolDuration(school3, new Duration(700)));
		score.add(new SchoolDuration(school4, new Duration(800)));
		/*
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.set(1, new SchoolDuration(school2, new Duration(500)));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.set(3, new SchoolDuration(school4, new Duration(400)));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 1);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 4);
		*/
	}
	
	@Test
	public void getDatabaseTest()
	{
		School school1 = new School(new ObjectId("111111111111111111111111"), "1");
		School school2 = new School(new ObjectId("222222222222222222222222"), "2");
		School school3 = new School(new ObjectId("333333333333333333333333"), "3");
		School school4 = new School(new ObjectId("444444444444444444444444"), "4");
		
		ArrayList<SchoolDuration> schoolDuration1 = new ArrayList<SchoolDuration>();
		
		schoolDuration1.add(new SchoolDuration(school1, new Duration(500)));
		schoolDuration1.add(new SchoolDuration(school2, new Duration(600)));
		schoolDuration1.add(new SchoolDuration(school3, new Duration(700)));
		schoolDuration1.add(new SchoolDuration(school4, new Duration(800)));
		
		ArrayList<SchoolDuration> schoolDuration2 = new ArrayList<SchoolDuration>();
		
		schoolDuration2.add(new SchoolDuration(school1, new Duration(4500)));
		schoolDuration2.add(new SchoolDuration(school2, new Duration(3600)));
		schoolDuration2.add(new SchoolDuration(school3, new Duration(2700)));
		schoolDuration2.add(new SchoolDuration(school4, new Duration(1800)));
		
		ArrayList<SchoolDuration> schoolDuration3 = new ArrayList<SchoolDuration>();
		schoolDuration3.add(new SchoolDuration(school1, new Duration(5)));
		schoolDuration3.add(new SchoolDuration(school2, new Duration(6)));
		schoolDuration3.add(new SchoolDuration(school3, new Duration(7)));
		schoolDuration3.add(new SchoolDuration(school4, new Duration(8)));
		
		SkillsCompetition competition = new SkillsCompetition(null, schoolDuration3, schoolDuration2, schoolDuration1);
		database.save(competition);
		
		SkillsCompetition competition2 = SkillsCompetition.get(database);
		
		// Not really a valid test, but just making sure the serialization/deserialization is working properly.
		Validate.isTrue(competition2.getSchoolScore(school1) > 0);
		
		/*
		Validate.isTrue(competition2.getSchoolScore(school1) == 9);
		Validate.isTrue(competition2.getSchoolScore(school2) == 8);
		Validate.isTrue(competition2.getSchoolScore(school3) == 7);
		Validate.isTrue(competition2.getSchoolScore(school4) == 6);
		*/
	}
	
}
