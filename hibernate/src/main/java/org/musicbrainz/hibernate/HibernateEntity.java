package org.musicbrainz.hibernate;

import org.musicbrainz.model.*;

import java.util.UUID;

public class HibernateEntity implements Entity {
	private int dbrow;
	private UUID mbid;

    public HibernateEntity() {}

    public int getRow() {
	return this.dbrow;
    }

    public void setRow(int row) {
	this.dbrow = row;
    }

    public String getUuid() {
    	if (this.mbid != null)
    		return this.mbid.toString();
    	return null;
    }

    public void setUuid(String id) {
    	this.mbid = UUID.fromString(id);
    }
    
    public UUID getId() {
    	return this.mbid;
    }

    public void setId(UUID id) {
    	this.mbid = id;
    }
    
    public UUID getXid() {
    	return this.mbid;
    }

    public void setXid(UUID id) {
    	this.mbid = id;
    }

    public Iterable<UrlRelation> getUrlRelations() {
    	return null;
    }

    public void addUrlRelation(UrlRelation urlRel) {

    }
}
