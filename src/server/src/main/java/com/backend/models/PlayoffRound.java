package com.backend.models;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.backend.models.GameEvent.GameEvent;
import com.backend.models.enums.GameTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayoffRound 
{
	public final ArrayList<PlayoffGroup> 	playoffGroups;
	public final GameTypeEnum				gameType;
	
	public PlayoffRound(@JsonProperty("playoffGroups") 	ArrayList<PlayoffGroup> _playoffGroups,
						@JsonProperty("gameType") 		GameTypeEnum _gameType
						)
	{
		playoffGroups 	= _playoffGroups;
		gameType 		= _gameType;
	}
	
	public static final Duration TIME_BETWEEN_GAMES = new Duration(5 * 60 * 1000);

	// I've hard coded this function ... it could be made more generic, but for Actimania it will be good enough.
	// Only works for games of 2v2
	public ArrayList<Game> getGames(DateTime startTime)
	{
		ArrayList<Game> games = new ArrayList<Game>();
		ArrayList<PlayoffGroup> orderedGroups = orderPlayoffGroups(playoffGroups);
		
		int currentGame = 0;
		for(int currentPass = 0; currentPass < 5; currentPass++)
		{
			for(PlayoffGroup playoffGroup : orderedGroups)
			{
				ArrayList<School> schools = playoffGroup.schools;
				if(playoffGroup.schools.size() == 4)
				{
					// 0 1 vs 2 3
					// 0 2 vs 1 3
					// 0 3 vs 1 2
					
					switch(currentPass)
					{
					case 0:
						games.add(createGame(startTime, currentGame, gameType, schools, 0, 1, /* vs */ 2, 3));
						break;
					case 2: // Not 1 because if we need to interwind 4 teams and 5 teams, it gives breathing time.
						games.add(createGame(startTime, currentGame, gameType, schools, 0, 2, /* vs */ 1, 3));
						break;
					case 4:
						games.add(createGame(startTime, currentGame, gameType, schools, 0, 3, /* vs */ 1, 2));
						break;
					}
				}
				else if(playoffGroup.schools.size() == 5)
				{
					// 0 3 vs 1 2
					// 0 4 vs 1 3
					// 0 2 vs 3 4
					// 1 4 vs 2 3
					// 0 1 vs 2 4

					switch(currentPass)
					{
					case 0:
						games.add(createGame(startTime, currentGame, gameType, schools, 0, 3, /* vs */ 1, 2));
						break;
					case 1:
						games.add(createGame(startTime, currentGame, gameType, schools, 0, 4, /* vs */ 1, 3));
						break;
					case 2:
						games.add(createGame(startTime, currentGame, gameType, schools, 0, 2, /* vs */ 3, 4));
						break;
					case 3:
						games.add(createGame(startTime, currentGame, gameType, schools, 1, 4, /* vs */ 2, 3));
						break;
					case 4:
						games.add(createGame(startTime, currentGame, gameType, schools, 0, 1, /* vs */ 2, 4));
						break;
					}
				}
				else
				{
					// Not a valid situation, if we hit that condition, we need to add it.
					Validate.isTrue(false);
				}
				currentGame++;
			}
		}
		
		return games;
	}

	public static ArrayList<PlayoffGroup> orderPlayoffGroups(ArrayList<PlayoffGroup> playoffGroups)
	{
		ArrayList<PlayoffGroup> retPlayoffGroups;
		boolean isEven = true;
		int firstSize = playoffGroups.get(0).schools.size();
		
		int nb4Schools = 0;

		for(PlayoffGroup playoffGroup : playoffGroups)
		{
			if(firstSize != playoffGroup.schools.size())
			{
				isEven = false;
			}
			
			if(playoffGroup.schools.size() == 4)
			{
				nb4Schools++;
			}
			else if(playoffGroup.schools.size() != 5)
			{
				// This function assume that there's either 4 or 5 schools in a group.
				Validate.isTrue(false);
			}
		}
		
		if(isEven)
		{
			retPlayoffGroups = new ArrayList<PlayoffGroup>(playoffGroups);
		}
		else
		{
			retPlayoffGroups = new ArrayList<PlayoffGroup>();
			
			int schoolsAdded = 0;
			
			for(int currentGroup = 0; schoolsAdded < nb4Schools / 2; currentGroup++)
			{
				if(playoffGroups.get(currentGroup).schools.size() == 4)
				{
					retPlayoffGroups.add(playoffGroups.get(currentGroup));
					schoolsAdded++;
				}
			}
			
			// In the middle add all groups with 5 games.
			for(PlayoffGroup playoffGroup : playoffGroups)
			{
				if(playoffGroup.schools.size() == 5)
				{
					retPlayoffGroups.add(playoffGroup);
				}
			}
			
			// Add all remaining groups.
			for(PlayoffGroup playoffGroup : playoffGroups)
			{
				if(!retPlayoffGroups.contains(playoffGroup))
				{
					retPlayoffGroups.add(playoffGroup);
				}
			}
		}
		
		return retPlayoffGroups;
	}
	
	public static Game createGame(DateTime startTime, int currentGame, GameTypeEnum gameType, ArrayList<School> schools, int school0, int school1, int school2, int school3)
	{
		ArrayList<School> blueTeam = new ArrayList<School>();
		blueTeam.add(schools.get(school0));
		blueTeam.add(schools.get(school1));
		
		ArrayList<School> yellowTeam = new ArrayList<School>();
		yellowTeam.add(schools.get(school2));
		yellowTeam.add(schools.get(school3));
		
		//return createGame(startTime, currentGame, blueTeam, yellowTeam);
		Duration delta = new Duration(currentGame * Game.getGameLength().getMillis() + currentGame * TIME_BETWEEN_GAMES.getMillis());
		
		return new Game(null, currentGame, startTime.plus(delta), gameType, blueTeam, yellowTeam, new ArrayList<GameEvent>(), false);

	}
	
	public String toString()
	{
		String string = "";
		for(PlayoffGroup playoffGroup : playoffGroups)
		{
			for(School school : playoffGroup.schools)
			{
				string += school.name + " ";
			}
			string += "\n";
		}
		return string;
	}
}