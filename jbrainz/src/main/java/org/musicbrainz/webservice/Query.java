package org.musicbrainz.webservice;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;

import java.util.*;

public class Query {
    private WebService ws;

    public Query() {
	this.ws = new WebService();
    }

    public Artist getArtistById(UUID id, Includes inc) throws WebServiceException {
	MMDDocument mmd = ws.getMMD("artist", id, null, inc);
	if (mmd.getArtist() == null)
	    throw new ResponseException("no artist element");
	return mmd.getArtist();
    }

    public Artist getArtistById(UUID id) throws WebServiceException {
	return getArtistById(id, null);
    }

    public Iterable<ArtistResult> getArtists(ArtistFilter filter) throws WebServiceException {
	return ws.getMMD("artist", null, filter, null).getArtistResults();
    }
}
