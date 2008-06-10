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
	try {
	    MushArtist artist = maf.getOrCreateArtist(id);
	    if (!artist.isReplicated()) {
		try {
		    maf.replicate(artist);
		} catch (WebServiceException e) {
		    log.error(e);
		    tx.failure();
		    response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, e.getMessage());
		    return;
		}
	    }

	    request.setAttribute("artist", artist);
	    this.getServletContext().getRequestDispatcher("/showArtist.jsp")
		.forward(request, response);

	    tx.success();
	} finally {
	    tx.finish();
	}
    }
}
