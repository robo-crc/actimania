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
		
		Game game = new Game(null, GameTypeEnum.PRELIMINARY, blueTeam, new ArrayList<School>(), getGameEvents(), false, new ArrayList<SchoolPenalty>(), new ArrayList<School>());
		Tournament tournament = new Tournament(new ArrayList<School>(), new ArrayList<Game>());
		tournament.schools.add(school);
		tournament.games.add(game);
		
		Validate.isTrue(game.getScore(school) == 100);
		
		SchoolPenalty penalty = new SchoolPenalty(school, 10);
		game.penalties.add(penalty);
		Validate.isTrue(game.getScore(school) == 90);
		
		// Not 3 games yet.
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 0);
		
		ArrayList<GameEvent> game2Events = getGameEvents();
		game2Events.add(GameEvent.pointModifierEvent(new PointModifier(TeamEnum.BLUE, -20, null)));
		Game game2 = new Game(null, GameTypeEnum.PRELIMINARY, blueTeam, new ArrayList<School>(), game2Events, false, new ArrayList<SchoolPenalty>(), new ArrayList<School>());
		Validate.isTrue(game2.getScore(school) == 80);
		tournament.games.add(game2);
		
		ArrayList<GameEvent> game3Events = new ArrayList<GameEvent>();
		game3Events.add(new GameEvent(GameEventEnum.START_GAME));
		game3Events.add(GameEvent.pointModifierEvent(new PointModifier(TeamEnum.YELLOW, 40, null)));
		ArrayList<School> yellowTeam = new ArrayList<School>();
		yellowTeam.add(school);
		
		Game game3 = new Game(null, GameTypeEnum.PRELIMINARY, new ArrayList<School>(), yellowTeam, game3Events, false, new ArrayList<SchoolPenalty>(), new ArrayList<School>());
		
		tournament.games.add(game3);
		Validate.isTrue(game3.getScore(school) == 40);
		Validate.isTrue(game3.getScore(new School(null, null)) == 0);
		
		// Best score is 90
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 90);
		
		// Bad kids. A misconduct penalty!
		game3.misconductPenalties.add(school);
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 0);
		
		ArrayList<GameEvent> game4Events = new ArrayList<GameEvent>();
		game4Events.add(new GameEvent(GameEventEnum.START_GAME));
		game4Events.add(GameEvent.pointModifierEvent(new PointModifier(TeamEnum.YELLOW, 120, null)));
		yellowTeam = new ArrayList<School>();
		yellowTeam.add(school);
		
		Game game4 = new Game(null, GameTypeEnum.PRELIMINARY, new ArrayList<School>(), yellowTeam, game4Events, false, new ArrayList<SchoolPenalty>(), new ArrayList<School>());
		tournament.games.add(game4);
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 120);
		
		ArrayList<GameEvent> game5Events = new ArrayList<GameEvent>();
		game5Events.add(new GameEvent(GameEventEnum.START_GAME));
		game5Events.add(GameEvent.pointModifierEvent(new PointModifier(TeamEnum.YELLOW, 30, null)));
		yellowTeam = new ArrayList<School>();
		yellowTeam.add(school);
		
		Game game5 = new Game(null, GameTypeEnum.PRELIMINARY, new ArrayList<School>(), yellowTeam, game5Events, false, new ArrayList<SchoolPenalty>(), new ArrayList<School>());
		tournament.games.add(game5);
		// 3 "best games" are 0 (misconduct penalty), 120, 90
		Validate.isTrue(tournament.getTotalScore(school, GameTypeEnum.PRELIMINARY) == 210);
	}
}

