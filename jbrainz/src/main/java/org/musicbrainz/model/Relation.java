package org.musicbrainz.model;

public interface Relation {
    public static final String TARGET_URL = NS.REL_1 + "Url";

    public String getType();
    public void setType(String type);
}
