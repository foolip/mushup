package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

abstract class NeoEntityFactory implements EntityFactory {
	protected final NeoService neo;
	protected final IndexService indexService;
	protected final Node mbNode;

	NeoEntityFactory(NeoService neo, IndexService indexService) {
		this.neo = neo;
		this.indexService = indexService;

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
