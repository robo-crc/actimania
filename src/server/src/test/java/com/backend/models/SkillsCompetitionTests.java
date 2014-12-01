package com.backend.models;

import java.util.TreeMap;

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
		
		TreeMap<School, Integer> score = new TreeMap<School, Integer>();
		score.put(school1, 8);
		score.put(school2, 6);
		score.put(school3, 5);
		score.put(school4, 3);
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.put(school2, 8);
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.put(school3, 8);
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.put(school4, 10);
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 4);
	}
	
	@Test
	public void getSkillPointsDurationTest()
	{
		School school1 = new School(new ObjectId("111111111111111111111111"), "1");
		School school2 = new School(new ObjectId("222222222222222222222222"), "2");
		School school3 = new School(new ObjectId("333333333333333333333333"), "3");
		School school4 = new School(new ObjectId("444444444444444444444444"), "4");
		
		TreeMap<School, Duration> score = new TreeMap<School, Duration>();
		
		score.put(school1, new Duration(500));
		score.put(school2, new Duration(600));
		score.put(school3, new Duration(700));
		score.put(school4, new Duration(800));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.put(school2, new Duration(500));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 4);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 2);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 1);
		
		score.put(school4, new Duration(400));
		
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school1) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school2) == 3);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school3) == 1);
		Validate.isTrue(SkillsCompetition.getSkillPoints(score, school4) == 4);
	}
	/*
	@Test
	public void getDatabaseTest()
	{
		School school1 = new School(new ObjectId("111111111111111111111111"), "1");
		School school2 = new School(new ObjectId("222222222222222222222222"), "2");
		School school3 = new School(new ObjectId("333333333333333333333333"), "3");
		School school4 = new School(new ObjectId("444444444444444444444444"), "4");
		
		TreeMap<School, Duration> twoActuators = new TreeMap<School, Duration>();
		
		twoActuators.put(school1, new Duration(500));
		twoActuators.put(school2, new Duration(600));
		twoActuators.put(school3, new Duration(700));
		twoActuators.put(school4, new Duration(800));
		
		TreeMap<School, Duration> twoTargets = new TreeMap<School, Duration>();
		
		twoTargets.put(school1, new Duration(4500));
		twoTargets.put(school2, new Duration(3600));
		twoTargets.put(school3, new Duration(2700));
		twoTargets.put(school4, new Duration(1800));
		
		TreeMap<School, Integer> pickupBalls = new TreeMap<School, Integer>();
		pickupBalls.put(school1, 8);
		pickupBalls.put(school2, 6);
		pickupBalls.put(school3, 5);
		pickupBalls.put(school4, 3);
		
		SkillsCompetition competition = new SkillsCompetition(null, pickupBalls, twoTargets, twoActuators);
		database.save(competition);
		
		SkillsCompetition competition2 = SkillsCompetition.get(database);
		Validate.isTrue(competition2.getSchoolScore(school1) == 9);
		Validate.isTrue(competition2.getSchoolScore(school2) == 8);
		Validate.isTrue(competition2.getSchoolScore(school2) == 7);
		Validate.isTrue(competition2.getSchoolScore(school2) == 6);
	}
	*/
}
