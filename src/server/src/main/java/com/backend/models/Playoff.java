package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bson.types.ObjectId;

import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.helpers.Database;

public class Playoff 
{
	@JsonIgnore
	public static final int SCHOOLS_PER_TEAM = 2;
	
	public static final int POSITIONS_SKIP_ROUND_ONE = 6;
	public static final int POSITIONS_SKIP_ROUND_TWO = 2;
	
	public final ObjectId _id;
	public final ArrayList<School> excludedSchools;
	
	public Playoff(
			@JsonProperty("_id") 				ObjectId playoffId,
			@JsonProperty("excludedSchools") 	ArrayList<School> _excludedSchools)
	{
		_id = playoffId;
		excludedSchools = _excludedSchools;
	}
	
	/*
	public static int getRoundsCount(int nbOfTeams)
	{
		int roundsCount = 1;
		int teamCountInRound = SCHOOLS_PER_TEAM * 2; // Always start with the final
		while(teamCountInRound < nbOfTeams)
		{
			teamCountInRound *= 2;
			roundsCount++;
		}
		return roundsCount;
	}
	
	public static PlayoffRound generateRepechageRound(ArrayList<School> preliminaryRanking)
	{
		ArrayList<School> schoolsInRound = new ArrayList<School>();
		int roundCount = getRoundsCount(preliminaryRanking.size());
		int nbSchoolsPassToNextRound = (int) (Math.pow(2,roundCount) - POSITIONS_SKIP_ROUND_ONE + POSITIONS_SKIP_ROUND_TWO);

		for(int index = POSITIONS_SKIP_ROUND_ONE; index < preliminaryRanking.size(); index++)
		{
			schoolsInRound.add(preliminaryRanking.get(index));
		}

		int schoolsCount = schoolsInRound.size();
		
		int minGroupsCount = schoolsCount / (SCHOOLS_PER_TEAM * 4 - 1);
		int maxGroupsCount = schoolsCount / (SCHOOLS_PER_TEAM * 2);
		
		boolean foundGroupCount = false;
		int groupsCount = maxGroupsCount;
		while( groupsCount >= minGroupsCount && !foundGroupCount)
		{
			if(nbSchoolsPassToNextRound % groupsCount == 0)
			{
				foundGroupCount = true;
			}
			else
			{
				 groupsCount--;
			}
		}
		
		// If we did not find the group count, there's no point in continuing.
		Validate.isTrue(foundGroupCount);
		
		int nbOfGroupsWithExtraSchool = schoolsCount % groupsCount;
		int schoolsPerGroup = schoolsCount / groupsCount;
		
		ArrayList<PlayoffGroup> playoffGroups = new ArrayList<PlayoffGroup>();
		for(int groupNb = 0; groupNb < groupsCount; groupNb++)
		{
			ArrayList<School> schoolsInGroup = new ArrayList<School>();
			
			for(int schoolNb = 0; schoolNb < schoolsPerGroup; schoolNb++)
			{
				if(schoolNb < schoolsPerGroup / 2)
				{
					// Lowest first
					schoolsInGroup.add(schoolsInRound.get(schoolNb * groupsCount + groupNb));
				}
				else
				{
					// Highest first
					int position = schoolsCount  - ((schoolsPerGroup - schoolNb - 1) * groupsCount + groupNb) - 1;
					schoolsInGroup.add(schoolsInRound.get(position));
				}
			}
			
			// The last groups will get the extra school. Those groups contains the most average groups.
			int invertGroupNb = groupsCount - groupNb -1;
			if(nbOfGroupsWithExtraSchool > invertGroupNb)
			{
				schoolsInGroup.add(schoolsInRound.get((schoolsCount / 2) + invertGroupNb));
			}
			
			playoffGroups.add(new PlayoffGroup(schoolsInGroup,groupNb));
		}
		
		return new PlayoffRound(null, playoffGroups, GameTypeEnum.PLAYOFF_REPECHAGE);
	}
	*/

