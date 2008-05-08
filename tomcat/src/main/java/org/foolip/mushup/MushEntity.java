package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.util.UUID;

abstract class MushEntity implements Entity {
    protected final Node underlyingNode;
    protected final IndexService indexService;

    private static final String KEY_MBID = "mbid";

    MushEntity(Node underlyingNode, IndexService indexService) {
        this.underlyingNode = underlyingNode;
        this.indexService = indexService;
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public UUID getId() {
	return UUID.fromString((String)underlyingNode.getProperty(KEY_MBID));
    }

    public void setId(UUID id) {
	underlyingNode.setProperty(KEY_MBID, id.toString());

	// FIXME: remove possible old value?
	indexService.index(underlyingNode, "UUID", id.getLeastSignificantBits());
    }

    public Iterable<UrlRelation> getUrlRelations() {
	return null; // FIXME: some iterator thing
    }

    public void addUrlRelation(UrlRelation urlRel) {
	String type = urlRel.getType();
	if (type.startsWith(NS.REL_1)) {
	    type = type.substring(NS.REL_1.length());
	    RelationshipType relType =  DynamicRelationshipType.getRelationshipType(type);
	    Node urlNode = ((MushUrlRelation)urlRel).getUnderlyingNode();
	    underlyingNode.createRelationshipTo(urlNode, relType);
	}
    }
}
