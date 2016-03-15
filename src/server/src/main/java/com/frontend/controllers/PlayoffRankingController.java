package com.frontend.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.models.PlayoffRound;
import com.backend.models.SchoolInteger;
import com.backend.models.Tournament;
import com.backend.models.enums.GameTypeEnum;
import com.framework.helpers.Helpers;
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
			boolean isAdmin = essentials.subject.isAuthenticated();
			
			ArrayList<ArrayList<SchoolInteger>> repechageRankedByGroup = new ArrayList<ArrayList<SchoolInteger>>();
			PlayoffRound repechageRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_REPECHAGE);

			ArrayList<ArrayList<SchoolInteger>> quarterRankedByGroup = new ArrayList<ArrayList<SchoolInteger>>();
			PlayoffRound quarterRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_QUARTER);

			ArrayList<ArrayList<SchoolInteger>> semiRankedByGroup = new ArrayList<ArrayList<SchoolInteger>>();
			PlayoffRound semiRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_DEMI);

			ArrayList<ArrayList<SchoolInteger>> finalRankedByGroup = new ArrayList<ArrayList<SchoolInteger>>();
			PlayoffRound finalRound = PlayoffRound.get(essentials.database, GameTypeEnum.PLAYOFF_FINAL);

			if(isAdmin)
			{
				if(repechageRound != null)
				{
					repechageRankedByGroup.addAll(repechageRound.getSchoolsRankedByGroup(tournament));
				}
				
				if(quarterRound != null)
				{
					quarterRankedByGroup.addAll(quarterRound.getSchoolsRankedByGroup(tournament));
				}
				
				if(semiRound != null)
				{
					semiRankedByGroup.addAll(semiRound.getSchoolsRankedByGroup(tournament));
				}
				
				if(finalRound != null)
				{
					finalRankedByGroup.addAll(finalRound.getSchoolsRankedByGroup(tournament));
				}
			}
			else
			{
				// For everyone, we only show the results up to previous round.
				if(repechageRound != null && quarterRound != null)
				{
					repechageRankedByGroup.addAll(repechageRound.getSchoolsRankedByGroup(tournament));
				}
				
				if(quarterRound != null && semiRound != null)
				{
					quarterRankedByGroup.addAll(quarterRound.getSchoolsRankedByGroup(tournament));
				}
				
				if(semiRound != null && finalRound != null)
				{
					semiRankedByGroup.addAll(semiRound.getSchoolsRankedByGroup(tournament));
				}
				
				if(finalRound != null)
				{
					finalRankedByGroup.addAll(finalRound.getSchoolsRankedByGroup(tournament));
				}
			}
			boolean showHeader = true;
			if(request.getParameter("showHeader") != null)
			{
				showHeader = Helpers.getParameter("showHeader", Boolean.class, essentials);
			}
			
			essentials.request.setAttribute("tournament", tournament);
			essentials.request.setAttribute("repechageRankedByGroup", repechageRankedByGroup);
			essentials.request.setAttribute("quarterRankedByGroup", quarterRankedByGroup);
			essentials.request.setAttribute("semiRankedByGroup", semiRankedByGroup);
			essentials.request.setAttribute("finalRankedByGroup", finalRankedByGroup);
			essentials.request.setAttribute("showHeader", showHeader);

			essentials.request.getRequestDispatcher("/WEB-INF/frontend/playoffRanking.jsp").forward(essentials.request, essentials.response);
		}
	}
}
