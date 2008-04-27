package org.musicbrainz.model;

public class Artist extends Entity {
    public static final String TYPE_PERSON = "http://musicbrainz.org/ns/mmd-1.0#Person";
    public static final String TYPE_GROUP = "http://musicbrainz.org/ns/mmd-1.0#Group";

    private String type;
    private String name;
    private String sortName;

    public String getType() {
	return this.type;
    }

    public void setType(String type) {
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
}
