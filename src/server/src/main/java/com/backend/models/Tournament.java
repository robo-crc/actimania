package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.Validate;

import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tournament 
{
	@JsonIgnore
	public static final int PRELIMINARY_GAMES_SKIPPED_IN_SCORE = 2;
	
	public final ArrayList<School> 	schools;
	public final ArrayList<Game> 	games;
	
	public Tournament(
			@JsonProperty("schools") 	ArrayList<School> 	_schools,
			@JsonProperty("games") 		ArrayList<Game> 	_games
			)
	{
		schools = _schools;
		games	= _games;
	}
	
	public ArrayList<School> getRanking(final GameTypeEnum gameType)
	{
		ArrayList<School> ranking = new ArrayList<School>();
		
		Collections.sort(ranking, new Comparator<School>() {
	        @Override
	        public int compare(School school1, School school2)
	        {
	            return getTotalScore(school2, gameType) - getTotalScore(school1, gameType);
	        }
	    });
		
		return ranking;
	}
	
	public static int numberSchool(Map<School, Map<School, Integer>> schoolList, School schoolCompare, School schoolWithAgainst)
	{
		return schoolList.get(schoolCompare).get(schoolWithAgainst);
	}
	
	public static boolean hasPlayed(Map<School, Map<School, Integer>> schoolList, School schoolCompare, ArrayList<School> schoolWithAgainstList)
	{
		for(School schoolWithAgainst : schoolWithAgainstList)
		{
			if( schoolList.get(schoolCompare).get(schoolWithAgainst) > 0 )
			{
				return true;
			}
		}
		return false;
	}
	
	// Manage the schoolWith and schoolAgainst array.
	public static Game createGame(Map<School, Map<School, Integer>> schoolWith, Map<School, Map<School, Integer>> schoolAgainst, ArrayList<School> blueTeam, ArrayList<School> yellowTeam)
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
		
		return new Game(null, GameTypeEnum.PRELIMINARY, blueTeam, yellowTeam, null, null);
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
	
	public static ArrayList<Game> createPreliminaryRoundSchedual(ArrayList<School> schools, int gamePerSchool, int blockNumbers, int schoolsPerTeam, long seed)
	{
		Validate.notNull(schools);
		Validate.noNullElements(schools);
		Validate.isTrue(gamePerSchool > 0);
		Validate.isTrue(schoolsPerTeam > 0);
				
		Map<School, Map<School, Integer>> schoolWith = new HashMap<School, Map<School, Integer>>();
		Map<School, Map<School, Integer>> schoolAgainst = new HashMap<School, Map<School, Integer>>();
		
		initSchoolWithAgainst(schoolWith, schools);
		initSchoolWithAgainst(schoolAgainst, schools);
		
		ArrayList<School> blueTeam1 = new ArrayList<School>();
		ArrayList<School> yellowTeam1 = new ArrayList<School>();
		
		for(int i = 0; i < schoolsPerTeam; i++)
		{
			blueTeam1.add(schools.get(i));
			yellowTeam1.add(schools.get(i + schoolsPerTeam));
		}
		
		Game previousGame = createGame(schoolWith, schoolAgainst, blueTeam1, yellowTeam1);
		
		ArrayList<Game> games = new ArrayList<Game>();
		games.add(previousGame);
		
		int numberOfGames = (int)Math.ceil(schools.size() * gamePerSchool / (schoolsPerTeam * 2));
		
		// 0 - 6
		
		for(int i = 1; i < numberOfGames; i++)
		{
			int gamesPerBlock = numberOfGames / blockNumbers;
			int maxGamePlayed = (i / gamesPerBlock + 1) * 2;
			ArrayList<School> blueTeam = new ArrayList<School>();
			ArrayList<School> yellowTeam = new ArrayList<School>();
			
			// Let's fill the blue team.
			for(School school : schools)
			{
				if( getGamesPlayed(games, school, GameTypeEnum.PRELIMINARY).size() < maxGamePlayed &&
					!previousGame.getSchools().contains(school) && 
					!hasPlayed(schoolWith, school, blueTeam) && 
					!hasPlayed(schoolAgainst, school, blueTeam) &&
					blueTeam.size() < schoolsPerTeam)
				{
					blueTeam.add(school);
				}
			}
			
			// Now let's fill the yellow team
			for(School school : schools)
			{
				if( getGamesPlayed(games, school, GameTypeEnum.PRELIMINARY).size() < maxGamePlayed &&
					!blueTeam.contains(school) &&
					!previousGame.getSchools().contains(school) && 
					!hasPlayed(schoolWith, school, blueTeam) && 
					!hasPlayed(schoolAgainst, school, blueTeam) &&
					!hasPlayed(schoolWith, school, yellowTeam) && 
					!hasPlayed(schoolAgainst, school, yellowTeam) &&
					yellowTeam.size() < schoolsPerTeam)
				{
					yellowTeam.add(school);
				}
			}
			
			previousGame = createGame(schoolWith, schoolAgainst, blueTeam, yellowTeam);
			games.add(previousGame);
		}
		
		return games;
	}
	
	public int getTotalScore(final School school, GameTypeEnum gameType)
	{
		ArrayList<Game> gamesForType = new ArrayList<Game>();
		
		for(Game game : getGamesPlayed(games, school, gameType))
		{
			if( game.gameType == gameType )
			{
				gamesForType.add(game);
			}
		}

		Collections.sort(gamesForType, new Comparator<Game>() 
		{
	        @Override
	        public int compare(Game game1, Game game2)
	        {
	            return game2.getScore(school) - game1.getScore(school);
	        }
	    });
		
		int points = 0;
		for(int i = 0; i < gamesForType.size() - PRELIMINARY_GAMES_SKIPPED_IN_SCORE; i++)
		{
			points += gamesForType.get(i).getScore(school);
		}
		
		// Calculate globals penalties
		for(Game game : gamesForType)
		{
			for(SchoolPenalty penalty : game.penalties)
			{
				// Misconduct penalty is calculated globally
				if(penalty.school.equals(school) && penalty.isMisconductPenalty)
				{
					points -= penalty.pointsDeduction;
				}
			}
		}
		
		return points;
	}
	
	public static ArrayList<Game> getGamesPlayed(ArrayList<Game> games, School school, GameTypeEnum gameType)
	{
		ArrayList<Game> gamesPlayed = new ArrayList<Game>();
		
		for(Game game : games)
		{
			if( game.gameType == gameType && (game.blueTeam.contains(school) || game.yellowTeam.contains(school)) )
			{
				gamesPlayed.add(game);
			}
		}
		return gamesPlayed;
	}
}
