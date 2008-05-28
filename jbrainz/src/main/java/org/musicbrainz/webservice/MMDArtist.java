package org.musicbrainz.webservice;

import org.musicbrainz.model.*;

import java.util.*;
import org.w3c.dom.Node;

class MMDArtist extends MMDEntity implements Artist {
    private String type;
    private String name;
    private String sortName;
    private String disamb;
    private LinkedList<Release> releases;

    MMDArtist(Node node) throws ResponseException {
	super(node);

	setType(MMD.getUriAttr(node, "type", NS.MMD_1));

	this.releases = new LinkedList<Release>();
	for (Node n : MMD.iter(node.getChildNodes())) {
	    if (MMD.isMMD(n, "name")) {
		setName(n.getTextContent());
	    } else if (MMD.isMMD(n, "sort-name")) {
		setSortName(n.getTextContent());
	    } else if (MMD.isMMD(n, "disambiguation")) {
		setDisambiguation(n.getTextContent());
	    } else if (MMD.isMMD(n, "release-list")) {
		for (Node n2 : MMD.iter(n.getChildNodes())) {
		    if (MMD.isMMD(n2, "release")) {
			addRelease(new MMDRelease(n2));
		    }
		}
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

    public String getDisambiguation() {
	return this.disamb;
    }

    public void setDisambiguation(String disamb) {
	this.disamb = disamb;
    }

    public Iterable<Release> getReleases() {
	return this.releases;
    }

    public void addRelease(Release rel) {
	this.releases.add(rel);
    }
}
