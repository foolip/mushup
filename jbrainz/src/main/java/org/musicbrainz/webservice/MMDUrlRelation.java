package org.musicbrainz.webservice;

import org.musicbrainz.model.*;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class MMDUrlRelation extends MMDRelation implements UrlRelation {
    private String url;

    MMDUrlRelation(Node node) throws ResponseException {
	super(node);

	setUrl(((Element)node).getAttribute("target"));
    }

    public String getUrl() {
	return this.url;
    }

    public void setUrl(String url) {
	this.url = url;
    }
}
