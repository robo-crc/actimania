package com.main;

import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.backend.models.Game;
import com.backend.models.GameState;
import com.backend.models.ISchoolScore;
import com.backend.models.Playoff;
import com.backend.models.PlayoffRound;
import com.backend.models.School;
import com.backend.models.SchoolDuration;
import com.backend.models.SchoolInteger;
import com.backend.models.Skill;
import com.backend.models.SkillsCompetition;
import com.backend.models.Tournament;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.GameEvent;
import com.backend.models.GameEvent.ScoreboardUpdateEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.enums.GameTypeEnum;
import com.backend.models.enums.yearly.TriangleStateEnum;
import com.backend.models.yearly.GameStateYearly;
import com.backend.models.yearly.Hole;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;
import com.framework.models.Essentials;

public class FakeTournament 
{
	public static void resetMatches(Essentials essentials)
	{
		Tournament tournament = Tournament.getTournament(essentials);
		for(int i = 0; i < tournament.games.size(); i++)
		{
			Game currentGame = tournament.games.get(i).getInitialState();
			essentials.database.save(currentGame);
		}
	}
	
	public static Hole[] fillTriangle(Random random)
	{
		Hole[] triangle = GameStateYearly.InitializeTriangle();
		for(Hole hole : triangle)
		{
			for(int i = 0; i < hole.triangleStates.length; i++)
			{
				hole.triangleStates[i] = TriangleStateEnum.values()[random.nextInt(3)];
				if(hole.triangleStates[i] == TriangleStateEnum.EMPTY)
				{
					break;
				}
			}
		}
		return triangle;
	}
	
	public static void fillFakeGameEvents(Game currentGame, Random random)
	{
		currentGame.addGameEvent(new StartGameEvent(DateTime.now()));
		
		int nbEvents = random.nextInt(30) + 10;
		
		for(int eventNo = 0; eventNo < nbEvents; eventNo++)
		{
			Hole[] triangleLeft = fillTriangle(random);
			Hole[] triangleRight = fillTriangle(random);
			
			currentGame.addGameEvent(new ScoreboardUpdateEvent(triangleLeft, triangleRight, DateTime.now()));
		}
		
		currentGame.addGameEvent(new EndGameEvent(DateTime.now()));
	}
	
	public static void main(String[] args) 
	{
		Random random = new Random(0);
		try(Essentials essentials = new Essentials(new Database(DatabaseType.PRODUCTION), null, null, null, null))
		{
			{
				Tournament tournament = Tournament.getTournament(essentials);
				
				SkillsCompetition skillsCompetition = TournamentYearlySetup.setupSkillCompetition(SkillsCompetition.get(essentials.database)._id, tournament.schools, true);
				essentials.database.save(skillsCompetition);
				
				ArrayList<Game> games = tournament.getHeatGames(GameTypeEnum.PLAYOFF_REPECHAGE);
				games.addAll(tournament.getHeatGames(GameTypeEnum.PLAYOFF_QUARTER));
				games.addAll(tournament.getHeatGames(GameTypeEnum.PLAYOFF_DEMI));
				games.addAll(tournament.getHeatGames(GameTypeEnum.PLAYOFF_FINAL));
				essentials.database.dropCollection(PlayoffRound.class);
				for(Game game : games)
				{
					essentials.database.remove(Game.class, game._id);
				}
				
				tournament.games.removeAll(games);
				
				for(int i = 0; i < tournament.games.size(); i++)
				{
					Game currentGame = tournament.games.get(i).getInitialState();
					
					fillFakeGameEvents(currentGame, random);
					
					essentials.database.save(currentGame);
				}
			}
			
			// Make sure to get the tournament again to reset the states. 
			// There's caching in the tournament which would be problematic if we use the same tournament as previously.
			Tournament tournament = Tournament.getTournament(essentials);
			ArrayList<School> excludedSchools = new ArrayList<School>();
			for(School school : tournament.schools)
			{
				if(school.name.equals("Bialik High School"))
				{
					excludedSchools.add(school);
				}
			}
			Playoff playoff = new Playoff(null, excludedSchools);
			
			PlayoffRound repechageRound = processRound(essentials.database, playoff, tournament, null, random, GameTypeEnum.PLAYOFF_REPECHAGE);
			essentials.database.save(repechageRound);
			PlayoffRound quarterRound = processRound(essentials.database, playoff, tournament, repechageRound, random, GameTypeEnum.PLAYOFF_QUARTER);
			essentials.database.save(quarterRound);
			PlayoffRound demiRound = processRound(essentials.database, playoff, tournament, quarterRound, random, GameTypeEnum.PLAYOFF_DEMI);
			essentials.database.save(demiRound);
			PlayoffRound finalRound = processRound(essentials.database, playoff, tournament, demiRound, random, GameTypeEnum.PLAYOFF_FINAL);
			essentials.database.save(finalRound);
		}
	}

	public static PlayoffRound processRound(Database database, Playoff playoff, Tournament tournament, PlayoffRound previousRound, Random random, GameTypeEnum gameType)
	{
		PlayoffRound round = playoff.generatePlayoffRound(database, tournament, previousRound, gameType);
		
		ArrayList<Game> playoffGames = round.getGames(new DateTime(), tournament.games.size() + 1);
		tournament.games.addAll(playoffGames);
		for(Game game : playoffGames)
		{
			FakeTournament.fillFakeGameEvents(game, random);
			if(database != null)
			{
				database.save(game);
			}
		}
		
		return round;
	}
}
