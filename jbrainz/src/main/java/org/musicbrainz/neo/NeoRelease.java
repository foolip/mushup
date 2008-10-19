package org.musicbrainz.neo;

import java.util.SortedMap;
import java.util.TreeMap;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Direction;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Relationship;
import org.neo4j.util.index.IndexService;

public class NeoRelease extends NeoEntity implements Release {
	private static final String KEY_TITLE = "title";
	private static final String KEY_TRACK_NUMBER = "trackNumber";

	NeoRelease(NeoService neo, Node node, IndexService indexService) {
		super(neo, node, indexService);
	}
	
	public NeoArtist getArtist() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setArtist(Artist artist) {
		// TODO Auto-generated method stub
		
	}
	
	public String getTitle() {
		return (String)getUnderlyingNode().getProperty(KEY_TITLE);
	}

	public void setTitle(String title) {
		getUnderlyingNode().setProperty(KEY_TITLE, title);
	}

	public SortedMap<Integer, NeoTrack> getTracks() {
		SortedMap<Integer, NeoTrack> tracks = new TreeMap<Integer, NeoTrack>();
		for (Relationship rel : getUnderlyingNode().getRelationships(RelTypes.RELEASE_TRACK, Direction.OUTGOING)) {
			int trackNumber = (Integer)rel.getProperty(KEY_TRACK_NUMBER);
			NeoTrack track = new NeoTrack(getNeo(), rel.getEndNode(), indexService);
			tracks.put(trackNumber, track);
		}
		return tracks;
	}
	
	public void addTrack(Track track, int number) {
		Relationship rel = getUnderlyingNode()
			.createRelationshipTo(((NeoTrack)track).getUnderlyingNode(), RelTypes.RELEASE_TRACK);
		rel.setProperty(KEY_TRACK_NUMBER, number);
	}
}
