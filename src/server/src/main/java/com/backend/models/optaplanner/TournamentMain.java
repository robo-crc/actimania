package com.backend.models.optaplanner;

import com.backend.models.Tournament;
import com.framework.helpers.Database;
import com.framework.models.Essentials;

public class TournamentMain {

	public static void main(String[] args) 
	{
		try(Essentials essentials = new Essentials(new Database(Database.DatabaseType.PRODUCTION), null, null, null, null))
		{
			TournamentSolution solvedTournament = TournamentSolver.solve(essentials, Tournament.GAME_PER_SCHOOL / (Tournament.SCHOOLS_PER_TEAM * 2), Tournament.SCHOOLS_PER_TEAM * 2, "com/backend/models/optaplanner/solverConfig.xml");
			if(solvedTournament != null)
			{
				solvedTournament.outputJudgmentSolution();
			}
		}
	}
}
