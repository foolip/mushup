package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

import java.util.*;

public class Replicator {

    private static Replicator instance;

    private MushArtistFactory maf;

    public Replicator(NeoService neo, IndexService indexService) {
	maf = new MushArtistFactory(neo, indexService);
    }

    public synchronized MushArtist getArtistById(UUID id, int depth) {
	MushArtist artist = maf.getArtistById(id);
	return artist;
    }
}
