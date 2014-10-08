package com.backend.models;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.commons.lang.Validate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.GameTypeEnum;
import com.backend.models.enums.TeamEnum;
import com.framework.helpers.LocalizedString;

public class ScoreTests 
{
	@BeforeClass
	public static void setUp()
    {
    }
	
	@AfterClass
	public static void tearDown()
	{
	}
	
	private ArrayList<GameEvent> getGameEvents()
	{
		ArrayList<GameEvent> gameEvents = new ArrayList<GameEvent>();
		gameEvents.add(new GameEvent(GameEventEnum.START_GAME));
		
		PointModifier pointModifier = new PointModifier(TeamEnum.BLUE, 100, new LocalizedString(Locale.ENGLISH, "fake game", "Game truquée"));
		gameEvents.add(GameEvent.pointModifierEvent(pointModifier));
		
		return gameEvents;
	}
	
	
	@Test
	public void testGetScore()
	{
		School school = new School(null, "21 Jump Street");
		
		ArrayList<School> blueTeam = new ArrayList<School>();
		blueTeam.add(school);
		
		Game game = new Game(null, GameTypeEnum.PRELIMINARY, blueTeam, new ArrayList<School>(), getGameEvents(), new ArrayList<SchoolPenalty>());
		Tournament tournament = new Tournament(new ArrayList<School>(), new ArrayList<Game>());
		tournament.schools.add(school);
		tournament.games.add(game);
		
		Validate.isTrue(game.getScore(school) == 100);
		
		SchoolPenalty penalty = new SchoolPenalty(school, 10, false);
		game.penalties.add(penalty);
		Validate.isTrue(game.getScore(school) == 90);
		
		// Not 3 games yet.
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 0);
		
		penalty = new SchoolPenalty(school, 30, true);
		game.penalties.add(penalty);
		Validate.isTrue(game.getScore(school) == 90);
		
		// Not 3 games yet. But misconduct points are always taken into account.
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == -30);
		
		ArrayList<GameEvent> game2Events = getGameEvents();
		game2Events.add(GameEvent.pointModifierEvent(new PointModifier(TeamEnum.BLUE, -20, null)));
		Game game2 = new Game(null, GameTypeEnum.PRELIMINARY, blueTeam, new ArrayList<School>(), game2Events, new ArrayList<SchoolPenalty>());
		Validate.isTrue(game2.getScore(school) == 80);
		tournament.games.add(game2);
		
		ArrayList<GameEvent> game3Events = new ArrayList<GameEvent>();
		game3Events.add(new GameEvent(GameEventEnum.START_GAME));
		game3Events.add(GameEvent.pointModifierEvent(new PointModifier(TeamEnum.YELLOW, 40, null)));
		ArrayList<School> yellowTeam = new ArrayList<School>();
		yellowTeam.add(school);
		
		Game game3 = new Game(null, GameTypeEnum.PRELIMINARY, new ArrayList<School>(), yellowTeam, game3Events, new ArrayList<SchoolPenalty>());
		
		
		tournament.games.add(game3);
		Validate.isTrue(game3.getScore(school) == 40);
		Validate.isTrue(game3.getScore(new School(null, null)) == 0);
		
		// Best score is 90, minus 30 points of misconduct penalty
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 60);
		
		// Really bad kids. Another misconduct penalty!
		penalty = new SchoolPenalty(school, 10, true);
		game3.penalties.add(penalty);

		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 50);
				
	}
}

