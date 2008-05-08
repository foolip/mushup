package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

import java.util.*;

public class MushArtistFactory extends MushEntityFactory implements ArtistFactory {
    private final Node artistFactoryNode;

    public MushArtistFactory(NeoService neo, IndexService indexService) {
	super(neo, indexService);

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
        return new MushArtist(node, indexService);
    }

    public MushArtist copyArtist(Artist artist) {
	MushArtist mushArtist = createArtist();
	mushArtist.setId(artist.getId());
	mushArtist.setName(artist.getName());
	mushArtist.setSortName(artist.getSortName());

	copyRelations(artist, mushArtist);

	return mushArtist;
    }

    public MushArtist getArtistById(UUID id) {
	Node node = indexService.getSingleNode("UUID", id.getLeastSignificantBits());
	if (node == null)
	    return null;
	return new MushArtist(node, indexService);
    }
}
