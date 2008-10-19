package org.musicbrainz.model;

import java.util.SortedMap;

public interface Release extends Entity {
	public Artist getArtist();
	public void setArtist(Artist artist);
	
    public String getTitle();
    public void setTitle(String title);
    
    public SortedMap<Integer, ? extends Track>getTracks();
    public void addTrack(Track track, int number);
}
