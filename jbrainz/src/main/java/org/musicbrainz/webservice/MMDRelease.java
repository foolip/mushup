package org.musicbrainz.webservice;

import org.musicbrainz.model.*;

import java.util.*;
import org.w3c.dom.Node;

class MMDRelease extends MMDEntity implements Release {
    private String title;
    private String asin;

    private LinkedList<Release> releases;

    MMDRelease(Node node) throws ResponseException {
	super(node);

	for (Node n : MMD.iter(node.getChildNodes())) {
	    if (MMD.isMMD(n, "title")) {
		setTitle(n.getTextContent());
	    } else if (MMD.isMMD(n, "asin")) {
		setAsin(n.getTextContent());
	    }
	}
    }

    public String getTitle() {
	return this.title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getAsin() {
	return this.asin;
    }

    public void setAsin(String asin) {
	this.asin = asin;
    }
}
