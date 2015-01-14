package com.main;

import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.backend.models.Game;
import com.backend.models.Playoff;
import com.backend.models.PlayoffRound;
import com.backend.models.School;
import com.backend.models.SchoolDuration;
import com.backend.models.SchoolInteger;
import com.backend.models.SkillsCompetition;
import com.backend.models.Tournament;
import com.backend.models.GameEvent.ActuatorStateChangedEvent;
import com.backend.models.GameEvent.EndGameEvent;
import com.backend.models.GameEvent.StartGameEvent;
import com.backend.models.GameEvent.TargetHitEvent;
import com.backend.models.enums.ActuatorStateEnum;
import com.backend.models.enums.GameTypeEnum;
import com.backend.models.enums.SideEnum;
import com.backend.models.enums.TargetEnum;
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
	
	public static void fillFakGameEvents(Game currentGame, Random random)
	{
		currentGame.addGameEvent(new StartGameEvent(DateTime.now()));
		
		int nbEvents = random.nextInt(30) + 20;
		
		for(int eventNo = 0; eventNo < nbEvents; eventNo++)
		{
			SideEnum side = SideEnum.values()[random.nextInt(SideEnum.values().length)];
			TargetEnum target = TargetEnum.values()[random.nextInt(TargetEnum.values().length)];
			
			boolean isTargetHit = random.nextBoolean();
			if(isTargetHit)
			{
				currentGame.addGameEvent(new TargetHitEvent(side, target, DateTime.now()));
			}
			else
			{
				ActuatorStateEnum actuator = ActuatorStateEnum.values()[random.nextInt(ActuatorStateEnum.values().length)];
				currentGame.addGameEvent(new ActuatorStateChangedEvent(side, target, actuator, DateTime.now()));
			}
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
				
				ArrayList<SchoolInteger> pickBallsArray = new ArrayList<SchoolInteger>();
				ArrayList<SchoolDuration> twoActuatorsArray = new ArrayList<SchoolDuration>();
				ArrayList<SchoolDuration> twoTargetsArray = new ArrayList<SchoolDuration>();
				
				SkillsCompetition skills = SkillsCompetition.get(essentials.database);
				for(School school : tournament.schools)
				{
					pickBallsArray.add(new SchoolInteger(school, random.nextInt(20)));
					twoActuatorsArray.add(new SchoolDuration(school, new Duration(random.nextInt(10*60*1000))));
					twoTargetsArray.add(new SchoolDuration(school, new Duration(random.nextInt(10*60*1000))));
				}
				SkillsCompetition skillsCompetition = new SkillsCompetition(skills._id, pickBallsArray, twoTargetsArray, twoActuatorsArray);
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
				
				for(int i = 0; i < tournament.games.size(); i++)
				{
					Game currentGame = tournament.games.get(i).getInitialState();
					
					fillFakGameEvents(currentGame, random);
					
					essentials.database.save(currentGame);
				}
			}
			
			// Make sure to get the tournament again to reset the states. 
			// There's caching in the tournament which would be problematic if we use the same tournament as previously.
			Tournament tournament = Tournament.getTournament(essentials);
			ArrayList<School> excludedSchools = new ArrayList<School>();
			
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
			FakeTournament.fillFakGameEvents(game, random);
			if(database != null)
			{
				database.save(game);
			}
		}
		
		return round;
	}
}
