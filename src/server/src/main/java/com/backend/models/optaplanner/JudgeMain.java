package com.backend.models.optaplanner;
import com.backend.models.Tournament;
import com.framework.helpers.Database;
import com.framework.models.Essentials;

public class JudgeMain {

	public static void main(String[] args) 
	{
		try(Essentials essentials = new Essentials(new Database(Database.DatabaseType.PRODUCTION), null, null, null, null))
		{
			TournamentSolution solvedTournament = TournamentSolver.solve(essentials, Tournament.EACH_SCHOOL_JUDGED, 1, "com/backend/models/optaplanner/judgeAssignmentConfig.xml");
			if(solvedTournament != null)
			{
				solvedTournament.outputJudgmentSolution();
			}
		}
	}

}
