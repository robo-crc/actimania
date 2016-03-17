package com.backend.controllers.yearly;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;

import com.backend.models.IPlayoff;
import com.backend.models.PlayoffGroup;
import com.backend.models.PlayoffRound;
import com.backend.models.School;
import com.backend.models.SchoolInteger;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;

public class Playoff30Teams implements IPlayoff
{
	public static final int SCHOOLS_PER_TEAM = 2;
	
	public static final int POSITIONS_SKIP_ROUND_ONE = 6;
	public static final int POSITIONS_SKIP_ROUND_TWO = 2;

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
	
	public static ArrayList<PlayoffGroup> generateRepechageRound(ArrayList<School> preliminaryRanking)
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
		
		return playoffGroups;
	}
			
	public ArrayList<PlayoffGroup> GenerateNextRound(Tournament tournament, PlayoffRound previousRound, int groupNo, GameTypeEnum gameType, ArrayList<School> preliminaryRanking)
	{
		ArrayList<PlayoffGroup> playoffGroups = new ArrayList<PlayoffGroup>();
		
		switch (gameType)
		{
		case PLAYOFF_REPECHAGE:
			return generateRepechageRound(preliminaryRanking);
		case PLAYOFF_QUARTER:
		{
			ArrayList<SchoolInteger> repechageResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_REPECHAGE);
			ArrayList<School> schoolsPoolA = previousRound.playoffGroups.get(0).getSchoolsRanked(repechageResults);
			ArrayList<School> schoolsPoolB = previousRound.playoffGroups.get(1).getSchoolsRanked(repechageResults);
			ArrayList<School> schoolsPoolC = previousRound.playoffGroups.get(2).getSchoolsRanked(repechageResults);
			ArrayList<School> schoolsPoolD = previousRound.playoffGroups.get(3).getSchoolsRanked(repechageResults);
			ArrayList<School> schoolsPoolE = previousRound.playoffGroups.get(4).getSchoolsRanked(repechageResults);
			ArrayList<School> schoolsPoolF = previousRound.playoffGroups.get(5).getSchoolsRanked(repechageResults);
			
			ArrayList<School> schoolsPoolG = new ArrayList<School>();
			schoolsPoolG.add(preliminaryRanking.get(3 - 1));
			schoolsPoolG.add(schoolsPoolD.get(1 - 1));
			schoolsPoolG.add(schoolsPoolE.get(1 - 1));
			schoolsPoolG.add(schoolsPoolA.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolG, groupNo++));
			
			ArrayList<School> schoolsPoolH = new ArrayList<School>();
			schoolsPoolH.add(preliminaryRanking.get(4 - 1));
			schoolsPoolH.add(schoolsPoolC.get(1 - 1));
			schoolsPoolH.add(schoolsPoolF.get(1 - 1));
			schoolsPoolH.add(schoolsPoolB.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolH, groupNo++));
			
			ArrayList<School> schoolsPoolI = new ArrayList<School>();
			schoolsPoolI.add(preliminaryRanking.get(5 - 1));
			schoolsPoolI.add(schoolsPoolB.get(1 - 1));
			schoolsPoolI.add(schoolsPoolF.get(2 - 1));
			schoolsPoolI.add(schoolsPoolC.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolI, groupNo++));
			
			ArrayList<School> schoolsPoolJ = new ArrayList<School>();
			schoolsPoolJ.add(preliminaryRanking.get(6 - 1));
			schoolsPoolJ.add(schoolsPoolA.get(1 - 1));
			schoolsPoolJ.add(schoolsPoolE.get(2 - 1));
			schoolsPoolJ.add(schoolsPoolD.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolJ, groupNo++));
		}
			break;
		case PLAYOFF_DEMI:
		{
			ArrayList<SchoolInteger> quarterResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_QUARTER);
			ArrayList<School> schoolsPoolG = previousRound.playoffGroups.get(0).getSchoolsRanked(quarterResults);
			ArrayList<School> schoolsPoolH = previousRound.playoffGroups.get(1).getSchoolsRanked(quarterResults);
			ArrayList<School> schoolsPoolI = previousRound.playoffGroups.get(2).getSchoolsRanked(quarterResults);
			ArrayList<School> schoolsPoolJ = previousRound.playoffGroups.get(3).getSchoolsRanked(quarterResults);
			

			ArrayList<School> schoolsPoolK = new ArrayList<School>();
			schoolsPoolK.add(preliminaryRanking.get(1 - 1));
			schoolsPoolK.add(schoolsPoolG.get(1 - 1));
			schoolsPoolK.add(schoolsPoolH.get(2 - 1));
			schoolsPoolK.add(schoolsPoolI.get(2 - 1));
			schoolsPoolK.add(schoolsPoolJ.get(1 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolK, groupNo++));
			
			ArrayList<School> schoolsPoolL = new ArrayList<School>();
			schoolsPoolL.add(preliminaryRanking.get(2 - 1));
			schoolsPoolL.add(schoolsPoolG.get(2 - 1));
			schoolsPoolL.add(schoolsPoolH.get(1 - 1));
			schoolsPoolL.add(schoolsPoolI.get(1 - 1));
			schoolsPoolL.add(schoolsPoolJ.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolL, groupNo++));
			
			break;
		}
		case PLAYOFF_FINAL:
		{
			ArrayList<SchoolInteger> demiResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_DEMI);
			ArrayList<School> schoolsPoolK = previousRound.playoffGroups.get(0).getSchoolsRanked(demiResults);
			ArrayList<School> schoolsPoolL = previousRound.playoffGroups.get(1).getSchoolsRanked(demiResults);

			ArrayList<School> schoolsPoolM = new ArrayList<School>();
			schoolsPoolM.add(schoolsPoolK.get(0));
			schoolsPoolM.add(schoolsPoolK.get(1));
			schoolsPoolM.add(schoolsPoolL.get(0));
			schoolsPoolM.add(schoolsPoolL.get(1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolM, groupNo++));
		}
		break;
		
		default:
			break;
		}
		
		return playoffGroups;
	}
}
