package com.backend.controllers;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.backend.models.Competition;
import com.backend.models.School;
import com.backend.models.SkillsCompetition;
import com.framework.helpers.Helpers;
import com.framework.models.Essentials;

@WebServlet("/admin/competition")
public class CompetitionController extends HttpServlet
{
	private static final long serialVersionUID = 2943193194399939430L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			showPage(essentials);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try(Essentials essentials = Essentials.createEssentials(request,  response))
		{
			String 	action 		= Helpers.getParameter("action", String.class, essentials);
			
			if(action.equals("skillsCompetition"))
			{
			}
			
			showPage(essentials);
		}
	}
	
	private void showPage(Essentials essentials) throws ServletException, IOException
	{
		ArrayList<School> schools = School.getSchools(essentials);

		Collections.sort(schools, new Comparator<School>() {
	        @Override
	        public int compare(School school1, School school2)
	        {
	        	// Do not take accents into account.
	        	Collator collator = Collator.getInstance(Locale.FRENCH);
	        	collator.setStrength(Collator.PRIMARY);
	        	return collator.compare(StringEscapeUtils.unescapeHtml(school1.name), StringEscapeUtils.unescapeHtml(school2.name));
	        }
	    });
		
		essentials.request.setAttribute("competition", Competition.get(essentials));
		essentials.request.setAttribute("skillsCompetition", SkillsCompetition.get(essentials.database));
		essentials.request.setAttribute("schools", schools);
		essentials.request.setAttribute("errorList", essentials.errorList);
		essentials.request.getRequestDispatcher("/WEB-INF/admin/competition.jsp").forward(essentials.request, essentials.response);
	}
}
