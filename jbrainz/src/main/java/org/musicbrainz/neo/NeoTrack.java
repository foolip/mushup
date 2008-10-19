package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.util.index.IndexService;

public class NeoTrack extends NeoEntity implements Track {
	private static final String KEY_TITLE = "title";

	NeoTrack(NeoService neo, Node node, IndexService indexService) {
		super(neo, node, indexService);
	}
	
	public String getTitle() {
		return (String)getUnderlyingNode().getProperty(KEY_TITLE);
	}

	public void setTitle(String title) {
		if (title == null || title.equals(""))
			getUnderlyingNode().removeProperty(KEY_TITLE);
		else
			getUnderlyingNode().setProperty(KEY_TITLE, title);
	}
}
