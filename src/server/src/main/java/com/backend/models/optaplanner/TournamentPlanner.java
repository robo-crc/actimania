package com.backend.models.optaplanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import com.backend.models.Game;
import com.backend.models.School;
import com.backend.models.Tournament;

@PlanningSolution
public class TournamentPlanner extends Tournament implements Solution<HardMediumSoftScore>
{
	public TournamentPlanner(ArrayList<School> _schools, ArrayList<Game> _games) {
		super(_schools, _games);
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

	public ArrayList<Game> getGames()
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
}
