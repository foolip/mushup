package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.Node;

public class MushUrlRelation extends MushRelation implements UrlRelation {
    private static final String KEY_URL = "url";

    MushUrlRelation(Node underlyingNode) {
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
