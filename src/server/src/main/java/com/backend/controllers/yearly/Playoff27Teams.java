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
	public ArrayList<PlayoffGroup> GenerateNextRound(Tournament tournament, PlayoffRound previousRound, int groupNo, GameTypeEnum gameType, ArrayList<School> preliminaryRanking, ArrayList<School> excludedSchools)
	{
		ArrayList<PlayoffGroup> playoffGroups = new ArrayList<PlayoffGroup>();
		
		switch (gameType)
		{
		// REPECHAGE
		// A 15 19 23 27
		// B 14 18 22 26
		// C 13 17 21 25
		// D 12 16 20 24
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
			ArrayList<School> schoolsPoolA = previousRound.playoffGroups.get(0).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolB = previousRound.playoffGroups.get(1).getSchoolsRanked(repechageResults, excludedSchools);
			ArrayList<School> schoolsPoolC = previousRound.playoffGroups.get(2).getSchoolsRanked(repechageResults, excludedSchools);
			
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
			ArrayList<School> schoolsPoolD = previousRound.playoffGroups.get(0).getSchoolsRanked(quarterResults, excludedSchools);
			ArrayList<School> schoolsPoolE = previousRound.playoffGroups.get(1).getSchoolsRanked(quarterResults, excludedSchools);
			ArrayList<School> schoolsPoolF = previousRound.playoffGroups.get(2).getSchoolsRanked(quarterResults, excludedSchools);

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
			ArrayList<School> schoolsPoolG = previousRound.playoffGroups.get(0).getSchoolsRanked(demiResults, excludedSchools);
			ArrayList<School> schoolsPoolH = previousRound.playoffGroups.get(1).getSchoolsRanked(demiResults, excludedSchools);
			
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
		
		return playoffGroups;
	}
}
