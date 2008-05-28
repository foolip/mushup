package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;
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

    public MushArtist getArtistById(UUID id) {
	Node node = indexService.getSingleNode("UUID", id.getLeastSignificantBits());
	if (node == null)
	    return null;
	return new MushArtist(node, indexService);
    }

    public synchronized MushArtist getOrCreateArtist(UUID id) {
	MushArtist artist = getArtistById(id);
	if (artist == null) {
	    artist = createArtist();
	    artist.setId(id);
	}
	return artist;
    }

    public void replicate(MushArtist mushArtist) throws WebServiceException {
	Query q = MushQuery.getInstance();
	Includes inc = new Includes();
	inc.include("url-rels");
	inc.include("sa-Album");
	Artist mbArtist = q.getArtistById(mushArtist.getId(), inc);

	mushArtist.setId(mbArtist.getId());
	mushArtist.setName(mbArtist.getName());
	mushArtist.setSortName(mbArtist.getSortName());
	String disamb = mbArtist.getDisambiguation();
	if (disamb != null)
	    mushArtist.setDisambiguation(disamb);

	MushReleaseFactory mrf = new MushReleaseFactory(neo, indexService);
	for (Release rel : mbArtist.getReleases()) {
	    MushRelease mushRel = mrf.createRelease();
	    mushRel.setId(rel.getId());
	    mushRel.setTitle(rel.getTitle());
	    String asin = rel.getAsin();
	    if (asin != null)
		mushRel.setAsin(asin);
	    mushArtist.addRelease(mushRel);
	}

	copyRelations(mbArtist, mushArtist);

	mushArtist.setReplicated();
    }
}
