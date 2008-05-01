package org.musicbrainz.webservice;

import org.musicbrainz.model.Entity;

import java.util.UUID;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

abstract class MMDEntity implements Entity {
    private UUID id;

    MMDEntity(Node node) throws ResponseException {
	Element elem = (Element)node;

	if (!elem.hasAttribute("id")) {
	    throw new ResponseException("no id attribute");
	}
	try {
	    setId(UUID.fromString(elem.getAttribute("id")));
	} catch (IllegalArgumentException e) {
	    throw new ResponseException("non-UUID id attribute");
	}
    }

    public UUID getId() {
	return this.id;
    }

    public void setId(UUID id) {
	this.id = id;
    }
}
