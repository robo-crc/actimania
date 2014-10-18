package com.backend.models.optaplanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import com.backend.models.School;
import com.backend.models.Tournament;

@PlanningSolution
public class TournamentSolution implements Solution<HardMediumSoftScore>
{
	public ArrayList<School> schools;
	public ArrayList<GameProcess> games;
	
	private List<TeamAssignment> teamAssignment;

	private HardMediumSoftScore 		score;
	
	public TournamentSolution() 
	{
		
	}
	public TournamentSolution(ArrayList<School> _schools, ArrayList<GameProcess> _games, ArrayList<TeamAssignment> _teamAssignments) 
	{
		schools = _schools;
		games = _games;
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
	
	public void outputTournamentSolution()
	{
		int GAMES_PER_BLOCK = games.size() / Tournament.BLOCK_NUMBERS;
		for(int row = 0; row < schools.size(); row++)
		{
			School currentSchool = schools.get(row);
			System.out.print(currentSchool.name + "\t");
			int nbGames = 0;
			for(int col = 0; col < games.size(); col++)
			{
				if(col != 0 && col % GAMES_PER_BLOCK == 0)
				{
					System.out.print(" ");
				}
				
				boolean isInGame = false;
				if(games.get(col).getBlueTeam().contains(currentSchool))
				{
					System.out.print("b");
					isInGame = true;
				}
				if(games.get(col).getYellowTeam().contains(currentSchool))
				{
					System.out.print("y");
					isInGame = true;
				}
				if(!isInGame)
				{
					System.out.print("_");
				}
				else
				{
					nbGames++;
				}
			}
			System.out.println("\t" + nbGames);
		}
	}
}
