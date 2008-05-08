package org.musicbrainz.webservice;

import org.musicbrainz.model.NS;
import java.util.*;
import org.w3c.dom.*;

// random constants and helper functions
class MMD {
    private MMD() {} // do not instantiate

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
		    public void remove() {/* no can do */}
		};
	    }
	};
    }

    static boolean isMMD(Node node, String name) {
	return node.getNodeType() == Node.ELEMENT_NODE &&
	    node.getLocalName().equals(name) && 
	    node.getNamespaceURI().equals(NS.MMD_1);
    }

    static String getUriAttr(Node node, String attrName, String nsPrefix) {
	String fragment = ((Element)node).getAttribute(attrName);
	if (!fragment.equals(""))
	    return nsPrefix + fragment;
	return null;
    }
}
