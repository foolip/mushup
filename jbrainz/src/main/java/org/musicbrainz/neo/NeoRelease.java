package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Node;
import org.neo4j.util.index.IndexService;

public class NeoRelease extends NeoEntity implements Release {
    private static final String KEY_TITLE = "title";
    private static final String KEY_ASIN = "asin";

    NeoRelease(Node underlyingNode, IndexService indexService) {
	super(underlyingNode, indexService);
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public String getTitle() {
	return (String)underlyingNode.getProperty(KEY_TITLE);
    }

    public void setTitle(String title) {
	underlyingNode.setProperty(KEY_TITLE, title);
    }

    public String getAsin() {
	if (!underlyingNode.hasProperty(KEY_ASIN))
	    return null;
	return (String)underlyingNode.getProperty(KEY_ASIN);
    }

    public void setAsin(String asin) {
	underlyingNode.setProperty(KEY_ASIN, asin);
    }
}
