package org.musicbrainz.webservice;

import org.musicbrainz.model.*;
import org.musicbrainz.Utils;
import org.musicbrainz.webservice.WebService;

import java.io.InputStream;
import java.util.*;

public class Query {
    private WebService ws;

    public Query() {
	this.ws = new WebService();
    }

    public Artist getArtistById(UUID id) throws WebServiceException {
	Object result = ws.get(ws.ARTIST, id, null);
	if (result instanceof Artist)
	    return (Artist)result;
	throw new WebServiceException();
    }

    public Artist getArtistById(String id) throws WebServiceException {
	return getArtistById(Utils.extractUUID(id));
    }

    public List<Artist> getArtists(ArtistFilter filter)
	throws WebServiceException {

	Object result = ws.get(ws.ARTIST, null, filter);
	//if (result instanceof List<Artist>)
	//    return (List<Artist>)result;
	throw new WebServiceException();
    }
}
