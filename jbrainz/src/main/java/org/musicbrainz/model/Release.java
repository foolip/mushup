package org.musicbrainz.model;

public interface Release extends Entity {
    public String getTitle();
    public void setTitle(String title);

    public String getAsin();
    public void setAsin(String asin);
}
