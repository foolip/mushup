package org.musicbrainz.model;

import java.util.*;

public class Track extends Entity {
    private Artist artist;
    private String title;

    public Artist getArtist() {
	return this.artist;
    }

    public void setArtist(Artist artist) {
	this.artist = artist;
    }
    
    public String getTitle() {
	return this.title;
    }

    public void setTitle(String title) {
	this.title = title;
    }
}
