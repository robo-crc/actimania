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
	
	public static final int NUMBER_OF_JUDGES = 31;
	public static final int EACH_SCHOOL_JUDGED = 6;
	
	public final ArrayList<School> 	schools;
	public final ArrayList<Game> 	games;
	
	// Optimization to not recalculate multiple times a school score.
	private final TreeMap<School, Integer> preliminarySchoolScore = new TreeMap<School, Integer>();
	
	public Tournament(
			@JsonProperty("schools") 	ArrayList<School> 	_schools,
			@JsonProperty("games") 		ArrayList<Game> 	_games
			)
	{
		schools = _schools;
		games	= _games;
	}
	
	public ArrayList<Game> getHeatGames(final GameTypeEnum gameType)
	{
		ArrayList<Game> retGames = new ArrayList<Game>();
		for(Game game : games)
		{
			if(game.gameType == gameType)
			{
				retGames.add(game);
			}
		}
		
		return retGames;
	}
	
	public ArrayList<School> getHeatRanking(final GameTypeEnum gameType)
	{
		ArrayList<School> ranking = new ArrayList<School>(schools);
		ArrayList<School> rankingCopy = new ArrayList<School>(schools);

		// Optimization : Let's just calculate the score once instead of each time we sort.
		final TreeMap<School, Integer> score = new TreeMap<School, Integer>();
		for(School school : rankingCopy)
		{
			if(gameType != GameTypeEnum.PRELIMINARY && getGamesPlayed(games, school, gameType).size() == 0 )
			{
				ranking.remove(school);
			}
			else
			{
				score.put(school, getTotalScore(school, gameType));
			}
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
	
	public double getPreliminaryHeatScore(School school)
	{
		ArrayList<School> ranking = getHeatRanking(GameTypeEnum.PRELIMINARY);
		double bestScore = getTotalScore(ranking.get(0), GameTypeEnum.PRELIMINARY);
		double currentScore = getTotalScore(school, GameTypeEnum.PRELIMINARY);
		
		if(bestScore == 0)
			return 0;
		
		return (currentScore / bestScore) * 0.7;
	}
	
	public double getPreliminaryScore(School school, SkillsCompetition skillsCompetition)
	{
		return getPreliminaryHeatScore(school) + skillsCompetition.getSchoolScore(school);
	}
	
	public ArrayList<School> getPreliminaryRanking(SkillsCompetition skillsCompetition)
	{
		ArrayList<School> cumulativeSchools = new ArrayList<School>(schools);
		
		// Optimization : Let's just calculate the score once instead of each time we sort.
		final TreeMap<School, Double> score = new TreeMap<School, Double>();
		for( School school : schools )
		{
			score.put(school, getPreliminaryScore(school, skillsCompetition));
		}
		
		Collections.sort(cumulativeSchools, new Comparator<School>() {
	        @Override
	        public int compare(School school1, School school2)
	        {
	        	double diff = score.get(school2) - score.get(school1);
	            if( diff > 0 )
	            {
	            	return 1;
	            }
	            else if( diff < 0 )
	            {
	            	return -1;
	            }
	            else
	            {
	            	return 0;
	            }
	        }
	    });
		
		return cumulativeSchools;
	}
	
	public int getTotalScore(final School school, GameTypeEnum gameType)
	{
		if(gameType == GameTypeEnum.PRELIMINARY && preliminarySchoolScore.containsKey(school))
		{
			return preliminarySchoolScore.get(school);
		}
		
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
		
		int skipGames = 0;
		if(gameType == GameTypeEnum.PRELIMINARY)
		{
			skipGames = PRELIMINARY_GAMES_SKIPPED_IN_SCORE;
		}
		
		int points = 0;
		for(int i = 0; i < gamesForType.size() - skipGames; i++)
		{
			points += gamesForType.get(i).getScore(school);
		}
		
		if(gameType == GameTypeEnum.PRELIMINARY)
		{
			preliminarySchoolScore.put(school, points);
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
	
	public ArrayList<School> getPlayoffRanking()
	{
		ArrayList<School> schoolsFinal 		= getHeatRanking(GameTypeEnum.PLAYOFF_FINAL);
		ArrayList<School> schoolsDemi 		= getHeatRanking(GameTypeEnum.PLAYOFF_DEMI);
		ArrayList<School> schoolsQuarter 	= getHeatRanking(GameTypeEnum.PLAYOFF_QUARTER);
		ArrayList<School> schoolsRepechage 	= getHeatRanking(GameTypeEnum.PLAYOFF_REPECHAGE);
		
		ArrayList<School> finalDemi = mergeSchoolList(schoolsFinal, schoolsDemi);
		ArrayList<School> finalDemiSemi = mergeSchoolList(finalDemi, schoolsQuarter);
		return mergeSchoolList(finalDemiSemi, schoolsRepechage);
	}
	
	public static ArrayList<School> mergeSchoolList(ArrayList<School> keepAll, ArrayList<School> keepNot)
	{
		ArrayList<School> mergedList = new ArrayList<School>(keepNot);
		for(School keepSchool : keepAll)
		{
			mergedList.remove(keepSchool);
		}
		
		for(int i = keepAll.size() - 1; i >= 0; i--)
		{
			mergedList.add(0, keepAll.get(i));
		}
		
		return mergedList;
	}
}
