package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

public class NeoArtistAliasFactory implements ArtistAliasFactory {
	protected final NeoService neo;
	protected final IndexService indexService;

	public NeoArtistAliasFactory(NeoService neo, IndexService indexService) {
		this.neo = neo;
		this.indexService = indexService;
	}

	public NeoArtistAlias createArtistAlias() {
		Node node = neo.createNode();
		return new NeoArtistAlias(neo, node, indexService);
	}
}
