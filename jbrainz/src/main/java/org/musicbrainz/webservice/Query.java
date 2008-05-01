package org.musicbrainz.webservice;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;

import java.util.*;

public class Query {
    private WebService ws;

    public Query() {
	this.ws = new WebService();
    }

    public Artist getArtistById(UUID id) throws WebServiceException {
	MMDDocument mmd = ws.getMMD("artist", id, null);
	if (mmd.getArtist() == null)
	    throw new ResponseException("no artist element");
	return mmd.getArtist();
    }

    public Iterable<ArtistResult> getArtists(ArtistFilter filter) throws WebServiceException {
	return ws.getMMD("artist", null, filter).getArtistResults();
    }

    /*
    public Release getReleaseById(UUID id) throws WebServiceException {
	Metadata md = ws.get("release", id, null);
	return md.getRelease();
    }

    public List<ReleaseResult> getReleases(ReleaseFilter filter) throws WebServiceException {
	Metadata md = ws.get("release", null, filter);
	return md.getReleaseResults();
    }
    */
}
