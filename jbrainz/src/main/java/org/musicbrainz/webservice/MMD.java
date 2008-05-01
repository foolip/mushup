package org.musicbrainz.webservice;

import java.util.*;
import org.w3c.dom.*;

// random constants and helper functions
class MMD {
    private MMD() {} // do not instantiate

    static final String NS_MMD_1 = "http://musicbrainz.org/ns/mmd-1.0#";
    static final String NS_REL_1 = "http://musicbrainz.org/ns/rel-1.0#";
    static final String NS_EXT_1 = "http://musicbrainz.org/ns/ext-1.0#";

    // utility iterator
    static final Iterable<Node> iter(final NodeList list) {
        return new Iterable<Node>() {
	    public Iterator<Node> iterator()
	    {
		return new Iterator<Node>() {
		    private int curr = 0;
		    private int length = list.getLength();
		    public boolean hasNext() { return curr < length; }
		    public Node next() { return list.item(curr++); }
		    public void remove(/* no can do */) {}
		};
	    }
	};
    }

    static boolean isMMD(Node node, String name) {
	return node.getNodeType() == Node.ELEMENT_NODE &&
	    node.getLocalName() == name && 
	    node.getNamespaceURI() == NS_MMD_1;
    }

    static String getUriAttr(Node node, String attrName) {
	String uri = ((Element)node).getAttribute(attrName);
	if (uri != "")
	    return NS_MMD_1 + uri;
	return null;
    }
}
