package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.api.core.Direction;
import org.neo4j.util.index.IndexService;

import java.util.*;

public class NeoArtist extends NeoEntity implements Artist {
    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_SORT_NAME = "sortName";
    private static final String KEY_DISAMBIGUATION = "disambiguation";

    NeoArtist(Node underlyingNode, IndexService indexService) {
	super(underlyingNode, indexService);
    }

    public Type getType() {
	if (underlyingNode.hasProperty(KEY_TYPE)) {
	    char type = ((Character)underlyingNode.getProperty(KEY_TYPE)).charValue();
	    if (type == 'P')
		return Type.PERSON;
	    else if (type == 'G')
		return Type.GROUP;
	}
	return Type.UNKNOWN;
    }

    public void setType(Type type) {
	switch (type) {
	case PERSON:
	    underlyingNode.setProperty(KEY_TYPE, 'P');
	    break;
	case GROUP:
	    underlyingNode.setProperty(KEY_TYPE, 'G');
	    break;
	default:
	    underlyingNode.removeProperty(KEY_TYPE);
	    break;
	}
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
	    releases.add(new NeoRelease(rel.getEndNode(), this.indexService));
	}
	return releases;
    }

    public void addRelease(Release rel) {
	underlyingNode.createRelationshipTo(((NeoRelease)rel).getUnderlyingNode(), RelTypes.RELEASE);
    }
}
