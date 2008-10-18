package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

import java.util.*;

public class NeoArtistFactory extends NeoEntityFactory implements ArtistFactory {
	private final Node artistFactoryNode;

	public NeoArtistFactory(NeoService neo, IndexService indexService) {
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

	public NeoArtist createArtist() {
		Node node = neo.createNode();
		artistFactoryNode.createRelationshipTo(node, RelTypes.ARTIST);
		return new NeoArtist(neo, node, indexService);
	}

	public NeoArtist getArtistById(UUID id) {
		for (Node node : indexService.getNodes("UUID", id.hashCode())) {
			NeoArtist artist = new NeoArtist(neo, node, indexService);
			if (artist.getId().equals(id))
				return artist;
		}
		return null;
	}

	public synchronized NeoArtist getOrCreateArtist(UUID id) {
		NeoArtist artist = getArtistById(id);
		if (artist == null) {
			artist = createArtist();
			artist.setId(id);
		}
		return artist;
	}
}
