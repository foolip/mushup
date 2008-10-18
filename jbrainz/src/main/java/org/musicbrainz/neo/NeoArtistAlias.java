package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.util.index.IndexService;
import org.neo4j.util.NodeWrapperImpl;

public class NeoArtistAlias extends NodeWrapperImpl implements ArtistAlias {
	protected final IndexService indexService;

	private static final String KEY_NAME = "name";

	public NeoArtistAlias(NeoService neo, Node node) {
		super(neo, node);
		indexService = null;
	}

	NeoArtistAlias(NeoService neo, Node node, IndexService indexService) {
		super(neo, node);
		this.indexService = indexService;
	}

	public String getName() {
		return (String)getUnderlyingNode().getProperty(KEY_NAME);
	}

	public void setName(String name) {
		if (name == null || name.equals(""))
			getUnderlyingNode().removeProperty(KEY_NAME);
		else
			getUnderlyingNode().setProperty(KEY_NAME, name);
	}
}
