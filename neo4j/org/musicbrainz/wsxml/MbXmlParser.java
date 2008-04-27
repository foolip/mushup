package org.musicbrainz.wsxml;

import org.musicbrainz.model.*;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MbXmlParser {

    private static final String NS_MMD_1 = "http://musicbrainz.org/ns/mmd-1.0#";
    private static final String NS_REL_1 = "http://musicbrainz.org/ns/rel-1.0#";
    private static final String NS_EXT_1 = "http://musicbrainz.org/ns/ext-1.0#";
    
    private Document parseXML(InputStream is) throws IOException, ParserException {
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setValidating(false);
	    factory.setNamespaceAware(true);
	    Document doc = factory.newDocumentBuilder().parse(is);
	    return doc;
	} catch (ParserConfigurationException e) {
	    throw new ParserException(e);
	} catch (SAXException e) {
	    throw new ParserException(e);
	}
    }

    private boolean matches(Node node, String name) {
	return node.getNodeType() == Node.ELEMENT_NODE &&
	    node.getLocalName() == name && 
	    node.getNamespaceURI() == NS_MMD_1;
    }

    private Artist createArtist(Element elem) {
	Artist a = new Artist();
	a.setId(elem.getAttribute("id"));
	
	if (elem.getAttribute("type") == "Person") {
	    a.setType(Artist.TYPE_PERSON);
	} else if (elem.getAttribute("type") == "Group") {
	    a.setType(Artist.TYPE_GROUP);
	}
	for (Node n : iter(elem.getChildNodes())) {
	    if (matches(n, "name")) {
		a.setName(n.getTextContent());
	    } else if (matches(n, "sort-name")) {
		a.setSortName(n.getTextContent());
	    }
	}
	return a;
    }

    private Object parseNode(Node n) throws ParserException {
	if (matches(n, "artist")) {
	    return createArtist(elem);
	} else {
	    throw new ParserException("unknown element: " + elem.getTagName());
	}
    }

    public Object parse(InputStream is) throws IOException, ParserException {
	Document doc = this.parseXML(is);

	NodeList elems = doc.getElementsByTagNameNS(NS_MMD_1, "metadata");
	if (elems.getLength() == 0) {
	    throw new ParserException("cannot find root element mmd:metafda");
	}

	//
	elems = elems.item(0).getElementsByTagName("*");
	if (elems.getLength() == 0) {
	    throw new ParserException("empty metadata element");
	}
	return parseNode(elems.item(0));
    }

    // utility iterator
    private static Iterable<Node> iter(final NodeList list) {
        return new Iterable<Node>() {
	    public Iterator<Node> iterator()
	    {
		return new Iterator<Node>() {
		    private int curr = 0;
		    private int length = list.getLength();
		    public boolean hasNext() { return curr < length; }
		    public Node next() { return list.item(curr++); }
		    public void remove() { throw new UnsupportedOperationException(); }
		};
	    }
	};
    }
}
