package org.foolip.mushup;

import org.musicbrainz.model.Entity;
import org.neo4j.api.core.Node;

import java.util.UUID;

public abstract class MushEntity implements Entity {
    protected final Node underlyingNode;

    private static final String KEY_MBID = "mbid";

    MushEntity(Node underlyingNode) {
        this.underlyingNode = underlyingNode;
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public UUID getId() {
	return UUID.fromString((String)underlyingNode.getProperty(KEY_MBID));
    }

    public void setId(UUID id) {
	underlyingNode.setProperty(KEY_MBID, id.toString());
    }
}
