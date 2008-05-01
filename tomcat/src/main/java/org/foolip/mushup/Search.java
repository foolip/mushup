package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;

import java.util.*;
import java.util.regex.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Search extends HttpServlet {

    Pattern pattern; 

    public void init() throws ServletException {
	this.pattern = Pattern.compile("/([^/]+)(?:/([^/]+))?");
    }

    public void unifiedSearch(HttpServletRequest request,
			      HttpServletResponse response,
			      String query, String format)
      throws IOException, ServletException {
	artistSearch(request, response, query, format);
    }

    public void artistSearch(HttpServletRequest request,
			     HttpServletResponse response,
			     String query, String format)
      throws IOException, ServletException {

	response.setContentType("text/html; charset=UTF-8");
	PrintWriter writer = response.getWriter();

	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>Mushup! MusicBrainz Search</title>");
	writer.println("</head>");
	writer.println("<body>");

	writer.println("<h1>" + query + "</h1>");
	writer.println("<p><i>" + format + "</i>");
	try {
	    Query q = new Query();
	    ArtistFilter af = new ArtistFilter();
	    af.setName(query);
	    for (ArtistResult ar : q.getArtists(af)) {
		Artist a = ar.getArtist();
		writer.println("<p>");
		writer.println("ID: " + a.getId());
		writer.println("<br />");
		writer.println("Type: " + a.getType());
		writer.println("<br />");
		writer.println("Name: " + a.getName());
		writer.println("<br />");
		writer.println("Sort Name: " + a.getSortName());
		writer.println("</p>");
	    }
	} catch (WebServiceException e) {
	    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, e.getMessage());
	}

	writer.println("</body>");
	writer.println("</html>");
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws IOException, ServletException {

	String format = request.getParameter("format");
	String path = request.getPathInfo();
	if (path != null) {
	    // figure out if unified or artist/release/track search is wanted
	    Matcher m = pattern.matcher(path);
	    if (m.matches()) {
		if (m.group(2) == null) {
		    unifiedSearch(request, response, m.group(1), format);
		    return;
		} else if (m.group(1).equals("artist")) {
		    artistSearch(request, response, m.group(2), format);
		    return;
		}
	    }
	}
	// this search was not meant to be
	response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
