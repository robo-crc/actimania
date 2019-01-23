package com.main;

import java.util.ArrayList;
import java.util.Scanner;

import org.bson.types.ObjectId;
import com.backend.models.School;
import com.backend.models.Tournament;
import com.backend.models.optaplanner.GameProcess;
import com.backend.models.optaplanner.TournamentScoreCalculator;
import com.backend.models.optaplanner.TournamentSolution;
import com.backend.models.optaplanner.TournamentSolver;

public class GenerateScheduleCSV
{
	public static void main(String[] args) 
	{
		Scanner keyboard = new Scanner(System.in);
		setupSchedule(keyboard);
		keyboard.close();
	}
	
	public static void setupSchedule(Scanner keyboard)
	{
		System.out.println("How many schools?");
		int schoolsCount = keyboard.nextInt();
		
		ArrayList<School> schools = new ArrayList<School>();
		for(int i = 0; i < schoolsCount; i++)
		{
			schools.add(new School(new ObjectId(), String.valueOf(i + 1)));
		}
    	
		if((schools.size() * Tournament.GAME_PER_SCHOOL) % (Tournament.SCHOOLS_PER_TEAM * 2) != 0 )
		{
        	System.out.println("There's no possible solution. Please change Tournament.GAME_PER_SCHOOL value.");
        	return;
		}
		
		TournamentSolution solvedTournament = TournamentSolver.solve(schools, Tournament.GAME_PER_SCHOOL / (Tournament.SCHOOLS_PER_TEAM * 2), Tournament.SCHOOLS_PER_TEAM * 2, "com/backend/models/optaplanner/TournamentSolverConfig.xml");
		if(solvedTournament != null)
		{
			solvedTournament.outputTournamentSolution();
			
			if(solvedTournament != null)
	        {
				System.out.println("");
				System.out.println("CSV format");
				
     		    ArrayList<GameProcess> games = solvedTournament.getGames();
        		
        		StringBuilder strBuilder = new StringBuilder();
        		strBuilder.append("Block #;");
        		strBuilder.append("Game #;");
        		
        		for(int blueSchoolIndex = 0; blueSchoolIndex < Tournament.SCHOOLS_PER_TEAM; blueSchoolIndex++)
        		{
        			strBuilder.append("Blue ");
        			strBuilder.append(blueSchoolIndex + 1);
        			strBuilder.append(";");
        		}
        		
        		for(int yellowSchoolIndex = 0; yellowSchoolIndex < Tournament.SCHOOLS_PER_TEAM; yellowSchoolIndex++)
        		{
        			strBuilder.append("Yellow ");
        			strBuilder.append(yellowSchoolIndex + 1);
        			strBuilder.append(";");
        		}
        		
        		strBuilder.append("\n");
        		
        		for(int currentGame = 0; currentGame < games.size(); currentGame++)
        		{
        			int blockNumber = TournamentScoreCalculator.getBlockForIndex(currentGame, games.size());
        			strBuilder.append(blockNumber + 1);
        			strBuilder.append(";");
        			strBuilder.append(currentGame + 1);
        			strBuilder.append(";");
        			
        			for(int blueSchoolIndex = 0; blueSchoolIndex < Tournament.SCHOOLS_PER_TEAM; blueSchoolIndex++)
            		{
        				strBuilder.append(games.get(currentGame).getBlueTeam().get(blueSchoolIndex).name);
        				strBuilder.append(";");
            		}
        			
        			for(int yellowSchoolIndex = 0; yellowSchoolIndex < Tournament.SCHOOLS_PER_TEAM; yellowSchoolIndex++)
            		{
        				strBuilder.append(games.get(currentGame).getYellowTeam().get(yellowSchoolIndex).name);
        				strBuilder.append(";");
            		}
        			
        			strBuilder.append("\n");
        		}
        		
        		System.out.println(strBuilder.toString());
        	}
        }
	}
}
