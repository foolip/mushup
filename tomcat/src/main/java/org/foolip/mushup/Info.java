package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

import java.util.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class Info extends HttpServlet {

    private NeoService neo;
    private IndexService indexService;

    public void init() throws ServletException {
	this.neo = (NeoService)getServletContext().getAttribute("neoService");
	this.indexService = (IndexService)getServletContext().getAttribute("indexService");
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
		    // ignore invalid ids
		}
	    }
	}

	LinkedList<MushArtist> artists = new LinkedList<MushArtist>();

	Transaction tx = Transaction.begin();
	try {
	    MushArtistFactory maf = new MushArtistFactory(neo, indexService);
	    for (UUID id : artistIds) {
		MushArtist artist = maf.getArtistById(id);
		if (artist == null) {
		    // replicate data from MusicBrainz WebService
		    try {
			Query q = new Query();
			Includes inc = new Includes();
			inc.include("url-rels");
			Artist mbArtist = q.getArtistById(id, inc);
			artist = maf.copyArtist(mbArtist);
		    } catch (WebServiceException e) {
			throw new ServletException(e);
		    }
		}
		artists.add(artist);
	    }
	    tx.success();
	} finally {
	    tx.finish();
	}

	request.setAttribute("artists", artists);
	this.getServletContext().getRequestDispatcher("/json/info.jsp")
	    .forward(request, response);
    }
}
