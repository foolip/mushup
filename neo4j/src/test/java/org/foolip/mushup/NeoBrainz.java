package org.foolip.mushup.test;

import org.neo4j.util.btree.*;
import org.neo4j.api.core.*;
import org.foolip.mushup.*;

import java.util.UUID;

public class NeoBrainz {

    public static void main(String[] args) {
        NeoService neo = new EmbeddedNeo("neobrainz");

	Transaction tx = Transaction.begin();
	try {
	    MushArtistFactory maf = new MushArtistFactory(neo);

	    MushArtist artist = maf.createArtist();
	    artist.setId(UUID.randomUUID());
	    artist.setName("The Beatles");
	    artist.setSortName("Beatles, The");
	    
	    tx.success();
	} finally {
	    tx.finish();
	    neo.shutdown();
	}
	System.out.println("OK");
    }
}
