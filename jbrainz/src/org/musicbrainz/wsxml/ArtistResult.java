package org.musicbrainz.wsxml;

import org.musicbrainz.model.*;

public class ArtistResult extends Result {
    private Artist artist;

    public Artist getArtist() {
	return this.artist;
    }

    public void setArtist(Artist artist) {
	this.artist = artist;
    }
}
