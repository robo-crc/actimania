package com.main.yearly;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.backend.models.ISchoolScore;
import com.backend.models.School;
import com.backend.models.SchoolDuration;
import com.backend.models.SchoolFloatSmallest;
import com.backend.models.SchoolInteger;
import com.backend.models.Skill;
import com.backend.models.SkillsCompetition;
import com.backend.models.Tournament;
import com.framework.helpers.LocalizedString;
import com.google.common.collect.ImmutableMap;

public class TournamentYearlySetup 
{
	public static DateTime[] getRoundStartTime()
	{
		DateTime[] roundStartHour = new DateTime[Tournament.BLOCK_NUMBERS];
		roundStartHour[0] = new DateTime(2017, 2, 16, 18, 30);
		roundStartHour[1] = new DateTime(2017, 2, 17, 9, 0);
		roundStartHour[2] = new DateTime(2017, 2, 17, 13, 00);
		roundStartHour[3] = new DateTime(2017, 2, 17, 18, 00);
		
		return roundStartHour;
	}
	
	private static final LocalizedString strEmptyCharger = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "Empty charger", 
			Locale.FRENCH, 	"Vider le chargeur"
			), Locale.ENGLISH);

	private static final LocalizedString strClosestToTarget = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "Closest to target", 
			Locale.FRENCH, 	"Le plus proche de la cible"
			), Locale.ENGLISH);

	private static final LocalizedString strQuickest = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "Quickest", 
			Locale.FRENCH, 	"Le plus rapide"
			), Locale.ENGLISH);
	
	private static final LocalizedString strEmptyChargerCompact = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "EMPTY<BR/>CHARGER", 
			Locale.FRENCH, 	"VIDER<BR/>LE CHARGEUR"
			), Locale.ENGLISH);

	private static final LocalizedString strClosestToTargetCompact = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "CLOSEST TO<BR/>TARGET", 
			Locale.FRENCH, 	"LE PLUS PROCHE<BR/>DE LA CIBLE"
			), Locale.ENGLISH);

	private static final LocalizedString strQuickestCompact = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "QUICKEST", 
			Locale.FRENCH, 	"LE PLUS RAPIDE"
			), Locale.ENGLISH);
	
	public static SkillsCompetition setupSkillCompetition(ObjectId id, ArrayList<School> schools, boolean generateRandom)
	{
		ArrayList<Skill> skills = new ArrayList<Skill>();
		
		Random random = null;
		if(generateRandom)
		{
			random = new Random(0);
		}
		
		skills.add(SetupSkillDuration(schools, strEmptyCharger, 		strEmptyChargerCompact, 	"emptyCharger", random));
		skills.add(SetupSkillFloatSmallest(schools, strClosestToTarget, strClosestToTargetCompact,	"closestToTarget", random));
		skills.add(SetupSkillDuration(schools, strQuickest, 			strQuickestCompact, 		"quickest", random));
		
		SkillsCompetition skillsCompetition = new SkillsCompetition(
				id,
				skills);
		
		return skillsCompetition;
	}
	
	// Helper functions for initial setup
	private static Skill SetupSkillDuration(ArrayList<School> schools, LocalizedString displayName, LocalizedString displayNameUpperCompact, String name, Random random)
	{
		ArrayList<ISchoolScore> schoolsScore = new ArrayList<ISchoolScore>();

		for(School school : schools)
		{
			Duration duration = new Duration(59 * 60 * 1000);
			if(random != null)
			{
				duration = new Duration(random.nextInt(5*60*1000));
			}
			// Initialize to 59 minutes.
			schoolsScore.add(new SchoolDuration(school, duration));
		}
		
		return new Skill(schoolsScore, displayName, displayNameUpperCompact, name);
	}
	
	private static Skill SetupSkillInteger(ArrayList<School> schools, LocalizedString displayName, LocalizedString displayNameUpperCompact, String name, Random random)
	{
		ArrayList<ISchoolScore> schoolsScore = new ArrayList<ISchoolScore>();

		for(School school : schools)
		{
			int value = 0;
			
			if(random != null)
			{
				value = random.nextInt(100);
			}
			
			schoolsScore.add(new SchoolInteger(school, value));
		}
		
		return new Skill(schoolsScore, displayName, displayNameUpperCompact, name);
	}

	private static Skill SetupSkillFloatSmallest(ArrayList<School> schools, LocalizedString displayName, LocalizedString displayNameUpperCompact, String name, Random random)
	{
		ArrayList<ISchoolScore> schoolsScore = new ArrayList<ISchoolScore>();

		for(School school : schools)
		{
			float value = Float.MAX_VALUE;
			
			if(random != null)
			{
				value = random.nextInt(100);
			}
			
			schoolsScore.add(new SchoolFloatSmallest(school, value));
		}
		
		return new Skill(schoolsScore, displayName, displayNameUpperCompact, name);
	}
}
