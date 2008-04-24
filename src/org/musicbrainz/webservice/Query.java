package org.musicbrainz.webservice;

import org.musicbrainz.model.*;
import org.musicbrainz.Utils;
import org.musicbrainz.webservice.WebService;

import java.io.InputStream;
import java.util.UUID;

public class Query {
    public Artist getArtistById(UUID id) throws WebServiceException {
	WebService ws = new WebService();
	return ws.getArtist(id);
    }

    public Artist getArtistById(String id) throws WebServiceException {
	return this.getArtistById(Utils.extractUUID(id));
    }

    public Artist getArtists(ArtistFilter filter) {
	
    }
}
