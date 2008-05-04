package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.util.*;

public class MushArtistFactory extends MushEntityFactory implements ArtistFactory {
    private final Node artistFactoryNode;

    public MushArtistFactory(NeoService neo) {
	super(neo);

	Relationship rel = mbNode.getSingleRelationship
	    (RelTypes.ARTISTS, Direction.OUTGOING);
	
        if (rel == null) {
            artistFactoryNode = neo.createNode();
            mbNode.createRelationshipTo
		(artistFactoryNode, RelTypes.ARTISTS);
        } else {
	    artistFactoryNode = rel.getEndNode();
	}
    }

    public MushArtist createArtist() {
        Node node = neo.createNode();
        artistFactoryNode.createRelationshipTo
	    (node, RelTypes.ARTIST);
        return new MushArtist(node);
    }

    public MushArtist getArtistById(UUID id) {
	Node node = MushUtil.getIndexService()
	    .getSingleNode("UUID", MushUtil.uuidToLongLong(id));
	if (node == null)
	    return null;
	return new MushArtist(node);
    }
}
