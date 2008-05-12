package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.regex.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ShowArtist extends HttpServlet {

    private final Log log = LogFactory.getLog(this.getClass());

    private MushArtistFactory maf;

    public void init() throws ServletException {
	NeoService neo = (NeoService)getServletContext().getAttribute("neoService");
	IndexService index = (IndexService)getServletContext().getAttribute("indexService");

	Transaction tx = Transaction.begin();
	try {
	    maf = new MushArtistFactory(neo, index);
	    tx.success();
	} finally {
	    tx.finish();
	}
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws IOException, ServletException {

	UUID id;
	try {
	    id = UUID.fromString(request.getPathInfo().substring(1));
	} catch (IllegalArgumentException e) {
	    response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    return;
	}

	Transaction tx = Transaction.begin();
	MushArtist artist;
	try {
	    artist = maf.getArtistById(id);
	    if (artist == null) {
		// replicate data from MusicBrainz WebService
		try {
		    Query q = MushQuery.getInstance();
		    Includes inc = new Includes();
		    inc.include("url-rels");
		    inc.include("sa-Album");
		    Artist mbArtist = q.getArtistById(id, inc);
		    artist = maf.copyArtist(mbArtist);
		} catch (WebServiceException e) {
		    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, e.getMessage());
		    return;
		}
	    }
	    tx.success();
	} finally {
	    tx.finish();
	}

	request.setAttribute("artist", artist);
	this.getServletContext().getRequestDispatcher("/showArtist.jsp")
	    .forward(request, response);
    }
}
