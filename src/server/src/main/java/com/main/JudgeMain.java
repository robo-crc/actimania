package com.main;
import com.backend.models.School;
import com.backend.models.Tournament;
import com.backend.models.optaplanner.TournamentSolution;
import com.backend.models.optaplanner.TournamentSolver;
import com.framework.helpers.Database;
import com.framework.models.Essentials;

public class JudgeMain {

	public static void main(String[] args) 
	{
		try(Essentials essentials = new Essentials(new Database(Database.DatabaseType.PRODUCTION), null, null, null, null))
		{
			TournamentSolution solvedTournament = TournamentSolver.solve(School.getSchools(essentials), Tournament.EACH_SCHOOL_JUDGED, 1, "com/backend/models/optaplanner/JudgeAssignmentConfig.xml");
			if(solvedTournament != null)
			{
				solvedTournament.outputJudgmentSolution();
			}
		}
	}

}
