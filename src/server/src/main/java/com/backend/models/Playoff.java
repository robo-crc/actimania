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
	
	// I've hard coded this function ... it could be made more generic, but for Actimania it will be good enough.
	public PlayoffRound generatePlayoffRound(Database database, Tournament tournament, PlayoffRound previousRound, GameTypeEnum gameType)
	{
		ArrayList<PlayoffGroup> playoffGroups = new ArrayList<PlayoffGroup>();
		
		ArrayList<School> schoolsInGroup1 = new ArrayList<School>();
		ArrayList<School> schoolsInGroup2 = new ArrayList<School>();
		ArrayList<School> schoolsInGroup3 = new ArrayList<School>();
		ArrayList<School> schoolsInGroup4 = new ArrayList<School>();
		
		int groupNo = 0;
		if(previousRound != null)
		{
			groupNo = previousRound.playoffGroups.get(previousRound.playoffGroups.size() - 1).groupNo + 1;
		}
		
		SkillsCompetition skillsCompetition = SkillsCompetition.get(database);
		ArrayList<School> preliminaryRanking = getRemainingSchools(tournament.getPreliminaryRanking(skillsCompetition));
		
		switch (gameType)
		{
		case PLAYOFF_REPECHAGE:
			return generateRepechageRound(preliminaryRanking);
		case PLAYOFF_QUARTER:
		{
			ArrayList<SchoolInteger> repechageResults = tournament.getHeatRanking(GameTypeEnum.PLAYOFF_REPECHAGE);
			ArrayList<School> schoolsPoolA = previousRound.playoffGroups.get(0).getSchoolsForNextRound(repechageResults);
			ArrayList<School> schoolsPoolB = previousRound.playoffGroups.get(1).getSchoolsForNextRound(repechageResults);
			ArrayList<School> schoolsPoolC = previousRound.playoffGroups.get(2).getSchoolsForNextRound(repechageResults);
			ArrayList<School> schoolsPoolD = previousRound.playoffGroups.get(3).getSchoolsForNextRound(repechageResults);
			ArrayList<School> schoolsPoolE = previousRound.playoffGroups.get(4).getSchoolsForNextRound(repechageResults);
			ArrayList<School> schoolsPoolF = previousRound.playoffGroups.get(5).getSchoolsForNextRound(repechageResults);
			
			schoolsInGroup1.add(preliminaryRanking.get(2));
			schoolsInGroup1.add(schoolsPoolD.get(0));
			schoolsInGroup1.add(schoolsPoolE.get(0));
			schoolsInGroup1.add(schoolsPoolA.get(1));
			
			schoolsInGroup2.add(preliminaryRanking.get(3));
			schoolsInGroup2.add(schoolsPoolC.get(0));
			schoolsInGroup2.add(schoolsPoolF.get(0));
			schoolsInGroup2.add(schoolsPoolB.get(1));
			
			schoolsInGroup3.add(preliminaryRanking.get(4));
			schoolsInGroup3.add(schoolsPoolB.get(0));
			schoolsInGroup3.add(schoolsPoolF.get(1));
			schoolsInGroup3.add(schoolsPoolC.get(1));
			
			schoolsInGroup4.add(preliminaryRanking.get(5));
			schoolsInGroup4.add(schoolsPoolA.get(0));
			schoolsInGroup4.add(schoolsPoolE.get(1));
			schoolsInGroup4.add(schoolsPoolD.get(1));
			
			playoffGroups.add(new PlayoffGroup(schoolsInGroup1, groupNo++));
			playoffGroups.add(new PlayoffGroup(schoolsInGroup2, groupNo++));
			playoffGroups.add(new PlayoffGroup(schoolsInGroup3, groupNo++));
			playoffGroups.add(new PlayoffGroup(schoolsInGroup4, groupNo++));
		}
			break;
		case PLAYOFF_DEMI:
		{
			ArrayList<SchoolInteger> quarterResults = tournament.getHeatRanking(GameTypeEnum.PLAYOFF_QUARTER);
			ArrayList<School> schoolsPoolG = previousRound.playoffGroups.get(0).getSchoolsForNextRound(quarterResults);
			ArrayList<School> schoolsPoolH = previousRound.playoffGroups.get(1).getSchoolsForNextRound(quarterResults);
			ArrayList<School> schoolsPoolI = previousRound.playoffGroups.get(2).getSchoolsForNextRound(quarterResults);
			ArrayList<School> schoolsPoolJ = previousRound.playoffGroups.get(3).getSchoolsForNextRound(quarterResults);
			
			schoolsInGroup1.add(preliminaryRanking.get(0));
			schoolsInGroup1.add(schoolsPoolG.get(0));
			schoolsInGroup1.add(schoolsPoolH.get(1));
			schoolsInGroup1.add(schoolsPoolI.get(1));
			schoolsInGroup1.add(schoolsPoolJ.get(0));
			
			schoolsInGroup2.add(preliminaryRanking.get(1));
			schoolsInGroup2.add(schoolsPoolG.get(1));
			schoolsInGroup2.add(schoolsPoolH.get(0));
			schoolsInGroup2.add(schoolsPoolI.get(0));
			schoolsInGroup2.add(schoolsPoolJ.get(1));
			
			playoffGroups.add(new PlayoffGroup(schoolsInGroup1, groupNo++));
			playoffGroups.add(new PlayoffGroup(schoolsInGroup2, groupNo++));
			break;
		}
		case PLAYOFF_FINAL:
		{
			ArrayList<SchoolInteger> demiResults = tournament.getHeatRanking(GameTypeEnum.PLAYOFF_DEMI);
			ArrayList<School> schoolsPoolK = previousRound.playoffGroups.get(0).getSchoolsForNextRound(demiResults);
			ArrayList<School> schoolsPoolL = previousRound.playoffGroups.get(1).getSchoolsForNextRound(demiResults);
			
			schoolsInGroup1.add(schoolsPoolK.get(0));
			schoolsInGroup1.add(schoolsPoolK.get(1));
			schoolsInGroup1.add(schoolsPoolL.get(0));
			schoolsInGroup1.add(schoolsPoolL.get(1));
			
			playoffGroups.add(new PlayoffGroup(schoolsInGroup1, groupNo++));
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
	
	public ArrayList<School> getRemainingSchools(ArrayList<School> allSchools)
	{
		ArrayList<School> ret = new ArrayList<School>(allSchools);
		ret.removeAll(excludedSchools);		
		return ret;
	}
}
