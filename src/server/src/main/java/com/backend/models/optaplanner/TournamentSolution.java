package com.backend.models.optaplanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import com.backend.models.School;

@PlanningSolution
public class TournamentSolution implements Solution<HardMediumSoftScore>
{
	public ArrayList<School> schools;
	private ArrayList<GameProcess> games;
	private Map<School, Map<School, Integer>> schoolWith;
	private Map<School, Map<School, Integer>> schoolAgainst;
	
	private List<TeamAssignment> teamAssignment;
	private HardMediumSoftScore 		score;
	
	private ArrayList<ArrayList<School>> judgements;
	
	public TournamentSolution() 
	{
		
	}
	public TournamentSolution(ArrayList<School> _schools, ArrayList<TeamAssignment> _teamAssignments) 
	{
		schools = _schools;
		teamAssignment = _teamAssignments;
	}
	
	@Override
	public Collection<? extends Object> getProblemFacts() 
	{
		List<Object> facts = new ArrayList<Object>();

        facts.addAll(schools);
        facts.addAll(games);

        // Do not add the planning entity's (games) because that will be done automatically
        return facts;
	}
	
	@ValueRangeProvider(id = "school")
	public ArrayList<School> getSchools()
	{
		return schools;
	}

	public ArrayList<GameProcess> getGames()
	{
		return games;
	}
	
	@Override
	public HardMediumSoftScore getScore() 
	{
		return score;
	}
	
	@Override
	public void setScore(HardMediumSoftScore arg0) 
	{
		score = arg0;
	}
	
	public static int getGamesPlayed(ArrayList<GameProcess> games, School school)
	{
		int gamesPlayed = 0;
		for(GameProcess game : games)
		{
			if(game.getBlueTeam().contains(school) || game.getYellowTeam().contains(school))
			{
				gamesPlayed++;
			}
		}
		return gamesPlayed;
	}
	
	@PlanningEntityCollectionProperty
	public List<TeamAssignment> getTeamAssignment() {
		return teamAssignment;
	}
	
	public void setTeamAssignment(List<TeamAssignment> teamAssignmentList) {
		this.teamAssignment = teamAssignmentList;
	}
	
	public void setGames(ArrayList<GameProcess> _games)
	{
		games = _games;
	}
	
	
	public void outputTournamentSolution()
	{
		for(int row = 0; row < schools.size(); row++)
		{
			School currentSchool = schools.get(row);
			System.out.print(currentSchool.name + "\t");
			int nbGames = 0;
			for(int col = 0; col < games.size(); col++)
			{
				if(col != 0 && TournamentScoreCalculator.isStartOfBlock(col, games.size()))
				{
					System.out.print(" ");
				}
				
				if(games.get(col).getBlueTeam().contains(currentSchool))
				{
					System.out.print("b");
					nbGames++;
				}
				else if(games.get(col).getYellowTeam().contains(currentSchool))
				{
					System.out.print("y");
					nbGames++;
				}
				else
				{
					System.out.print("_");
				}
			}
			System.out.println("\t" + nbGames);
		}
		
		System.out.println("\nSchool With table");
		
		outputTableMatchtup(schoolWith);
		
		System.out.println("\nSchool Against table");
		
		outputTableMatchtup(schoolAgainst);
	}
	
	public void outputTableMatchtup(Map<School, Map<School, Integer>> schoolWithAgainst)
	{
		System.out.print("   ");
		for(School schoolRow : schools)
		{
			System.out.print(StringUtils.rightPad(schoolRow.name, 3));
		}
		System.out.println();
		for(School schoolRow : schools)
		{
			System.out.print(StringUtils.rightPad(schoolRow.name, 3));
			for(School schoolCol : schools)
			{
				if(schoolRow == schoolCol)
				{
					System.out.print(StringUtils.rightPad("-", 3));
				}
				else
				{
					String occurences = String.valueOf(schoolWithAgainst.get(schoolRow).get(schoolCol));
					System.out.print(StringUtils.rightPad(occurences, 3));
				}
			}
			System.out.print("\n");
		}
	}
	
	public void outputJudgmentSolution()
	{
		System.out.print("   ");
		for(School school : schools)
		{
			System.out.print(StringUtils.rightPad(school.name, 3));
		}
		
		System.out.println();
		for( int i = 0; i < judgements.size(); i++ )
		{
			System.out.print(StringUtils.rightPad(String.valueOf(i), 3));
			for(School school : schools)
			{
				if(judgements.get(i).contains(school))
				{
					System.out.print(StringUtils.rightPad("Y", 3));
				}
				else
				{
					System.out.print(StringUtils.rightPad("-", 3));
				}
			}
			System.out.print("\n");
		}
		
		System.out.println("\n\nJudgement same occurences");
		System.out.print("   ");
		for(int judgeCount = 0; judgeCount < judgements.size(); judgeCount++)
		{
			System.out.print(StringUtils.rightPad(String.valueOf(judgeCount), 3));
		}		
		System.out.print("\n");
		for(int judge1 = 0; judge1 < judgements.size(); judge1++)
		{
			System.out.print(StringUtils.rightPad(String.valueOf(judge1), 3));
			for(int judge2 = 0; judge2 < judgements.size(); judge2++)
			{
				if(judge1 != judge2)
				{
					int sameSchools = 0;
					for(School schoolJudge1 : judgements.get(judge1))
					{
						for(School schoolJudge2 : judgements.get(judge2))
						{
							if(schoolJudge1.equals(schoolJudge2))
							{
								sameSchools++;
							}
						}
					}
					System.out.print(StringUtils.rightPad(String.valueOf(sameSchools), 3));
				}
				else
				{
					System.out.print(StringUtils.rightPad("-", 3));
				}
			}
			System.out.print("\n");
		}
	}
	
	public Map<School, Map<School, Integer>> getSchoolWith() 
	{
		return schoolWith;
	}
	
	public void setSchoolWith(Map<School, Map<School, Integer>> schoolWith) 
	{
		this.schoolWith = schoolWith;
	}
	
	public Map<School, Map<School, Integer>> getSchoolAgainst() 
	{
		return schoolAgainst;
	}
	
	public void setSchoolAgainst(Map<School, Map<School, Integer>> schoolAgainst) 
	{
		this.schoolAgainst = schoolAgainst;
	}
	
	public ArrayList<ArrayList<School>> getJudgements() 
	{
		return judgements;
	}
	
	public void setJudgements(ArrayList<ArrayList<School>> judgements) 
	{
		this.judgements = judgements;
	}
	
}
