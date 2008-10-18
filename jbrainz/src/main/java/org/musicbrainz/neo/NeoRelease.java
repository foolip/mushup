package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.util.index.IndexService;

public class NeoRelease extends NeoEntity implements Release {
	private static final String KEY_TITLE = "title";
	private static final String KEY_ASIN = "asin";

	NeoRelease(NeoService neo, Node node, IndexService indexService) {
		super(neo, node, indexService);
	}

	public String getTitle() {
		return (String)getUnderlyingNode().getProperty(KEY_TITLE);
	}

	public void setTitle(String title) {
		getUnderlyingNode().setProperty(KEY_TITLE, title);
	}

	public String getAsin() {
		if (!getUnderlyingNode().hasProperty(KEY_ASIN))
			return null;
		return (String)getUnderlyingNode().getProperty(KEY_ASIN);
	}

	public void setAsin(String asin) {
		getUnderlyingNode().setProperty(KEY_ASIN, asin);
	}
}
