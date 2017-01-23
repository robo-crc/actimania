package com.frontend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.backend.models.Game;
import com.backend.models.Playoff;
import com.backend.models.School;
import com.backend.models.SchoolExtra;
import com.backend.models.SkillsCompetition;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

// Display the list of games played by the school
// Can also show the scores in other area

@WebServlet("/school")
public class SchoolController extends HttpServlet
{
	private static final long serialVersionUID = 1842100102197583562L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			SchoolExtra school = essentials.database.findOne(SchoolExtra.class, Helpers.getParameter("schoolId", ObjectId.class, essentials));
			
			Tournament tournament = Tournament.getTournament(essentials);
			SkillsCompetition skillsCompetition = SkillsCompetition.get(essentials.database);
			
			ArrayList<School> preliminaryRanking = tournament.getPreliminaryRanking(skillsCompetition);
			ArrayList<School> excludedSchools = Playoff.get(essentials.database).excludedSchools;
			
			ArrayList<School> preliminaryRankingForPlayoff = Playoff.getRemainingSchools(preliminaryRanking, excludedSchools);
			
			boolean isExcluded = excludedSchools.contains(school);
			
			int posToDisplay = 0;
			
			if(!isExcluded)
			{
				posToDisplay = preliminaryRankingForPlayoff.indexOf(school) + 1;
			}
			
			ArrayList<Game> sortedGames = tournament.getSortedGamesNoCache(school, GameTypeEnum.PRELIMINARY);
			ArrayList<Game> gamesSkippedInScore = new ArrayList<Game>();
			for(int i = Tournament.GAME_PER_SCHOOL - Tournament.PRELIMINARY_GAMES_SKIPPED_IN_SCORE; i < sortedGames.size(); i++)
			{
				gamesSkippedInScore.add(sortedGames.get(i));
			}

			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("school", school);
			essentials.request.setAttribute("rank", tournament.getRoundRanking(GameTypeEnum.PRELIMINARY).indexOf(school) + 1);
			essentials.request.setAttribute("score", tournament.getRoundScore(school, GameTypeEnum.PRELIMINARY));
			essentials.request.setAttribute("skillsCompetition", skillsCompetition);
			essentials.request.setAttribute("posToDisplay", posToDisplay);
			essentials.request.setAttribute("numberOfSchools", preliminaryRankingForPlayoff.size());
			essentials.request.setAttribute("isExcluded", isExcluded);
			essentials.request.setAttribute("gamesSkippedInScore", gamesSkippedInScore);
			essentials.request.getRequestDispatcher("/WEB-INF/frontend/school.jsp").forward(essentials.request, essentials.response);
		}
	}
}
