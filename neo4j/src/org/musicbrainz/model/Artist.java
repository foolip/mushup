package org.musicbrainz.model;

public interface Artist extends Entity {
    public static final String TYPE_PERSON = "http://musicbrainz.org/ns/mmd-1.0#Person";
    public static final String TYPE_GROUP = "http://musicbrainz.org/ns/mmd-1.0#Group";

    public String getType();
    public void setType(String type);
    
    public String getName();
    public void setName(String name);

    public String getSortName();
    public void setSortName(String sortName);
}
