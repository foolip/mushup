package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Node;

public class NeoUrlRelation extends NeoRelation implements UrlRelation {
    private static final String KEY_URL = "url";

    NeoUrlRelation(Node underlyingNode) {
	super(underlyingNode);
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public String getUrl() {
	return (String)underlyingNode.getProperty(KEY_URL);
    }

    public void setUrl(String url) {
	underlyingNode.setProperty(KEY_URL, url);
    }
}
