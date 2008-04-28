package org.musicbrainz.wsxml;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.NS;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MbXmlParser {

    public Metadata parse(InputStream is) throws IOException, ParserException {
	Document doc = this.parseXML(is);

	NodeList elems = doc.getElementsByTagNameNS(NS.MMD_1, "metadata");
	if (elems.getLength() == 0) {
	    throw new ParserException("cannot find root element mmd:metafda");
	}

	return parseMetadata(elems.item(0));
    }

    private boolean isMMD(Node node, String name) {
	return node.getNodeType() == Node.ELEMENT_NODE &&
	    node.getLocalName() == name && 
	    node.getNamespaceURI() == NS.MMD_1;
    }

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

    private int parseScore(Node node) {
	try {
	    return Integer.parseInt(((Element)node).getAttributeNS(NS.EXT_1, "score"));
	} catch (NumberFormatException e) {
	    return 0;
	}
    }

    private int parseCount(Node node) {
	try {
	    return Integer.parseInt(((Element)node).getAttributeNS(NS.MMD_1, "count"));
	} catch (NumberFormatException e) {
	    return 0;
	}
    }

    private int parseOffset(Node node) {
	try {
	    return Integer.parseInt(((Element)node).getAttributeNS(NS.MMD_1, "offset"));
	} catch (NumberFormatException e) {
	    return 0;
	}
    }

    private String parseId(Node node) {
	return ((Element)node).getAttribute("id");
    }

    // FIXME: ugly as hell
    private List<String> parseUriListAttr(Node node, String attrName) {
	String attr = ((Element)node).getAttribute(attrName);
	if (attr == null)
	    return null;

	LinkedList<String> uris = new LinkedList<String>();
	for (String uri : attr.split("\\s+")) {
	    if (uri != "")
		uris.add(NS.MMD_1 + uri);
	}
	return uris;
    }

    private String parseUriAttr(Node node, String attrName) {
	String uri = ((Element)node).getAttribute(attrName);
	if (uri != "")
	    return NS.MMD_1 + uri;
	return null;
    }

    private Artist parseArtist(Node node) {
	Artist a = new Artist();

	a.setId(parseId(node));
	a.setType(parseUriAttr(node, "type"));

	for (Node n : iter(node.getChildNodes())) {
	    if (isMMD(n, "name")) {
		a.setName(n.getTextContent());
	    } else if (isMMD(n, "sort-name")) {
		a.setSortName(n.getTextContent());
	    }
	}
	return a;
    }

    private Release parseRelease(Node node) {
	Element elem = (Element)node;

	Release r = new Release();

	r.setId(parseId(node));
	for (String type: parseUriListAttr(node, "type")) {
	    r.addType(type);
	}

	for (Node n : iter(elem.getChildNodes())) {
	    if (isMMD(n, "title")) {
		r.setTitle(n.getTextContent());
	    } else if (isMMD(n, "track-list")) {
		
	    }
	}
	return r;
    }

    private List<ArtistResult> parseArtistResults(Node node) {
	Element elem = (Element)node;

	LinkedList<ArtistResult> list = new LinkedList<ArtistResult>();

	for (Node n : iter(node.getChildNodes())) {
	    if (isMMD(n, "artist")) {
		ArtistResult ar = new ArtistResult();
		ar.setArtist(parseArtist(n));
		ar.setScore(parseScore(n));
		list.add(ar);
	    }
	}

	return list;
    }

    private Metadata parseMetadata(Node node) {
	Metadata md = new Metadata();
	for (Node n : iter(node.getChildNodes())) {
	    //System.out.println(n.getNodeName());
	    if (isMMD(n, "artist")) {
		md.setArtist(parseArtist(n));
	    } else if (isMMD(n, "release")) {
		md.setRelease(parseRelease(n));
	    } else if (isMMD(n, "artist-list")) {
		md.setArtistResultsOffset(parseOffset(n));
		md.setArtistResultsCount(parseCount(n));
		md.setArtistResults(parseArtistResults(n));
	    }
	}
	return md;
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
