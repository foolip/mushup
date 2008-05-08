package org.musicbrainz.webservice;

import org.musicbrainz.model.NS;
import org.musicbrainz.model.Artist;

import org.w3c.dom.Node;

class MMDArtist extends MMDEntity implements Artist {
    private String type;
    private String name;
    private String sortName;

    MMDArtist(Node node) throws ResponseException {
	super(node);

	setType(MMD.getUriAttr(node, "type", NS.MMD_1));

	for (Node n : MMD.iter(node.getChildNodes())) {
	    if (MMD.isMMD(n, "name")) {
		setName(n.getTextContent());
	    } else if (MMD.isMMD(n, "sort-name")) {
		setSortName(n.getTextContent());
	    }
	}
    }

    public String getType() {
	return this.type;
    }

    public void setType(String type) {
	this.type = type;
    }
    
    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSortName() {
	return this.sortName;
    }

    public void setSortName(String sortName) {
	this.sortName = sortName;
    }
}
