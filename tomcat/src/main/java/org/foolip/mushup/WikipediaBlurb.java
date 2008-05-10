package org.foolip.mushup;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import org.htmlparser.*;
import org.htmlparser.filters.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.*;

public class WikipediaBlurb {
    private final static int blurbLength = 200;

    public static String getBlurb(String url) throws WikipediaException {
	Pattern pattern = Pattern.compile("^(http://[^.]+\\.wikipedia.org)/wiki/(.+)$");
	Matcher m = pattern.matcher(url);
	if (!m.matches())
	    throw new WikipediaException("Unrecognized Wikipedia URL: " + url);

	String baseUrl = m.group(1);
	String page = m.group(2);
	String apiUrl = baseUrl + "/w/api.php?format=xml&action=parse&prop=text&page=" + page;

	Document doc;
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setValidating(false);
	    doc = factory.newDocumentBuilder().parse(new URL(apiUrl).openStream());
	} catch (ParserConfigurationException e) {
	    throw new WikipediaException(e);
	} catch (SAXException e) {
	    throw new WikipediaException(e);
	} catch (IOException e) {
	    throw new WikipediaException(e);
	}

	org.w3c.dom.NodeList nodes = doc.getElementsByTagName("text");
	if (nodes.getLength() == 0)
	    throw new WikipediaException("No parsed text returned");

	String html = ((Element)nodes.item(0)).getTextContent();

	Parser parser = Parser.createParser(html, "UTF-8");
	PrototypicalNodeFactory factory = new PrototypicalNodeFactory();
	factory.registerTag (new SupTag());
	parser.setNodeFactory (factory);

	org.htmlparser.util.NodeList nl;
	try {
	    nl = parser.parse(null);
	} catch (ParserException e) {
	    throw new WikipediaException(e);
	}

	// only consider top-level paragraphs
	nl.keepAllNodesThatMatch(new TagNameFilter("P"));

	if (nl.size() == 0)
	    throw new WikipediaException("No paragraphs in article");

	// get rid of references ...
	nl.keepAllNodesThatMatch(new NotFilter(new CssClassFilter("reference")), true);

	// ... and [citation needed]
	nl.keepAllNodesThatMatch(new NotFilter(new CssClassFilter("noprint")), true);

	StringBuilder blurb = new StringBuilder();
	for (SimpleNodeIterator i = nl.elements(); i.hasMoreNodes();) {
	    blurb.append(i.nextNode().toPlainTextString().replaceAll("\\s+", " "));
	    // FIXME: Chinese doesn't want spaces!
	    blurb.append("\n");
	}

	if (blurb.length() < 10) {
	    throw new WikipediaException("Rejecting almost empty article");
	}

	// cut at the appropriate blurb length
	if (blurb.length() <= blurbLength) {
	    return blurb.toString();
	}

	// remove last word
	int lastSpace = blurb.lastIndexOf(" ", blurbLength);
	if (blurbLength - lastSpace < 10) {
	    return blurb.substring(0, lastSpace);
	}

	return blurb.substring(0, blurbLength);
    }

    public static final class SupTag extends CompositeTag
    {
	private static final String[] mIds = new String[] {"SUP"};
	public String[] getIds () { return (mIds); }
	public String[] getEnders () { return (mIds); }
	public String[] getEndTagEnders () { return (new String[0]); }
    }
}
