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

import com.backend.models.optaplanner.GameProcess;
import com.backend.models.optaplanner.TeamAssignment;
import com.backend.models.optaplanner.TournamentSolution;

public class TournamentTests 
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
		int SCHOOL_NUMBER = 30;
		int NUMBER_OF_GAME = SCHOOL_NUMBER * Tournament.GAME_PER_SCHOOL / (Tournament.SCHOOLS_PER_TEAM * 2);
		
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 0; i < SCHOOL_NUMBER; i++)
		{
			String iString = String.valueOf(i);
			schools.add(new School(null, iString));
		}
		
		ArrayList<TeamAssignment> teamAssignments = new ArrayList<TeamAssignment>();
		ArrayList<GameProcess> games = new ArrayList<GameProcess>();
		for(int i = 0; i < NUMBER_OF_GAME; i++)
		{
			ArrayList<School> blueTeam = new ArrayList<School>();
			ArrayList<School> yellowTeam = new ArrayList<School>();
			
			GameProcess game = new GameProcess(blueTeam, yellowTeam);
			
			for(int j = 0; j < Tournament.SCHOOLS_PER_TEAM * 2; j++)
			{
				teamAssignments.add(new TeamAssignment(i));
				//blueTeam.add(new School(null, "null"));
				//yellowTeam.add(new School(null, "null"));
			}
			games.add(game);
		}
		
 		TournamentSolution solvedTournament = null;
		try
		{
			SolverFactory solverFactory = SolverFactory.createFromXmlResource("com/backend/models/optaplanner/solverConfig.xml");
	        Solver solver = solverFactory.buildSolver();
	        
	        TournamentSolution unsolvedTournament = new TournamentSolution(schools, games, teamAssignments);
	        
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
		
		solvedTournament.outputTournamentSolution();
        
        Validate.isTrue(solvedTournament.getScore().getHardScore() == 0);
	}
}
