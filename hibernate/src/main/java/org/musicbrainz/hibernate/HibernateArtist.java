package org.musicbrainz.hibernate;

import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.UrlRelation;

public class HibernateArtist extends HibernateEntity implements Artist {
    private Type type;
    private String name;
    private String sortName;
    private String disamb;

    public HibernateArtist() {}

    public Type getType() {
	return this.type;
    }

    public void setType(Type type) {
	this.type = type;
    }
    
    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSortName() {
	return this.sortName;
    }

    public void setSortName(String sortName) {
	this.sortName = sortName;
    }
    
    public String getDisambiguation() {
	return this.disamb;
    }

    public void setDisambiguation(String disamb) {
	this.disamb = disamb;
    }

    public Iterable<Release> getReleases() {
	return null;
    }

    public void addRelease(Release rel) {

    }
}
