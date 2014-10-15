package com.backend.models.optaplanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.backend.models.Game;
import com.backend.models.School;

@PlanningSolution
public class ScheduleSolution implements Solution<HardSoftScore>
{
	public ArrayList<School> 	schools;
	private ArrayList<Game> 	games;
	private HardSoftScore 		score;
	
	@Override
	public Collection<? extends Object> getProblemFacts() 
	{
		List<Object> facts = new ArrayList<Object>();

        facts.addAll(schools);

        // Do not add the planning entity's (games) because that will be done automatically
        return facts;
	}
	
	@Override
	public HardSoftScore getScore() 
	{
		return score;
	}
	
	@Override
	public void setScore(HardSoftScore arg0) 
	{
		score = arg0;
	}
}
