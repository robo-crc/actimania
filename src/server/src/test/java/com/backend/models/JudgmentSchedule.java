package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

import com.backend.models.optaplanner.TeamAssignment;
import com.backend.models.optaplanner.TournamentSolution;

public class JudgmentSchedule
{
	@BeforeClass
	public static void setUp()
    {
    }
	
	@AfterClass
	public static void tearDown()
	{
	}
	
	/*
	 *  - No 2 consecutive games
		- Between 1 and 3 games per block, ideally 2
		- 8 games per team
		- Do not play 2 times with the same team and ideally not 2 times against the same team.
	 */
	@Test
	public void testSchedualGeneration()
	{
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 0; i < Tournament.SCHOOL_NUMBER; i++)
		{
			String iString = String.valueOf(i);
			schools.add(new School(null, iString));
		}
		
		ArrayList<TeamAssignment> teamAssignments = new ArrayList<TeamAssignment>();
		for(int i = 0; i < Tournament.SCHOOL_NUMBER * Tournament.EACH_SCHOOL_JUDGED; i++)
		{
			teamAssignments.add(new TeamAssignment(i));
		}
		
 		TournamentSolution solvedTournament = null;
		try
		{
			SolverFactory solverFactory = SolverFactory.createFromXmlResource("com/backend/models/optaplanner/judgeAssignmentConfig.xml");
	        Solver solver = solverFactory.buildSolver();
	        
	        TournamentSolution unsolvedTournament = new TournamentSolution(schools, teamAssignments);
	        
	        solver.addEventListener(new SolverEventListener<TournamentSolution>() {
	            public void bestSolutionChanged(BestSolutionChangedEvent<TournamentSolution> event) {

	                // Ignore invalid solutions
	                if (event.isNewBestSolutionInitialized()) 
	                {
	                	System.out.println(event.getNewBestSolution().getScore().toString());
	                }
	            }
	        });
	        
	        solver.solve(unsolvedTournament);
	     
	        solvedTournament = (TournamentSolution) solver.getBestSolution();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		solvedTournament.outputJudgmentSolution();
        
        Validate.isTrue(solvedTournament.getScore().getHardScore() == 0);
	}
}
