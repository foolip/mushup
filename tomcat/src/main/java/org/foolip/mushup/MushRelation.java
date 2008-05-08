package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Node;

public class MushRelation implements Relation {
    private String type;
    protected final Node underlyingNode;

    MushRelation(Node underlyingNode) {
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