	// I've tried to make this function generic, but finally those are kind of decisions my tournament per tournament.
	public PlayoffRound generatePlayoffRound(Database database, Tournament tournament, PlayoffRound previousRound, GameTypeEnum gameType)
	{
		SkillsCompetition skillsCompetition = SkillsCompetition.get(database);
		ArrayList<School> preliminaryRanking = getRemainingSchools(tournament.getPreliminaryRanking(skillsCompetition), excludedSchools);
		
		int groupNo = 0;
		if(previousRound != null)
		{
			groupNo = previousRound.playoffGroups.get(previousRound.playoffGroups.size() - 1).groupNo + 1;
		}
		
		ArrayList<PlayoffGroup> playoffGroups = new ArrayList<PlayoffGroup>();
		
		switch (gameType)
		{
		// REPECHAGE
		// A 9  12 17 20 23
		// B 10 13 15 19 22
		// C 11 14 16 18 21
		case PLAYOFF_REPECHAGE:
		{
			ArrayList<School> schoolsPoolA = new ArrayList<School>();
			schoolsPoolA.add(preliminaryRanking.get(9 - 1));
			schoolsPoolA.add(preliminaryRanking.get(12 - 1));
			schoolsPoolA.add(preliminaryRanking.get(17 - 1));
			schoolsPoolA.add(preliminaryRanking.get(20 - 1));
			schoolsPoolA.add(preliminaryRanking.get(23 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolA, groupNo++));
			
			ArrayList<School> schoolsPoolB = new ArrayList<School>();
			schoolsPoolB.add(preliminaryRanking.get(10 - 1));
			schoolsPoolB.add(preliminaryRanking.get(13 - 1));
			schoolsPoolB.add(preliminaryRanking.get(15 - 1));
			schoolsPoolB.add(preliminaryRanking.get(19 - 1));
			schoolsPoolB.add(preliminaryRanking.get(22 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolB, groupNo++));
			
			ArrayList<School> schoolsPoolC = new ArrayList<School>();
			schoolsPoolC.add(preliminaryRanking.get(11 - 1));
			schoolsPoolC.add(preliminaryRanking.get(14 - 1));
			schoolsPoolC.add(preliminaryRanking.get(16 - 1));
			schoolsPoolC.add(preliminaryRanking.get(18 - 1));
			schoolsPoolC.add(preliminaryRanking.get(21 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolC, groupNo++));
			
			break;
		}
		
		// QUART
		// D 3  6  C1  A2  B3
		// E 4  7  A1  B2  C3
		// F 5  8  B1  C2  A3
		case PLAYOFF_QUARTER:
		{
			ArrayList<SchoolInteger> repechageResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_REPECHAGE);
			ArrayList<School> schoolsPoolA = previousRound.playoffGroups.get(0).getSchoolsForNextRound(repechageResults,3);
			ArrayList<School> schoolsPoolB = previousRound.playoffGroups.get(1).getSchoolsForNextRound(repechageResults,3);
			ArrayList<School> schoolsPoolC = previousRound.playoffGroups.get(2).getSchoolsForNextRound(repechageResults,3);
			
			ArrayList<School> schoolsPoolD = new ArrayList<School>();
			schoolsPoolD.add(preliminaryRanking.get(3 - 1));
			schoolsPoolD.add(preliminaryRanking.get(6 - 1));
			schoolsPoolD.add(schoolsPoolC.get(1 - 1));
			schoolsPoolD.add(schoolsPoolA.get(2 - 1));
			schoolsPoolD.add(schoolsPoolB.get(3 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolD, groupNo++));

			ArrayList<School> schoolsPoolE = new ArrayList<School>();
			schoolsPoolE.add(preliminaryRanking.get(4 - 1));
			schoolsPoolE.add(preliminaryRanking.get(7 - 1));
			schoolsPoolE.add(schoolsPoolA.get(1 - 1));
			schoolsPoolE.add(schoolsPoolB.get(2 - 1));
			schoolsPoolE.add(schoolsPoolC.get(3 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolE, groupNo++));

			ArrayList<School> schoolsPoolF = new ArrayList<School>();
			schoolsPoolF.add(preliminaryRanking.get(5 - 1));
			schoolsPoolF.add(preliminaryRanking.get(8 - 1));
			schoolsPoolF.add(schoolsPoolB.get(1 - 1));
			schoolsPoolF.add(schoolsPoolC.get(2 - 1));
			schoolsPoolF.add(schoolsPoolA.get(3 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolF, groupNo++));

			}
			break;

		// SEMI
		// G 1 D2 E1 F2
		// H 2 D1 E2 F1
		case PLAYOFF_DEMI:
		{
			ArrayList<SchoolInteger> quarterResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_QUARTER);
			ArrayList<School> schoolsPoolD = previousRound.playoffGroups.get(0).getSchoolsForNextRound(quarterResults, 2);
			ArrayList<School> schoolsPoolE = previousRound.playoffGroups.get(1).getSchoolsForNextRound(quarterResults, 2);
			ArrayList<School> schoolsPoolF = previousRound.playoffGroups.get(2).getSchoolsForNextRound(quarterResults, 2);

			ArrayList<School> schoolsPoolG = new ArrayList<School>();
			schoolsPoolG.add(preliminaryRanking.get(1 - 1));
			schoolsPoolG.add(schoolsPoolD.get(2 - 1));
			schoolsPoolG.add(schoolsPoolE.get(1 - 1));
			schoolsPoolG.add(schoolsPoolF.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolG, groupNo++));
			
			ArrayList<School> schoolsPoolH = new ArrayList<School>();
			schoolsPoolH.add(preliminaryRanking.get(2 - 1));
			schoolsPoolH.add(schoolsPoolD.get(1 - 1));
			schoolsPoolH.add(schoolsPoolE.get(2 - 1));
			schoolsPoolH.add(schoolsPoolF.get(1 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolH, groupNo++));
			break;
		}
		
		// FINAL
		// I G1 G2 H1 H2
		case PLAYOFF_FINAL:
		{
			ArrayList<SchoolInteger> demiResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_DEMI);
			ArrayList<School> schoolsPoolG = previousRound.playoffGroups.get(0).getSchoolsForNextRound(demiResults, 2);
			ArrayList<School> schoolsPoolH = previousRound.playoffGroups.get(1).getSchoolsForNextRound(demiResults, 2);
			
			ArrayList<School> schoolsPoolI = new ArrayList<School>();
			schoolsPoolI.add(schoolsPoolG.get(1 - 1));
			schoolsPoolI.add(schoolsPoolG.get(2 - 1));
			schoolsPoolI.add(schoolsPoolH.get(1 - 1));
			schoolsPoolI.add(schoolsPoolH.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolI, groupNo++));
		}
		break;
		
		default:
			break;
		}

		return new PlayoffRound(null, playoffGroups, gameType);
	}
	
	public static Playoff get(Database database)
	{
		Playoff playoff = database.findOne(Playoff.class, "{ }");
		if(playoff == null)
		{
			playoff = new Playoff(null, new ArrayList<School>());
			database.save(playoff);
		}
		return playoff;
	}
	
	public static ArrayList<School> getRemainingSchools(ArrayList<School> allSchools, ArrayList<School> excludedSchools)
	{
		ArrayList<School> ret = new ArrayList<School>(allSchools);
		ret.removeAll(excludedSchools);
		return ret;
	}
}
