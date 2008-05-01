package org.musicbrainz.webservice;

import org.musicbrainz.model.*;

import org.w3c.dom.Node;

class MMDArtistResult extends MMDResult implements ArtistResult {
    private Artist artist;

    MMDArtistResult(Node node) throws ResponseException {
	super(node);
	artist = new MMDArtist(node);
    }

    public Artist getArtist() {
	return this.artist;
    }
}
