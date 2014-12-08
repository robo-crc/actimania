package com.backend.models.optaplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import com.backend.models.School;
import com.backend.models.Tournament;

public class TournamentScoreCalculator implements EasyScoreCalculator<TournamentSolution> 
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
		int IDEAL_GAMES_PER_BLOCK = Tournament.GAME_PER_SCHOOL / Tournament.BLOCK_NUMBERS;
		boolean USE_IDEAL_GAMES_SCORE = (numberOfGames % Tournament.BLOCK_NUMBERS) == 0;

		ArrayList<GameProcess> gamesToIteration = new ArrayList<GameProcess>();
		
		int blockStart = 0;
		
		ArrayList<Integer> gamesPerBlock = getGamesPerBlockCount(numberOfGames);
		
		boolean fastExit = false;
		
		for(int currentBlockNumber = 0; currentBlockNumber < gamesPerBlock.size() && !fastExit; currentBlockNumber++)
		{
			int blockEnd = blockStart + gamesPerBlock.get(currentBlockNumber);
			GameProcess previous1Game = fakeGame;
			GameProcess previous2Game = previous1Game;
			GameProcess previous3Game = previous2Game;
			ArrayList<GameProcess> gamesThisBlock = new ArrayList<GameProcess>();
			
			for(int i = blockStart; i < blockEnd; i++)
			{
				// We start by checking that there's no null result.
				int startJ = i * Tournament.SCHOOLS_PER_TEAM * 2;
				ArrayList<School> blueTeam = new ArrayList<School>();
				ArrayList<School> yellowTeam = new ArrayList<School>();
				
				for(int j = startJ; j < startJ + Tournament.SCHOOLS_PER_TEAM; j++)
				{
					School blueSchool = tournament.getTeamAssignment().get(j).getSchool();
					School yellowSchool = tournament.getTeamAssignment().get(j + Tournament.SCHOOLS_PER_TEAM).getSchool();
	
					blueTeam.add(blueSchool);
					yellowTeam.add(yellowSchool);
					
					if(yellowSchool == null || blueSchool == null)
					{
						hardScore -= 1000;
						fastExit = true;
						break;
					}
				}
				
				// We got a null result, so skip.
				if(fastExit)
				{
					break;
				}
				
				GameProcess game = new GameProcess(blueTeam, yellowTeam);
				
				gamesToIteration.add(game);
				gamesThisBlock.add(game);
				
				for(School school : game.getSchools())
				{
					if( game.getSchools().indexOf(school) != game.getSchools().lastIndexOf(school) )
					{
						// The school is present 2 times in the game!
						hardScore -= 1;
					}
					
					// School played in previous game.
					if( previous1Game.getSchools().contains(school) )
					{
						hardScore -= 1;
					}
					
					if(previous2Game.getSchools().contains(school))
					{
						mediumScore -= 1;
						softScore -= 1;
					}
					
					if(previous3Game.getSchools().contains(school))
					{
						softScore -= 1;
					}
				}
				
				for(School school : game.getBlueTeam())
				{
					if( hasPlayed(schoolWith, school, game.getBlueTeam()) )
					{
						mediumScore -= 5;
					}
					
					if( hasPlayed(schoolAgainst, school, game.getYellowTeam()) )
					{
						mediumScore -= 2;
					}
				}
				
				for(School school : game.getYellowTeam())
				{
					if( hasPlayed(schoolWith, school, game.getYellowTeam()) )
					{
						mediumScore -= 5;
					}
					
					if( hasPlayed(schoolAgainst, school, game.getBlueTeam()) )
					{
						mediumScore -= 2;
					}
				}
				
				manageSchoolWithAgainst(schoolWith, schoolAgainst, game.getBlueTeam(), game.getYellowTeam());
				previous3Game = previous2Game;
				previous2Game = previous1Game;
				previous1Game = game;
			}
			
			// Check if at the end of this round, we have the required games played
			for(School school : tournament.getSchools())
			{
				int gamesPlayed = TournamentSolution.getGamesPlayed(gamesThisBlock, school);
				
				// In each block, a school should play between 1 and 3 games.
				if( gamesPlayed < IDEAL_GAMES_PER_BLOCK - 1 || gamesPlayed > IDEAL_GAMES_PER_BLOCK + 1 )
				{
					hardScore -= 1;
				}
				
				// Let's give a small bonus when we play 2 games in a round.
				if( USE_IDEAL_GAMES_SCORE && gamesPlayed != IDEAL_GAMES_PER_BLOCK )
				{
					softScore -= 1;
				}
			}
			
			blockStart = blockEnd;
		}

		for(School school : tournament.schools)
		{
			hardScore -= Math.abs(Tournament.GAME_PER_SCHOOL - TournamentSolution.getGamesPlayed(gamesToIteration, school));
		}
		
		tournament.setGames(gamesToIteration);
		tournament.setSchoolWith(schoolWith);
		tournament.setSchoolAgainst(schoolAgainst);
		
		return HardMediumSoftScore.valueOf(hardScore, mediumScore, softScore);
	}
	
	public static ArrayList<Integer> getGamesPerBlockCount(int numberOfGames)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int gamesPerBlock 		= numberOfGames / Tournament.BLOCK_NUMBERS;
		int blockWithExtraGames = numberOfGames % Tournament.BLOCK_NUMBERS;
		
		for(int currentBlockCount = 0; currentBlockCount <Tournament.BLOCK_NUMBERS; currentBlockCount++)
		{
			int gamesThisBlock = gamesPerBlock;
			if(currentBlockCount < blockWithExtraGames)
			{
				gamesThisBlock++;
			}
			ret.add(gamesThisBlock);
		}
		
		return ret;
	}
	
	public static int getBlockForIndex(int index, int numberOfGames)
	{
		int blockStart = 0;
		for(int currentBlockNumber = 0; currentBlockNumber < getGamesPerBlockCount(numberOfGames).size(); currentBlockNumber++)
		{
			int blockEnd = blockStart + getGamesPerBlockCount(numberOfGames).get(currentBlockNumber);
			if(index >= blockStart && index < blockEnd)
			{
				return currentBlockNumber;
			}
			blockStart = blockEnd;
		}
		
		return -1;
	}
	
	public static boolean isStartOfBlock(int index, int numberOfGames)
	{
		int blockStart = 0;
		for(int currentBlockNumber = 0; currentBlockNumber < getGamesPerBlockCount(numberOfGames).size(); currentBlockNumber++)
		{
			int blockEnd = blockStart + getGamesPerBlockCount(numberOfGames).get(currentBlockNumber);
			if(index == blockStart)
			{
				return true;
			}
			blockStart = blockEnd;
		}
		return false;
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
