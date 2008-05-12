package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;

import java.util.*;
import java.util.regex.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Search extends HttpServlet {

    private Pattern pattern;

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
	Iterable<ArtistResult> result;
	try {
	    Query q = MushQuery.getInstance();
	    ArtistFilter af = new ArtistFilter();
	    af.setName(query);
	    af.setLimit(12);
	    result = q.getArtists(af);
	} catch (WebServiceException e) {
	    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, e.getMessage());
	    return;
	}

	if (format.equals("json")) {
	    request.setAttribute("result", result);
	    this.getServletContext().getRequestDispatcher("/json/artistResults.jsp")
		.forward(request, response);
	}
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws IOException, ServletException {

	String path = request.getPathInfo();
	if (path != null) {
	    // figure out if unified or artist/release/track search is wanted
	    Matcher m = pattern.matcher(path);
	    if (m.matches()) {
		String format = request.getParameter("format");
		if (format == null || !format.equals("json")) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "format must be json");
		    return;
		}

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
