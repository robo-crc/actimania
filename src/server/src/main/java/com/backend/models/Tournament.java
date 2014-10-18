package com.backend.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tournament
{
	@JsonIgnore
	public static final int PRELIMINARY_GAMES_SKIPPED_IN_SCORE = 2;
	
	public static final int GAME_PER_SCHOOL = 10;
	public static final int SCHOOLS_PER_TEAM = 3;
	public static final int BLOCK_NUMBERS = 4;
	
	public static final int NUMBER_OF_JUDGES = 20;
	public static final int EACH_SCHOOL_JUDGED = 4;
	public static final int SCHOOL_NUMBER = 33;
	
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
	        	if(game1.misconductPenalties.contains(school))
	        	{
	        		// We put the misconduct penalties at the top
	        		return -1;
	        	}
	        	else if(game2.misconductPenalties.contains(school))
	        	{
	        		// We put the misconduct penalties at the top
	        		return 1;
	        	}
	        	else
	        	{
	        		return game2.getScore(school) - game1.getScore(school);
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
			if( game.gameType == gameType && (game.blueTeam.contains(school) || game.yellowTeam.contains(school)) )
			{
				gamesPlayed.add(game);
			}
		}
		return gamesPlayed;
	}

}
