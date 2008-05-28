package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

import java.util.*;

public class MushReleaseFactory extends MushEntityFactory implements ReleaseFactory {
    private final Node releaseFactoryNode;

    public MushReleaseFactory(NeoService neo, IndexService indexService) {
	super(neo, indexService);

	Relationship rel = mbNode.getSingleRelationship
	    (RelTypes.RELEASES, Direction.OUTGOING);
	
        if (rel == null) {
            releaseFactoryNode = neo.createNode();
            mbNode.createRelationshipTo
		(releaseFactoryNode, RelTypes.RELEASES);
        } else {
	    releaseFactoryNode = rel.getEndNode();
	}
    }

    public MushRelease createRelease() {
        Node node = neo.createNode();
        releaseFactoryNode.createRelationshipTo
	    (node, RelTypes.RELEASE);
        return new MushRelease(node, indexService);
    }
}
