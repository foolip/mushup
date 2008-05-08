package org.musicbrainz.webservice;

import org.musicbrainz.model.*;

import java.util.*;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

abstract class MMDEntity implements Entity {
    private UUID id;
    private LinkedList<UrlRelation> urlRels;

    MMDEntity(Node node) throws ResponseException {
	Element elem = (Element)node;

	try {
	    setId(UUID.fromString(elem.getAttribute("id")));
	} catch (IllegalArgumentException e) {
	    throw new ResponseException("non-UUID id attribute");
	}

	urlRels = new LinkedList<UrlRelation>();
	for (Node n : MMD.iter(node.getChildNodes())) {
	    if (MMD.isMMD(n, "relation-list")) {
		String targetType = MMD.getUriAttr(n, "target-type", NS.REL_1);
		if (targetType != null) {
		    if (targetType.equals(Relation.TARGET_URL)) {
			for (Node n2 : MMD.iter(n.getChildNodes())) {
			    if (MMD.isMMD(n2, "relation")) {
			    addUrlRelation(new MMDUrlRelation(n2));
			    }
			}
		    }
		}
	    }
	}
    }

    public UUID getId() {
	return this.id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

    public Iterable<UrlRelation> getUrlRelations() {
	return this.urlRels;
    }

    public void addUrlRelation(UrlRelation urlRel) {
	this.urlRels.add(urlRel);
    }
}
