package com.backend.controllers.yearly;

import java.util.ArrayList;

import com.backend.models.IPlayoff;
import com.backend.models.PlayoffGroup;
import com.backend.models.PlayoffRound;
import com.backend.models.School;
import com.backend.models.SchoolInteger;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;

public class Playoff27Teams implements IPlayoff
{	
	private void RemoveOtherThan3rd(ArrayList<SchoolInteger> bestThird, ArrayList<School> group)
	{
		for(int i = 0; i < group.size(); i++)
		{
			if( i != (3-1) )
			{
				bestThird.remove(group.get(i));
			}
		}
	}
	
	public ArrayList<PlayoffGroup> GenerateNextRound(Tournament tournament, PlayoffRound previousRound, int groupNo, GameTypeEnum gameType, ArrayList<School> preliminaryRanking, ArrayList<School> excludedSchools)
	{
		ArrayList<PlayoffGroup> playoffGroups = new ArrayList<PlayoffGroup>();
		
		switch (gameType)
		{
		// REPECHAGE
		// A 11 12 19 20 27
		// B 10 13 18 21 26
		// C 9  14 17 22 25
		// D 8  15 16 23 24
		case PLAYOFF_REPECHAGE:
		{
			ArrayList<School> schoolsPoolA = new ArrayList<School>();
			schoolsPoolA.add(preliminaryRanking.get(11 - 1));
			schoolsPoolA.add(preliminaryRanking.get(12 - 1));
			schoolsPoolA.add(preliminaryRanking.get(19 - 1));
			schoolsPoolA.add(preliminaryRanking.get(20 - 1));
			schoolsPoolA.add(preliminaryRanking.get(27 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolA, groupNo++));
			
			ArrayList<School> schoolsPoolB = new ArrayList<School>();
			schoolsPoolB.add(preliminaryRanking.get(10 - 1));
			schoolsPoolB.add(preliminaryRanking.get(13 - 1));
			schoolsPoolB.add(preliminaryRanking.get(18 - 1));
			schoolsPoolB.add(preliminaryRanking.get(21 - 1));
			schoolsPoolB.add(preliminaryRanking.get(26 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolB, groupNo++));
			
			ArrayList<School> schoolsPoolC = new ArrayList<School>();
			schoolsPoolC.add(preliminaryRanking.get(9 - 1));
			schoolsPoolC.add(preliminaryRanking.get(14 - 1));
			schoolsPoolC.add(preliminaryRanking.get(17 - 1));
			schoolsPoolC.add(preliminaryRanking.get(22 - 1));
			schoolsPoolC.add(preliminaryRanking.get(25 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolC, groupNo++));
			
			ArrayList<School> schoolsPoolD = new ArrayList<School>();
			schoolsPoolD.add(preliminaryRanking.get(8 - 1));
			schoolsPoolD.add(preliminaryRanking.get(15 - 1));
			schoolsPoolD.add(preliminaryRanking.get(16 - 1));
			schoolsPoolD.add(preliminaryRanking.get(23 - 1));
			schoolsPoolD.add(preliminaryRanking.get(24 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolD, groupNo++));
			break;
		}
		
		// QUART
		// E 4 A1 B2 C1 D2
		// F 5 6  B1 C2 MT1
		// G 3 7  A2 D1 MT2
		case PLAYOFF_QUARTER:
		{
			ArrayList<SchoolInteger> repechageResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_REPECHAGE);
			ArrayList<School> schoolsPoolA = previousRound.playoffGroups.get(0).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolB = previousRound.playoffGroups.get(1).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolC = previousRound.playoffGroups.get(2).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolD = previousRound.playoffGroups.get(3).getSchoolsRanked(repechageResults, excludedSchools);
			
			// Remove best 2 of each pool. The next schools are the best third.
			ArrayList<SchoolInteger> bestThird = new ArrayList<SchoolInteger>(repechageResults);
			RemoveOtherThan3rd(bestThird, schoolsPoolA);
			RemoveOtherThan3rd(bestThird, schoolsPoolB);
			RemoveOtherThan3rd(bestThird, schoolsPoolC);
			RemoveOtherThan3rd(bestThird, schoolsPoolD);
			
			ArrayList<School> schoolsPoolE = new ArrayList<School>();
			schoolsPoolE.add(preliminaryRanking.get(4 - 1));
			schoolsPoolE.add(schoolsPoolA.get(1 - 1));
			schoolsPoolE.add(schoolsPoolB.get(2 - 1));
			schoolsPoolE.add(schoolsPoolC.get(1 - 1));
			schoolsPoolE.add(schoolsPoolD.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolE, groupNo++));

			ArrayList<School> schoolsPoolF = new ArrayList<School>();
			schoolsPoolF.add(preliminaryRanking.get(5 - 1));
			schoolsPoolF.add(preliminaryRanking.get(6 - 1));
			schoolsPoolF.add(schoolsPoolB.get(1 - 1));
			schoolsPoolF.add(schoolsPoolC.get(2 - 1));
			schoolsPoolF.add(bestThird.get(1 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolF, groupNo++));

			// G 3 7  A2 D1 MT2
			ArrayList<School> schoolsPoolG = new ArrayList<School>();
			schoolsPoolG.add(preliminaryRanking.get(3 - 1));
			schoolsPoolG.add(preliminaryRanking.get(7 - 1));
			schoolsPoolG.add(schoolsPoolA.get(2 - 1));
			schoolsPoolG.add(schoolsPoolD.get(1 - 1));
			schoolsPoolG.add(bestThird.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolG, groupNo++));
			}
			break;

		// SEMI
		// H 2 E1 F2 G1
		// I 1 E2 F1 G2
		case PLAYOFF_DEMI:
		{
			ArrayList<SchoolInteger> quarterResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_QUARTER);
			ArrayList<School> schoolsPoolE = previousRound.playoffGroups.get(0).getSchoolsRanked(quarterResults, excludedSchools);
			ArrayList<School> schoolsPoolF = previousRound.playoffGroups.get(1).getSchoolsRanked(quarterResults, excludedSchools);
			ArrayList<School> schoolsPoolG = previousRound.playoffGroups.get(2).getSchoolsRanked(quarterResults, excludedSchools);

			ArrayList<School> schoolsPoolH = new ArrayList<School>();
			schoolsPoolH.add(preliminaryRanking.get(2 - 1));
			schoolsPoolH.add(schoolsPoolE.get(1 - 1));
			schoolsPoolH.add(schoolsPoolF.get(2 - 1));
			schoolsPoolH.add(schoolsPoolG.get(1 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolH, groupNo++));

			ArrayList<School> schoolsPoolI = new ArrayList<School>();
			schoolsPoolI.add(preliminaryRanking.get(1 - 1));
			schoolsPoolI.add(schoolsPoolE.get(2 - 1));
			schoolsPoolI.add(schoolsPoolF.get(1 - 1));
			schoolsPoolI.add(schoolsPoolG.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolI, groupNo++));

			break;
		}
		
		// FINAL
		// J H1 H2 I1 I2
		case PLAYOFF_FINAL:
		{
			ArrayList<SchoolInteger> demiResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_DEMI);
			ArrayList<School> schoolsPoolH = previousRound.playoffGroups.get(0).getSchoolsRanked(demiResults, excludedSchools);
			ArrayList<School> schoolsPoolI = previousRound.playoffGroups.get(1).getSchoolsRanked(demiResults, excludedSchools);
			
			ArrayList<School> schoolsPoolJ = new ArrayList<School>();
			schoolsPoolJ.add(schoolsPoolH.get(1 - 1));
			schoolsPoolJ.add(schoolsPoolH.get(2 - 1));
			schoolsPoolJ.add(schoolsPoolI.get(1 - 1));
			schoolsPoolJ.add(schoolsPoolI.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolJ, groupNo++));
		}
		break;
		
		default:
			break;
		}
		
		return playoffGroups;
	}
}
