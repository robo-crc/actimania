package com.backend.views.yearly;

import java.util.Locale;

import com.backend.controllers.GameController;
import com.backend.models.Game;
import com.backend.models.GameEvent.yearly.ScoreboardUpdateEvent;
import com.backend.models.enums.GameEventEnum;
import com.backend.models.enums.TeamEnum;
import com.backend.models.yearly.GameStateYearly;
import com.framework.helpers.LocalizedString;
import com.google.common.collect.ImmutableMap;

public class GameYearlyView 
{
	// Called by the view
	public static String getScripts()
	{
		StringBuilder strBuilder = new StringBuilder();		
		
		strBuilder.append("$(document).ready(function(){");
		
		strBuilder.append("$( '.spinner' ).spinner();");
		strBuilder.append("$( '.spinner' ).numeric();");
		strBuilder.append("});");

		return strBuilder.toString();
	}
	
	// Called by the view
	public static String getHtmlForGame(Game game, Locale currentLocale)
	{
		LocalizedString strScoreboardUpdate = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Update score board", 
				Locale.FRENCH, 	"Mettre à jour le tableau de pointage"
				), currentLocale);

		LocalizedString strAdd = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Add", 
				Locale.FRENCH, 	"Ajouter"
				), currentLocale);

		LocalizedString strAddBoth = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Add Blue and Yellow", 
				Locale.FRENCH, 	"Ajouter bleu et jaune"
				), currentLocale);

		LocalizedString strAddBlue = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Add Blue", 
				Locale.FRENCH, 	"Ajouter bleu"
				), currentLocale);

		LocalizedString strAddYellow = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Add Yellow", 
				Locale.FRENCH, 	"Ajouter jaune"
				), currentLocale);

		LocalizedString strMultiplier = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Multiplier #GP", 
				Locale.FRENCH, 	"Multiplicateur #PJ"
				), currentLocale);

		LocalizedString strCylinder = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Cylinder #GP", 
				Locale.FRENCH, 	"Cylindre #PJ"
				), currentLocale);

		LocalizedString strPrism = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Prism #GP", 
				Locale.FRENCH, 	"Prisme #PJ"
				), currentLocale);
		
		LocalizedString strThreeLevels = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "Three Level (0 to 3)", 
				Locale.FRENCH, 	"Trois niveaux (0 à 3)"
				), currentLocale);

		LocalizedString strvShape = new LocalizedString(ImmutableMap.of( 	
				Locale.ENGLISH, "V Shape #GP", 
				Locale.FRENCH, 	"Forme en V #PJ"
				), currentLocale);
		
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<form method=\"post\" class=\"playingFieldForm\">");
		strBuilder.append("	<input type=\"hidden\" name=\"gameEvent\" value=\"" + GameEventEnum.SCOREBOARD_UPDATED.toString() + "\" />");
		strBuilder.append("	<input type=\"hidden\" name=\"id\" value=\"" + game._id + "\" />");
		strBuilder.append("	<h2>" + strScoreboardUpdate + "</h2>");
		
        ScoreboardUpdateEvent scoreboard = ((GameStateYearly)(game.getGameStates().get(game.getGameStates().size() - 1))).currentScoreboard;
		
		strBuilder.append("<table>");
		strBuilder.append("	<tr>");
		strBuilder.append("		<th></th>");
		strBuilder.append("		<th>" + TeamEnum.BLUE + "</th>");
		strBuilder.append("		<th>" + TeamEnum.YELLOW + "</th>");
		strBuilder.append("	</tr>");
		strBuilder.append("	<tr><td>" + strCylinder + "</td>");
		strBuilder.append(" <td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"cylinderBlue\" 		   value=\"" + scoreboard.cylinderBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"cylinderYellow\" 		   value=\"" + scoreboard.cylinderYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strPrism + "</td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"prismBlue\" 			   value=\"" + scoreboard.prismBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"prismYellow\" 		   value=\"" + scoreboard.prismYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strvShape + "</td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"vShapeBlue\"             value=\"" + scoreboard.vShapeBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"vShapeYellow\"           value=\"" + scoreboard.vShapeYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strThreeLevels + "</td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" max=\"3\" class=\"spinner\" name=\"threeLevelBlue\"         value=\"" + scoreboard.threeLevelBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" max=\"3\" class=\"spinner\" name=\"threeLevelYellow\"       value=\"" + scoreboard.threeLevelYellow + "\"></input></td>");
		strBuilder.append("	</tr><tr><td>" + strMultiplier + "</td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"gameMultiplierBlue\"     value=\"" + scoreboard.gameMultiplierBlue + "\"></input></td>");
		strBuilder.append("	<td><input type=\"Text\" min=\"0\" class=\"spinner\" name=\"gameMultiplierYellow\"   value=\"" + scoreboard.gameMultiplierYellow + "\"></input></td>");
		strBuilder.append("	</tr>");
		strBuilder.append("	</tr><tr><td>" + strAdd + "</td>");
		strBuilder.append("	<td><input name=\"blue\" type=\"submit\" value=\"" + strAddBlue + "\" /></td>");
		strBuilder.append("	<td><input name=\"yellow\" type=\"submit\" value=\"" + strAddYellow + "\" /></td>");
		strBuilder.append("	</tr>");
		strBuilder.append("</table>");

		strBuilder.append(GameController.outputAddAfterForView(game, currentLocale));
		strBuilder.append("	<br/>");
		strBuilder.append("	<input name=\"both\" type=\"submit\" value=\"" + strAddBoth + "\" />");
		strBuilder.append("</div>");
		strBuilder.append("</form>");

		return strBuilder.toString();
	}
}
