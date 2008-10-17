package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.util.UUID;

public abstract class NeoEntity implements Entity {
    protected final Node underlyingNode;
    protected final IndexService indexService;

    private static final String KEY_MBID = "mbid";

    NeoEntity(Node underlyingNode, IndexService indexService) {
        this.underlyingNode = underlyingNode;
        this.indexService = indexService;
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public UUID getId() {
	long[] uuid = (long[])underlyingNode.getProperty(KEY_MBID);
	return new UUID(uuid[0], uuid[1]);
    }

    public void setId(UUID id) {
	long[] uuid = {id.getMostSignificantBits(), id.getLeastSignificantBits()};
	underlyingNode.setProperty(KEY_MBID, uuid);

	// FIXME: remove possible old value?
	indexService.index(underlyingNode, "UUID", id.hashCode());
    }

    public Iterable<UrlRelation> getUrlRelations() {
	return null; // FIXME: some iterator thing
    }

    public void addUrlRelation(UrlRelation urlRel) {
	/*
	String type = urlRel.getType();
	if (type.startsWith(NS.REL_1)) {
	    type = type.substring(NS.REL_1.length());
	    RelationshipType relType =  DynamicRelationshipType.getRelationshipType(type);
	    Node urlNode = ((NeoUrlRelation)urlRel).getUnderlyingNode();
	    underlyingNode.createRelationshipTo(urlNode, relType);
	}
	*/
    }
}
