package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Node;

public class NeoRelation implements Relation {
    private String type;
    protected final Node underlyingNode;

    NeoRelation(Node underlyingNode) {
        this.underlyingNode = underlyingNode;
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public String getType() {
	return this.type;
    }

    public void setType(String type) {
	this.type = type;
    }
}
