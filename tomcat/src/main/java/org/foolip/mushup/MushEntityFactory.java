package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

abstract class MushEntityFactory implements EntityFactory {
    protected final NeoService neo;
    protected final Node mbNode;

    MushEntityFactory(NeoService neo) {
	this.neo = neo;

	Relationship rel = neo.getReferenceNode().getSingleRelationship
	    (RelTypes.MUSICBRAINZ, Direction.OUTGOING);
	
        if (rel == null) {
            mbNode = neo.createNode();
            neo.getReferenceNode().createRelationshipTo
		(mbNode, RelTypes.MUSICBRAINZ);
        } else {
	    mbNode = rel.getEndNode();
	}
    }
}
