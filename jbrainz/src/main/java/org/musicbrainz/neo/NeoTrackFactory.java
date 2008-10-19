package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

public class NeoTrackFactory extends NeoEntityFactory implements TrackFactory {
	private final Node trackFactoryNode;

	public NeoTrackFactory(NeoService neo, IndexService indexService) {
		super(neo, indexService);

		Relationship rel = mbNode.getSingleRelationship(RelTypes.TRACKS, Direction.OUTGOING);
		if (rel == null) {
			trackFactoryNode = neo.createNode();
			mbNode.createRelationshipTo(trackFactoryNode, RelTypes.TRACKS);
		} else {
			trackFactoryNode = rel.getEndNode();
		}
	}

	public NeoTrack createTrack() {
		Node node = neo.createNode();
		trackFactoryNode.createRelationshipTo(node, RelTypes.TRACK);
		return new NeoTrack(neo, node, indexService);
	}
}
