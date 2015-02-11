package com.backend.models.optaplanner;

import java.util.ArrayList;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import com.backend.models.School;
import com.backend.models.Tournament;

public class JudgeScoreCalculator implements EasyScoreCalculator<TournamentSolution> 
{
	@Override
	public HardMediumSoftScore calculateScore(TournamentSolution tournament) 
	{
		int hardScore 	= 0;
		int mediumScore = 0;
		int softScore 	= 0;
		
		// 31 judges
		// 31 schools 
		
		ArrayList<ArrayList<School>> judgeSchools = new ArrayList<ArrayList<School>>();
		
		ArrayList<Integer> schoolCountPerJudges = getSchoolCountPerJudges(tournament.getSchools().size());
		
		// Each school needs to be judge the same amount of time
		// Each judge should have a similar amount of school to judge
		// The judging should be spread as much as possible ( Ideally 2 judge should not judge 2 same schools)
		int teamAssignmentStart = 0;
		
		boolean fastExit = false;
		
		for(int currentJudge = 0; currentJudge < schoolCountPerJudges.size() && !fastExit; currentJudge++)
		{
			int teamAssignmentEnd = teamAssignmentStart + schoolCountPerJudges.get(currentJudge);
			judgeSchools.add(new ArrayList<School>(teamAssignmentEnd - teamAssignmentStart));
			for(int i = teamAssignmentStart; i < teamAssignmentEnd; i++)
			{
				School schoolToAdd = tournament.getTeamAssignment().get(i).getSchool();
				if(schoolToAdd == null || judgeSchools.get(currentJudge).contains(schoolToAdd))
				{
					hardScore -= 10000;
					fastExit = true;
					break;
				}
				judgeSchools.get(currentJudge).add(tournament.getTeamAssignment().get(i).getSchool());
			}
			teamAssignmentStart = teamAssignmentEnd;
		}
		
		if(!fastExit)
		{
			tournament.setJudgements(judgeSchools);
			
			// N^4 ... could use hashmap ... whatever for now.
			for(int judge1 = 0; judge1 < judgeSchools.size(); judge1++)
			{
				for(int judge2 = judge1 + 1; judge2 < judgeSchools.size(); judge2++)
				{
					int sameSchools = 0;
					for(School schoolJudge1 : judgeSchools.get(judge1))
					{
						for(School schoolJudge2 : judgeSchools.get(judge2))
						{
							if(schoolJudge1.equals(schoolJudge2))
							{
								sameSchools++;
							}
						}
					}
					int schoolOverLimit = Math.max(0, sameSchools - 1);
					// 1 school overlap each is good.
					softScore -= schoolOverLimit * schoolOverLimit;
				}
			}
		
			for(School school : tournament.getSchools())
			{
				int judgedTime = 0;
				for(ArrayList<School> currentJudgeSchools : judgeSchools)
				{
					for(School schoolJudge : currentJudgeSchools)
					{
						if(school.equals(schoolJudge))
						{
							judgedTime++;
						}
					}
				}
				
				if(judgedTime != Tournament.EACH_SCHOOL_JUDGED)
				{
					hardScore -= Math.abs(judgedTime - Tournament.EACH_SCHOOL_JUDGED);
				}
			}
		}
		
		return HardMediumSoftScore.valueOf(hardScore, mediumScore, softScore);
	}
	
	public static ArrayList<Integer> getSchoolCountPerJudges(int numberOfSchool)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int schoolToJudge 		= (numberOfSchool * Tournament.EACH_SCHOOL_JUDGED) / Tournament.NUMBER_OF_JUDGES;
		int judgingExtra		= (numberOfSchool * Tournament.EACH_SCHOOL_JUDGED) % Tournament.NUMBER_OF_JUDGES;
		
		for(int currentJudge = 0; currentJudge < Tournament.NUMBER_OF_JUDGES; currentJudge++)
		{
			int schoolsForCurrentJudge = schoolToJudge;
			if(currentJudge < judgingExtra)
			{
				schoolsForCurrentJudge++;
			}
			ret.add(schoolsForCurrentJudge);
		}
		
		return ret;
	}
}
