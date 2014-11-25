package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.framework.models.Essentials;

public class Tournament
{
	@JsonIgnore
	public static final int PRELIMINARY_GAMES_SKIPPED_IN_SCORE = 2;
	
	public static final int GAME_PER_SCHOOL = 12;
	public static final int SCHOOLS_PER_TEAM = 3;
	public static final int BLOCK_NUMBERS = 4;
	
	public static final int NUMBER_OF_JUDGES = 20;
	public static final int EACH_SCHOOL_JUDGED = 4;
	
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
	
	public ArrayList<School> getRanking(ArrayList<School> schools, final GameTypeEnum gameType)
	{
		ArrayList<School> ranking = new ArrayList<School>(schools);
		
		// Optimization : Let's just calculate the score once instead of each time we sort.
		final TreeMap<School, Integer> score = new TreeMap<School, Integer>();
		for(School school : ranking)
		{
			score.put(school, getTotalScore(school, gameType));
		}

		Collections.sort(ranking, new Comparator<School>() {
	        @Override
	        public int compare(School school1, School school2)
	        {
	            return score.get(school2) - score.get(school1);
	        }
	    });
		
		return ranking;
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
		
		// Optimization : Let's just calculate the score once instead of each time we sort.
		final TreeMap<Game, Integer> score = new TreeMap<Game, Integer>();
		for(Game game : gamesForType)
		{
			score.put(game, game.getScore(school));
		}
		
		final TreeMap<Game, Boolean> hasMisconductPenalty = new TreeMap<Game, Boolean>();
		for(Game game : gamesForType)
		{
			hasMisconductPenalty.put(game, game.hasMisconductPenalty(school));
		}

		Collections.sort(gamesForType, new Comparator<Game>() 
		{
	        @Override
	        public int compare(Game game1, Game game2)
	        {
	        	if(hasMisconductPenalty.get(game1))
	        	{
	        		// We put the misconduct penalties at the top
	        		return -1;
	        	}
	        	else if(hasMisconductPenalty.get(game2))
	        	{
	        		// We put the misconduct penalties at the top
	        		return 1;
	        	}
	        	else
	        	{
	        		return score.get(game2) - score.get(game1);
	        	}
	        }
	    });
		
		int points = 0;
		for(int i = 0; i < gamesForType.size() - PRELIMINARY_GAMES_SKIPPED_IN_SCORE; i++)
		{
			points += gamesForType.get(i).getScore(school);
		}
		
		return points;
	}
	
	public static ArrayList<Game> getGamesPlayed(ArrayList<Game> games, School school, GameTypeEnum gameType)
	{
		ArrayList<Game> gamesPlayed = new ArrayList<Game>();
		
		for(Game game : games)
		{
			if( game.gameType.equals(gameType) && (game.blueTeam.contains(school) || game.yellowTeam.contains(school)) )
			{
				gamesPlayed.add(game);
			}
		}
		return gamesPlayed;
	}
	
	public static Tournament getTournament(Essentials essentials)
	{
		return new Tournament(School.getSchools(essentials), Game.getGames(essentials));
	}
	
	public static Game getNextGame(ArrayList<Game> games)
	{
		for(Game game : games)
		{
			if(game.getGameEvents().size() == 0)
			{
				return game;
			}
		}
		return null;
	}
}
