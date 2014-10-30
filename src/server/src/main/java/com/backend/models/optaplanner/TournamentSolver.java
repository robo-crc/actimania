package com.backend.models.optaplanner;

import java.util.ArrayList;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;

import com.backend.models.School;
import com.framework.models.Essentials;

public class TournamentSolver
{
	public static TournamentSolution solve(Essentials essentials, int multiplier, int schoolsPerAssignment, String configXML)
	{
		ArrayList<School> schools = School.getSchools(essentials);
		
		ArrayList<TeamAssignment> teamAssignments = new ArrayList<TeamAssignment>();
		for(int i = 0; i < schools.size() * multiplier; i++)
		{
			for(int j = 0; j < schoolsPerAssignment; j++)
			{
				teamAssignments.add(new TeamAssignment(i));
			}
		}
		
 		TournamentSolution solvedTournament = null;
		try
		{
			SolverFactory solverFactory = SolverFactory.createFromXmlResource(configXML);
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
		
		return solvedTournament;
	}
}
