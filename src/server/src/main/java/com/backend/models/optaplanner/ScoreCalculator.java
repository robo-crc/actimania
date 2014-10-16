package com.backend.models.optaplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import com.backend.models.Game;
import com.backend.models.GameEvent;
import com.backend.models.School;
import com.backend.models.SchoolPenalty;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;

public class ScoreCalculator implements EasyScoreCalculator<TournamentPlanner> 
{
	private static final int GAME_PER_SCHOOL = 8;
	private static final int SCHOOLS_PER_TEAM = 2;
	private static final int BLOCK_NUMBERS = 4;
	
	@Override
	public HardMediumSoftScore calculateScore(TournamentPlanner tournament) 
	{
		Map<School, Map<School, Integer>> schoolWith = new HashMap<School, Map<School, Integer>>();
		Map<School, Map<School, Integer>> schoolAgainst = new HashMap<School, Map<School, Integer>>();
		
		initSchoolWithAgainst(schoolWith, tournament.schools);
		initSchoolWithAgainst(schoolAgainst, tournament.schools);
		
		// fakeGame is used as previous game when starting a round.
		Game fakeGame = new Game(null, GameTypeEnum.PRELIMINARY, new ArrayList<School>(), new ArrayList<School>(), new ArrayList<GameEvent>(), new ArrayList<SchoolPenalty>(), new ArrayList<School>());
		
		int numberOfGames = tournament.games.size();
		
		Game previousGame 		= fakeGame;
		int currentBlockNumber 	= 0;
		int gamesPerBlock 		= numberOfGames / BLOCK_NUMBERS;
		int blockWithExtraGames = numberOfGames % gamesPerBlock;
		int blockStartAtGame 	= 0;
		
		ArrayList<Game> gamesToIteration = new ArrayList<Game>();
		
		int hardScore 	= 0;
		int mediumScore = 0;
		int softScore 	= 0;
		
		for(int i = 0; i < numberOfGames; i++)
		{
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
				gamesThisBlock = gamesPerBlock;
				
				if(currentBlockNumber < blockWithExtraGames)
				{
					gamesThisBlock++;
				}
			}
			
			// Maximum of 2 games per round.
			int maxGamePlayed = (currentBlockNumber + 1) * 2;
			if(maxGamePlayed < GAME_PER_SCHOOL)
			{
				// We can go to one over the limit.
				maxGamePlayed++;
			}
			
			Game game = tournament.games.get(i);
			gamesToIteration.add(game);
			
			for(School school : game.getSchools())
			{
				if( Tournament.getGamesPlayed(gamesToIteration, school, GameTypeEnum.PRELIMINARY).size() > maxGamePlayed)
				{
					hardScore -= 1;
				}
				if( previousGame.getSchools().contains(school) )
				{
					hardScore -= 1;
				}
			}
			
			if(game.yellowTeam.size() != game.blueTeam.size())
			{
				hardScore -= 1;
			}
			
			if(game.blueTeam.size() != SCHOOLS_PER_TEAM)
			{
				hardScore -= 1;
			}
			
			if(game.yellowTeam.size() != SCHOOLS_PER_TEAM)
			{
				hardScore -= 1;
			}
			
			for(School school : game.blueTeam)
			{
				if( hasPlayed(schoolWith, school, game.blueTeam) )
				{
					mediumScore -= 1;
				}
				
				if( hasPlayed(schoolAgainst, school, game.yellowTeam) )
				{
					softScore -= 1;
				}
			}
			
			for(School school : game.yellowTeam)
			{
				if( hasPlayed(schoolWith, school, game.yellowTeam) )
				{
					mediumScore -= 1;
				}
				
				if( hasPlayed(schoolAgainst, school, game.blueTeam) )
				{
					softScore -= 1;
				}
			}
			
			manageSchoolWithAgainst(schoolWith, schoolAgainst, game.blueTeam, game.yellowTeam);
			previousGame = tournament.games.get(i);
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
