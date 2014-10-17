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

@PlanningSolution
public class TournamentPlanner implements Solution<HardMediumSoftScore>
{
	public ArrayList<School> schools;
	public ArrayList<GameProcess> games;
	
	public TournamentPlanner() 
	{
		
	}
	public TournamentPlanner(ArrayList<School> _schools, ArrayList<GameProcess> _games) 
	{
		schools = _schools;
		games = _games;
	}
	
	private HardMediumSoftScore 		score;
	
	@Override
	public Collection<? extends Object> getProblemFacts() 
	{
		List<Object> facts = new ArrayList<Object>();

        facts.addAll(schools);

        // Do not add the planning entity's (games) because that will be done automatically
        return facts;
	}
	
	@ValueRangeProvider(id = "blueTeam")
	public ArrayList<School> getSchoolsBlue()
	{
		return schools;
	}
	
	@ValueRangeProvider(id = "yellowTeam")
	public ArrayList<School> getSchoolsYellow()
	{
		return schools;
	}

	@PlanningEntityCollectionProperty
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
}
