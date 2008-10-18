package org.musicbrainz.model;

public interface Release extends Entity {
	public Artist getArtist();
	public void setArtist(Artist artist);
	
    public String getTitle();
    public void setTitle(String title);
}
