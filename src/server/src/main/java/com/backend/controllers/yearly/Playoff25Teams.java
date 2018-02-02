package com.backend.controllers.yearly;

import java.util.ArrayList;

import com.backend.models.IPlayoff;
import com.backend.models.PlayoffGroup;
import com.backend.models.PlayoffRound;
import com.backend.models.School;
import com.backend.models.SchoolInteger;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;

public class Playoff25Teams implements IPlayoff
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
		//A	6	11	20	25
		//B	7	12	19	24
		//C	8	13	18	23
		//D	9	14	17	22
		//E	10	15	16	21
		case PLAYOFF_REPECHAGE:
		{
			ArrayList<School> schoolsPoolA = new ArrayList<School>();
			schoolsPoolA.add(preliminaryRanking.get(6 - 1));
			schoolsPoolA.add(preliminaryRanking.get(11 - 1));
			schoolsPoolA.add(preliminaryRanking.get(20 - 1));
			schoolsPoolA.add(preliminaryRanking.get(25 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolA, groupNo++));
			
			ArrayList<School> schoolsPoolB = new ArrayList<School>();
			schoolsPoolB.add(preliminaryRanking.get(7 - 1));
			schoolsPoolB.add(preliminaryRanking.get(12 - 1));
			schoolsPoolB.add(preliminaryRanking.get(19 - 1));
			schoolsPoolB.add(preliminaryRanking.get(24 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolB, groupNo++));
			
			ArrayList<School> schoolsPoolC = new ArrayList<School>();
			schoolsPoolC.add(preliminaryRanking.get(8 - 1));
			schoolsPoolC.add(preliminaryRanking.get(13 - 1));
			schoolsPoolC.add(preliminaryRanking.get(18 - 1));
			schoolsPoolC.add(preliminaryRanking.get(23 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolC, groupNo++));
			
			ArrayList<School> schoolsPoolD = new ArrayList<School>();
			schoolsPoolD.add(preliminaryRanking.get(9 - 1));
			schoolsPoolD.add(preliminaryRanking.get(14 - 1));
			schoolsPoolD.add(preliminaryRanking.get(17 - 1));
			schoolsPoolD.add(preliminaryRanking.get(22 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolD, groupNo++));

			ArrayList<School> schoolsPoolE = new ArrayList<School>();
			schoolsPoolE.add(preliminaryRanking.get(10 - 1));
			schoolsPoolE.add(preliminaryRanking.get(15 - 1));
			schoolsPoolE.add(preliminaryRanking.get(16 - 1));
			schoolsPoolE.add(preliminaryRanking.get(21 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolE, groupNo++));

			break;
		}
		
		// QUART (No F in excel)
		//G	3	A1	E2	D2	MT1
		//H	4	B1	E1	C2	MT2
		//I	5	C1	D1	B2	A2
		case PLAYOFF_QUARTER:
		{
			ArrayList<SchoolInteger> repechageResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_REPECHAGE);
			ArrayList<School> schoolsPoolA = previousRound.playoffGroups.get(0).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolB = previousRound.playoffGroups.get(1).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolC = previousRound.playoffGroups.get(2).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolD = previousRound.playoffGroups.get(3).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolE = previousRound.playoffGroups.get(4).getSchoolsRanked(repechageResults, excludedSchools);
			
			// Remove best 2 of each pool. The next schools are the best third.
			ArrayList<SchoolInteger> bestThird = new ArrayList<SchoolInteger>(repechageResults);
			RemoveOtherThan3rd(bestThird, schoolsPoolA);
			RemoveOtherThan3rd(bestThird, schoolsPoolB);
			RemoveOtherThan3rd(bestThird, schoolsPoolC);
			RemoveOtherThan3rd(bestThird, schoolsPoolD);
			RemoveOtherThan3rd(bestThird, schoolsPoolE);
			
			//G	3	A1	E2	D2	MT1
			ArrayList<School> schoolsPoolG = new ArrayList<School>();
			schoolsPoolG.add(preliminaryRanking.get(3 - 1));
			schoolsPoolG.add(schoolsPoolA.get(1 - 1));
			schoolsPoolG.add(schoolsPoolE.get(2 - 1));
			schoolsPoolG.add(schoolsPoolD.get(2 - 1));
			schoolsPoolG.add(bestThird.get(1 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolG, groupNo++));

			//H	4	B1	E1	C2	MT2
			ArrayList<School> schoolsPoolH = new ArrayList<School>();
			schoolsPoolH.add(preliminaryRanking.get(4 - 1));
			schoolsPoolH.add(schoolsPoolB.get(1 - 1));
			schoolsPoolH.add(schoolsPoolE.get(1 - 1));
			schoolsPoolH.add(schoolsPoolC.get(2 - 1));
			schoolsPoolH.add(bestThird.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolH, groupNo++));

			//I	5	C1	D1	B2	A2
			ArrayList<School> schoolsPoolI = new ArrayList<School>();
			schoolsPoolI.add(preliminaryRanking.get(5 - 1));
			schoolsPoolI.add(schoolsPoolC.get(1 - 1));
			schoolsPoolI.add(schoolsPoolD.get(1 - 1));
			schoolsPoolI.add(schoolsPoolB.get(2 - 1));
			schoolsPoolI.add(schoolsPoolA.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolI, groupNo++));
		}
			break;

		// SEMI
		//J	1	G2	H1	I2
		//K	2	G1	H2	I1
		case PLAYOFF_DEMI:
		{
			ArrayList<SchoolInteger> quarterResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_QUARTER);
			ArrayList<School> schoolsPoolG = previousRound.playoffGroups.get(0).getSchoolsRanked(quarterResults, excludedSchools);
			ArrayList<School> schoolsPoolH = previousRound.playoffGroups.get(1).getSchoolsRanked(quarterResults, excludedSchools);
			ArrayList<School> schoolsPoolI = previousRound.playoffGroups.get(2).getSchoolsRanked(quarterResults, excludedSchools);

			ArrayList<School> schoolsPoolJ = new ArrayList<School>();
			schoolsPoolJ.add(preliminaryRanking.get(1 - 1));
			schoolsPoolJ.add(schoolsPoolG.get(2 - 1));
			schoolsPoolJ.add(schoolsPoolH.get(1 - 1));
			schoolsPoolJ.add(schoolsPoolI.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolJ, groupNo++));

			ArrayList<School> schoolsPoolK = new ArrayList<School>();
			schoolsPoolK.add(preliminaryRanking.get(2 - 1));
			schoolsPoolK.add(schoolsPoolG.get(1 - 1));
			schoolsPoolK.add(schoolsPoolH.get(2 - 1));
			schoolsPoolK.add(schoolsPoolI.get(1 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolK, groupNo++));

			break;
		}
		
		// FINAL
		// GG K1 K2 L1 L2
		case PLAYOFF_FINAL:
		{
			ArrayList<SchoolInteger> demiResults = tournament.getRoundRanking(GameTypeEnum.PLAYOFF_DEMI);
			ArrayList<School> schoolsPoolJ = previousRound.playoffGroups.get(0).getSchoolsRanked(demiResults, excludedSchools);
			ArrayList<School> schoolsPoolK = previousRound.playoffGroups.get(1).getSchoolsRanked(demiResults, excludedSchools);
			
			ArrayList<School> schoolsPoolGG = new ArrayList<School>();
			schoolsPoolGG.add(schoolsPoolJ.get(1 - 1));
			schoolsPoolGG.add(schoolsPoolJ.get(2 - 1));
			schoolsPoolGG.add(schoolsPoolK.get(1 - 1));
			schoolsPoolGG.add(schoolsPoolK.get(2 - 1));
			playoffGroups.add(new PlayoffGroup(schoolsPoolGG, groupNo++));
		}
		break;
		
		default:
			break;
		}
		
		return playoffGroups;
	}
}
