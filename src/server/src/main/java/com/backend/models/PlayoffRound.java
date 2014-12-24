package com.backend.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayoffRound 
{
	public final ArrayList<PlayoffGroup> playoffGroups;
	
	public PlayoffRound(@JsonProperty("playoffGroups") 	ArrayList<PlayoffGroup> _playoffGroups)
	{
		playoffGroups = _playoffGroups;
	}
	
	public ArrayList<Game> generateGames()
	{
		ArrayList<Game> games = new ArrayList<Game>();
		//Playoff.SCHOOLS_PER_TEAM
		
		return games;
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