package org.musicbrainz.webservice;

import org.musicbrainz.model.Artist;

public interface ArtistResult extends Result {
    Artist getArtist();
}
