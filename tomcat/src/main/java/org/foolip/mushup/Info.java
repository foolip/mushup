package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Info extends HttpServlet {

    private final Log log = LogFactory.getLog(this.getClass());

    private MushArtistFactory maf;

    private final static long maxTime = 2000;

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

	String format = request.getParameter("format");
	if (format == null || !format.equals("json")) {
	    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "format must be JSON");
	    return;
	}

	LinkedList<UUID> artistIds = new LinkedList<UUID>();
	String[] artistParams = request.getParameterValues("artist");
	if (artistParams != null) {
	    for (int i=0; i<artistParams.length; i++) {
		try {
		    artistIds.add(UUID.fromString(artistParams[i]));
		} catch (IllegalArgumentException e) {
		    log.warn("Ignoring invalid id " + artistParams[i]);
		}
	    }
	}

	long startTime = new Date().getTime();

	// add returnable artists to this list
	LinkedList<MushArtist> artists = new LinkedList<MushArtist>();

	for (UUID id : artistIds) {
	    Transaction tx = Transaction.begin();
	    try {
		MushArtist artist = maf.getArtistById(id);
		if (artist == null) {
		    // replicate data from MusicBrainz WebService
		    try {
			Query q = MushQuery.getInstance();
			Includes inc = new Includes();
			inc.include("url-rels");
			Artist mbArtist = q.getArtistById(id, inc);
			artist = maf.copyArtist(mbArtist);
		    } catch (WebServiceException e) {
			log.error(e);
			tx.failure();
			continue;
		    }
		}

		try {
		    artist.updateWikipediaBlurb();
		} catch (WikipediaException e) {
		    log.error(e);
		}

		artists.add(artist);
		tx.success();
	    } finally {
		tx.finish();
	    }

	    if (new Date().getTime() - startTime >= maxTime) {
		// return to client and wait for new request
		break;
	    }
	}

	Transaction tx = Transaction.begin();
	try {
	    request.setAttribute("artists", artists);
	    this.getServletContext().getRequestDispatcher("/json/info.jsp")
		.forward(request, response);
	} finally {
	    tx.finish();
	}
    }
}
