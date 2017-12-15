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
		roundStartHour[0] = new DateTime(2018, 2, 1, 18, 30);
		roundStartHour[1] = new DateTime(2018, 2, 2, 9, 0);
		roundStartHour[2] = new DateTime(2018, 2, 2, 13, 00);
		roundStartHour[3] = new DateTime(2018, 2, 2, 18, 00);
		
		return roundStartHour;
	}
	
	private static final LocalizedString strDropPieces = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "Drop 3 game pieces",
			Locale.FRENCH, 	"Déposer 3 pièces de jeux"
			), Locale.ENGLISH);

	private static final LocalizedString strDropPiecesCompact = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "DROP<BR/>3 PIECES", 
			Locale.FRENCH, 	"DÉPOSER<BR/>3 PIÈCES"
			), Locale.ENGLISH);

	private static final LocalizedString strPickupPieces = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "Pick up 3 game pieces", 
			Locale.FRENCH, 	"Ramasser 3 pièces de jeux"
			), Locale.ENGLISH);

	private static final LocalizedString strPickupPiecesCompact = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "PICK UP<BR/>3 PIECES", 
			Locale.FRENCH, 	"RAMASSER<BR/>3 PIÈCES"
			), Locale.ENGLISH);
	
	private static final LocalizedString strQuickest = new LocalizedString(ImmutableMap.of( 	
			Locale.ENGLISH, "Quickest", 
			Locale.FRENCH, 	"Le plus rapide"
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
		
		skills.add(SetupSkillDuration(schools, strPickupPieces, strPickupPiecesCompact, "pickupPieces", random));
		skills.add(SetupSkillDuration(schools, strDropPieces, 	strDropPiecesCompact,	"dropPieces", random));
		skills.add(SetupSkillDuration(schools, strQuickest, 	strQuickestCompact, 	"quickest", random));
		
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
