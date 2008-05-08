package org.musicbrainz.webservice;

import org.musicbrainz.model.NS;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

abstract class MMDResult implements Result {
    private int score;

    MMDResult(Node node) throws ResponseException {
	Element elem = (Element)node;

	if (!elem.hasAttributeNS(NS.EXT_1, "score")) {
	    throw new ResponseException("no ext:score attribute");
	}
	try {
	    score = Integer.parseInt(elem.getAttributeNS(NS.EXT_1, "score"));
	} catch (NumberFormatException e) {
	    throw new ResponseException("non-integer ext:score attribute");
	}
    }

    public int getScore() {
	return this.score;
    }
}
