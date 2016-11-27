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
import org.bson.types.ObjectId;
import org.joda.time.Duration;

import com.backend.models.Competition;
import com.backend.models.ISchoolScore;
import com.backend.models.LiveRefresh;
import com.backend.models.School;
import com.backend.models.SchoolDuration;
import com.backend.models.SchoolInteger;
import com.backend.models.Skill;
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
				SkillsCompetition skillsCompetition = SkillsCompetition.get(essentials.database);

				for(String parameter : Collections.list(request.getParameterNames()) )
				{
					for(Skill skill : skillsCompetition.skills)
					{
						if(parameter.startsWith(skill.shortName))
						{
							ObjectId id = new ObjectId(parameter.substring(skill.shortName.length() + 1));
							School school = essentials.database.findOne(School.class, id);
							
							skill.schoolsScore.remove(skill.getSchoolScore(school));
							
							ISchoolScore schoolScore = null;
							
							if(skill.schoolsScore.get(0) instanceof SchoolDuration)
							{
								Duration value = Helpers.stopwatchFormatter.parsePeriod(Helpers.getParameter(parameter, String.class, essentials)).toStandardDuration();
								schoolScore = new SchoolDuration(school, value);
							}
							else if(skill.schoolsScore.get(0) instanceof SchoolInteger)
							{
								Integer value = Integer.parseInt(Helpers.getParameter(parameter, String.class, essentials));
								schoolScore = new SchoolInteger(school, value);
							}
							
							skill.schoolsScore.add(schoolScore);
						}
					}
				}
				
				essentials.database.save(skillsCompetition);
			}
			else if(action.equals("toggleLiveRefresh"))
			{
				LiveRefresh liveRefresh = LiveRefresh.get(essentials);
				LiveRefresh toggledLiveRefresh = new LiveRefresh(liveRefresh._id, !liveRefresh.isLiveRefreshOn);
				essentials.database.save(toggledLiveRefresh);
			}
			else if(action.equals("overallCompetition"))
			{
				Competition competition = Competition.get(essentials.database);
				ArrayList<SchoolInteger> kioskArray 			= new ArrayList<SchoolInteger>();
				ArrayList<SchoolInteger> programmingArray 		= new ArrayList<SchoolInteger>();
				ArrayList<SchoolInteger> robotConstructionArray = new ArrayList<SchoolInteger>();
				ArrayList<SchoolInteger> robotDesignArray		= new ArrayList<SchoolInteger>();
				ArrayList<SchoolInteger> sportsmanshipArray 	= new ArrayList<SchoolInteger>();
				ArrayList<SchoolInteger> videoArray 			= new ArrayList<SchoolInteger>();
				ArrayList<SchoolInteger> websiteDesignArray 	= new ArrayList<SchoolInteger>();
				ArrayList<SchoolInteger> websiteJournalismArray = new ArrayList<SchoolInteger>();
				
				for(String parameter : Collections.list(request.getParameterNames()) )
				{
					final String kiosk 				= "kiosk_";
					final String programming 		= "programming_";
					final String robotConstruction 	= "robotConstruction_";
					final String robotDesign 		= "robotDesign_";
					final String sportsmanship 		= "sportsmanship_";
					final String video 				= "video_";
					final String websiteDesign 		= "websiteDesign_";
					final String websiteJournalism 	= "websiteJournalism_";
					
					process(essentials, parameter, kiosk, 				kioskArray);
					process(essentials, parameter, programming, 		programmingArray);
					process(essentials, parameter, robotConstruction, 	robotConstructionArray);
					process(essentials, parameter, robotDesign, 		robotDesignArray);
					process(essentials, parameter, sportsmanship, 		sportsmanshipArray);
					process(essentials, parameter, video, 				videoArray);
					process(essentials, parameter, websiteDesign, 		websiteDesignArray);
					process(essentials, parameter, websiteJournalism, 	websiteJournalismArray);
				}
				
				Competition competitionUpdate = new Competition(competition._id, kioskArray, programmingArray, robotConstructionArray, robotDesignArray, sportsmanshipArray, videoArray, websiteDesignArray, websiteJournalismArray);
				essentials.database.save(competitionUpdate);
			}
			
			showPage(essentials);
		}
	}
	
	private static void process(Essentials essentials, final String parameter, final String strConstant, ArrayList<SchoolInteger> schoolInteger)
	{
		if(parameter.startsWith(strConstant))
		{
			ObjectId id = new ObjectId(parameter.substring(strConstant.length()));
			Integer value = Helpers.getParameter(parameter, Integer.class, essentials);
			schoolInteger.add(new SchoolInteger(essentials.database.findOne(School.class, id), value));
		}
	}
	
	public static void showPage(Essentials essentials) throws ServletException, IOException
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
		
		essentials.request.setAttribute("competition", Competition.get(essentials.database));
		essentials.request.setAttribute("skillsCompetition", SkillsCompetition.get(essentials.database));
		essentials.request.setAttribute("isLivreRefreshOn", LiveRefresh.get(essentials).isLiveRefreshOn);
		essentials.request.setAttribute("schools", schools);
		essentials.request.setAttribute("errorList", essentials.errorList);
		essentials.request.getRequestDispatcher("/WEB-INF/admin/competition.jsp").forward(essentials.request, essentials.response);
	}
}
