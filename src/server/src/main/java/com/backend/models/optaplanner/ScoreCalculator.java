package com.backend.models.optaplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import com.backend.models.School;
import com.backend.models.Tournament;

public class ScoreCalculator implements EasyScoreCalculator<TournamentSolution> 
{
	@Override
	public HardMediumSoftScore calculateScore(TournamentSolution tournament) 
	{
		int hardScore 	= 0;
		int mediumScore = 0;
		int softScore 	= 0;
		
		Map<School, Map<School, Integer>> schoolWith = new HashMap<School, Map<School, Integer>>();
		Map<School, Map<School, Integer>> schoolAgainst = new HashMap<School, Map<School, Integer>>();
		
		initSchoolWithAgainst(schoolWith, tournament.schools);
		initSchoolWithAgainst(schoolAgainst, tournament.schools);
		
		// fakeGame is used as previous game when starting a round.
		GameProcess fakeGame = new GameProcess(new ArrayList<School>(), new ArrayList<School>());
		
		int numberOfGames = tournament.getTeamAssignment().size() / (Tournament.SCHOOLS_PER_TEAM * 2);

		GameProcess previousGame = fakeGame;
		GameProcess previousPreviousGame = previousGame;
		int currentBlockNumber 	= 0;
		int gamesPerBlock 		= numberOfGames / Tournament.BLOCK_NUMBERS;
		int blockWithExtraGames = numberOfGames % gamesPerBlock;
		int blockStartAtGame 	= 0;
		
		ArrayList<GameProcess> gamesToIteration = new ArrayList<GameProcess>();
		
		for(int i = 0; i < numberOfGames; i++)
		{
			int startJ = i * Tournament.SCHOOLS_PER_TEAM * 2;
			ArrayList<School> blueTeam = new ArrayList<School>();
			ArrayList<School> yellowTeam = new ArrayList<School>();
			boolean skip = false;
			for(int j = startJ; j < startJ + Tournament.SCHOOLS_PER_TEAM; j++)
			{
				School blueSchool = tournament.getTeamAssignment().get(j).getSchool();
				School yellowSchool = tournament.getTeamAssignment().get(j + Tournament.SCHOOLS_PER_TEAM).getSchool();

				blueTeam.add(blueSchool);
				yellowTeam.add(yellowSchool);
				
				if(yellowSchool == null || blueSchool == null)
				{
					hardScore -= 5;
					skip = true;
				}
			}
			
			if(skip)
			{
				continue;
			}
			// This section is to calculate the number of games per block of games.
			// When we start a new block, it can be the same robot as the previous round.
			int gamesThisBlock = gamesPerBlock;
			if(currentBlockNumber < blockWithExtraGames)
			{
				gamesThisBlock++;
			}
			if(i >= blockStartAtGame + gamesThisBlock)
			{
				blockStartAtGame = i;
				currentBlockNumber++;
				previousGame = fakeGame;
				previousPreviousGame = previousGame;
				gamesThisBlock = gamesPerBlock;
				
				if(currentBlockNumber < blockWithExtraGames)
				{
					gamesThisBlock++;
				}
			}
			
			// Maximum of 2 games per round.
			int maxGamePlayed = (currentBlockNumber + 1) * 2;
			if(maxGamePlayed < Tournament.GAME_PER_SCHOOL)
			{
				// We can go to one over the limit.
				maxGamePlayed++;
			}
			
			int minGamePlayed = (currentBlockNumber) * 2 - 1;
			
			GameProcess game = new GameProcess(blueTeam, yellowTeam);
			
			gamesToIteration.add(game);
			
			for(School school : game.getSchools())
			{
				if( game.getSchools().indexOf(school) != game.getSchools().lastIndexOf(school) )
				{
					// The school is present 2 times in the game!
					hardScore -= 1;
				}

				int gamesPlayed = TournamentSolution.getGamesPlayed(gamesToIteration, school);
				// At this point in the tournament, a school should not have played more than maxGamePlayed.
				if( gamesPlayed > maxGamePlayed)
				{
					hardScore -= 1;
				}
				
				if(gamesPlayed < minGamePlayed)
				{
					hardScore -= 1;
				}
				
				// School played in previous game.
				if( previousGame.getSchools().contains(school) )
				{
					hardScore -= 1;
				}
				
				if(previousPreviousGame.getSchools().contains(school))
				{
					softScore -= 1;
				}
			}
			
			for(School school : game.getBlueTeam())
			{
				if( hasPlayed(schoolWith, school, game.getBlueTeam()) )
				{
					mediumScore -= 1;
				}
				
				if( hasPlayed(schoolAgainst, school, game.getYellowTeam()) )
				{
					softScore -= 5;
				}
			}
			
			for(School school : game.getYellowTeam())
			{
				if( hasPlayed(schoolWith, school, game.getYellowTeam()) )
				{
					mediumScore -= 1;
				}
				
				if( hasPlayed(schoolAgainst, school, game.getBlueTeam()) )
				{
					softScore -= 1;
				}
			}
			
			manageSchoolWithAgainst(schoolWith, schoolAgainst, game.getBlueTeam(), game.getYellowTeam());
			previousPreviousGame = previousGame;
			previousGame = game;
		}
		
		tournament.games = gamesToIteration;

		for(School school : tournament.schools)
		{
			if( TournamentSolution.getGamesPlayed(tournament.games, school) != Tournament.GAME_PER_SCHOOL)
			{
				hardScore -= 1;
			}
		}
		
		return HardMediumSoftScore.valueOf(hardScore, mediumScore, softScore);
	}
	
