package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.api.core.Direction;
import org.neo4j.util.index.IndexService;

import java.util.*;

public class MushArtist extends MushEntity implements Artist {
    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_SORT_NAME = "sortName";
    private static final String KEY_DISAMBIGUATION = "disambiguation";

    MushArtist(Node underlyingNode, IndexService indexService) {
	super(underlyingNode, indexService);
    }

    public String getType() {
	return (String)underlyingNode.getProperty(KEY_TYPE);
    }

    public void setType(String type) {
	underlyingNode.setProperty(KEY_TYPE, type);
    }

    public String getName() {
	return (String)underlyingNode.getProperty(KEY_NAME);
    }

    public void setName(String name) {
	underlyingNode.setProperty(KEY_NAME, name);
    }

    public String getSortName() {
	return (String)underlyingNode.getProperty(KEY_SORT_NAME);
    }

    public void setSortName(String sortName) {
	underlyingNode.setProperty(KEY_SORT_NAME, sortName);
    }

    public String getDisambiguation() {
	return (String)underlyingNode.getProperty(KEY_DISAMBIGUATION);
    }

    public void setDisambiguation(String disamb) {
	underlyingNode.setProperty(KEY_DISAMBIGUATION, disamb);
    }

    public Iterable<Release> getReleases() {
	// FIXME: why does this suck so badly?
	LinkedList<Release> releases = new LinkedList<Release>();
	for (Relationship rel : underlyingNode.
		 getRelationships(RelTypes.RELEASE, Direction.OUTGOING)) {
	    releases.add(new MushRelease(rel.getEndNode(), this.indexService));
	}
	return releases;
    }

    public void addRelease(Release rel) {
	underlyingNode.createRelationshipTo(((MushRelease)rel).getUnderlyingNode(), RelTypes.RELEASE);
    }
}
