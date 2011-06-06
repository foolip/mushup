import org.musicbrainz.model.*;
import org.musicbrainz.neo.*;

import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.util.*;

public class Export {

	private static void exportArtists(NeoService neo) {
		NeoArtistFactory naf;
		Transaction tx = neo.beginTx();
		try {
			naf = new NeoArtistFactory(neo, null);
			tx.success();
		} finally {
			tx.finish();
		}
	}
	
	public static void main(String[] args) {
		NeoService neo = new EmbeddedNeo("neostore");

		exportArtists(neo);

		neo.shutdown();
	}
}
