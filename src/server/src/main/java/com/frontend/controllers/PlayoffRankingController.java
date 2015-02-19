package com.frontend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.backend.models.PlayoffRound;
import com.backend.models.SchoolInteger;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.models.Essentials;

@WebServlet("/playoff")
public class PlayoffRankingController extends HttpServlet
{
	private static final long serialVersionUID = 8336235714386239006L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			Tournament tournament = Tournament.getTournament(essentials);
			
			PlayoffRound repechageRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_REPECHAGE);
			ArrayList<ArrayList<SchoolInteger>> schoolsRankedByGroup = new ArrayList<ArrayList<SchoolInteger>>();
			schoolsRankedByGroup.addAll(repechageRound.getSchoolsRankedByGroup(tournament));

			PlayoffRound semiRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_QUARTER);
			schoolsRankedByGroup.addAll(semiRound.getSchoolsRankedByGroup(tournament));

			PlayoffRound demiRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_DEMI);
			schoolsRankedByGroup.addAll(demiRound.getSchoolsRankedByGroup(tournament));

			PlayoffRound finalRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_FINAL);
			schoolsRankedByGroup.addAll(finalRound.getSchoolsRankedByGroup(tournament));

			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("schoolsRankedByGroup", schoolsRankedByGroup);
			
			essentials.request.getRequestDispatcher("/WEB-INF/frontend/playoffRanking.jsp").forward(essentials.request, essentials.response);
		}
	}
}
