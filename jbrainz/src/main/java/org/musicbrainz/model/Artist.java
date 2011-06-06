package org.musicbrainz.model;

public interface Artist extends Entity {
    public static enum Type { UNKNOWN, PERSON, GROUP }

    public Type getType();
    public void setType(Type type);
    
    public String getName();
    public void setName(String name);

    public String getSortName();
    public void setSortName(String sortName);
    
    public String getDisambiguation();
    public void setDisambiguation(String disamb);

    public String getBeginDate();
    public void setBeginDate(String date);
    
    public String getEndDate();
    public void setEndDate(String date);
    
    public Iterable<? extends ArtistAlias> getAliases();
    public void addAlias(ArtistAlias alias);

    public Iterable<? extends Release> getReleases();
    public void addRelease(Release release);
}
