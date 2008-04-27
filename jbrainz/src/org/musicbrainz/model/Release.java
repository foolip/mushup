package org.musicbrainz.model;

import java.util.*;

public class Release extends Entity {
    private Artist artist;
    private String title;
    private LinkedList<String> types;

    public Release() {
	this.types = new LinkedList<String>();
    }

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

    public List<String> getTypes() {
	return this.types;
    }

    public void addType(String type) {
	this.types.add(type);
    }
}
