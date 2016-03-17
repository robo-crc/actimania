package com.main;

import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;

import com.backend.models.Game;
import com.backend.models.Playoff;
import com.backend.models.PlayoffRound;
import com.backend.models.School;
import com.backend.models.SkillsCompetition;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.helpers.Database;
import com.framework.helpers.Database.DatabaseType;
import com.framework.models.Essentials;
import com.main.yearly.FakeYearlyTournament;
import com.main.yearly.TournamentYearlySetup;

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
					
					FakeYearlyTournament.fillFakeGameEvents(currentGame, random);
					
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
			Playoff playoff = new Playoff(null, excludedSchools, null);
			
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
			FakeYearlyTournament.fillFakeGameEvents(game, random);
			if(database != null)
			{
				database.save(game);
			}
		}
		
		return round;
	}
}