	// Manage the schoolWith and schoolAgainst array.
	public static void manageSchoolWithAgainst(Map<School, Map<School, Integer>> schoolWith, Map<School, Map<School, Integer>> schoolAgainst, ArrayList<School> blueTeam, ArrayList<School> yellowTeam)
	{
		for( School blueSchool : blueTeam )
		{
			for( School blueSchoolWith : blueTeam )
			{
				if(!blueSchoolWith.equals(blueSchool))
				{
					schoolWith.get(blueSchool).put(blueSchoolWith, schoolWith.get(blueSchool).get(blueSchoolWith) + 1);
				}
			}
			
			for( School yellowSchool : yellowTeam )
			{
				schoolAgainst.get(blueSchool).put(yellowSchool, schoolWith.get(blueSchool).get(yellowSchool) + 1);
			}
		}
		
		for( School yellowSchool : yellowTeam )
		{
			for( School yellowSchoolWith : yellowTeam )
			{
				if(!yellowSchoolWith.equals(yellowSchool))
				{
					schoolWith.get(yellowSchool).put(yellowSchoolWith, schoolWith.get(yellowSchool).get(yellowSchoolWith) + 1);
				}
			}
			
			for( School blueSchool : blueTeam )
			{
				schoolAgainst.get(yellowSchool).put(blueSchool, schoolWith.get(yellowSchool).get(blueSchool) + 1);
			}
		}
	}
	
	public static void initSchoolWithAgainst(Map<School, Map<School, Integer>> schoolWithAgainstList, ArrayList<School> schools)
	{
		for(School school : schools)
		{
			Map<School, Integer> mapSchool = new TreeMap<School, Integer>();
			schoolWithAgainstList.put(school, mapSchool);
			for(School schoolWithAgainst : schools)
			{
				mapSchool.put(schoolWithAgainst, 0);
			}
		}
	}
	
	public static boolean hasPlayed(Map<School, Map<School, Integer>> schoolList, School schoolCompare, ArrayList<School> schoolWithAgainstList)
	{
		for(School schoolWithAgainst : schoolWithAgainstList)
		{
			if( !schoolWithAgainst.equals(schoolCompare) && schoolList.get(schoolCompare).get(schoolWithAgainst) > 0 )
			{
				return true;
			}
		}
		return false;
	}
}
