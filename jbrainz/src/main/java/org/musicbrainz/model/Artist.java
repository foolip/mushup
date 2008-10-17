package org.musicbrainz.model;

public interface Artist extends Entity {
    public enum Type { UNKNOWN, PERSON, GROUP }

    public Type getType();
    public void setType(Type type);
    
    public String getName();
    public void setName(String name);

    public String getSortName();
    public void setSortName(String sortName);
    
    public String getDisambiguation();
    public void setDisambiguation(String disamb);

    public Iterable<Release> getReleases();
    public void addRelease(Release rel);
}
