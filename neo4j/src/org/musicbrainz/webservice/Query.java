package org.musicbrainz.webservice;

import org.musicbrainz.model.*;
import org.musicbrainz.Utils;
import org.musicbrainz.webservice.*;
import org.musicbrainz.wsxml.*;

import java.io.InputStream;
import java.util.*;

public class Query {
    private WebService ws;

    public Query() {
	this.ws = new WebService();
    }

    public Artist getArtistById(UUID id) throws WebServiceException {
	Metadata md = ws.get("artist", id, null);
	return md.getArtist();
    }

    public Artist getArtistById(String id) throws WebServiceException {
	return getArtistById(Utils.extractUUID(id));
    }

    public List<ArtistResult> getArtists(ArtistFilter filter) throws WebServiceException {
	Metadata md = ws.get("artist", null, filter);
	return md.getArtistResults();
    }

    public Release getReleaseById(UUID id) throws WebServiceException {
	Metadata md = ws.get("release", id, null);
	return md.getRelease();
    }

    public Release getReleaseById(String id) throws WebServiceException {
	return getReleaseById(Utils.extractUUID(id));
    }

    /*
    public List<ReleaseResult> getReleases(ReleaseFilter filter) throws WebServiceException {
	Metadata md = ws.get("release", null, filter);
	return md.getReleaseResults();
    }
    */
}
