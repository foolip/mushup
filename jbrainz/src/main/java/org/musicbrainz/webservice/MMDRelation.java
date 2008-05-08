package org.musicbrainz.webservice;

import org.musicbrainz.model.NS;
import org.musicbrainz.model.Relation;

import org.w3c.dom.Node;

public class MMDRelation implements Relation {
    private String type;

    MMDRelation(Node node) throws ResponseException {
	setType(MMD.getUriAttr(node, "type", NS.REL_1));
    }

    public String getType() {
	return this.type;
    }

    public void setType(String type) {
	this.type = type;
    }
}
