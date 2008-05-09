package org.foolip.mushup;

import java.io.*;
import java.net.*;
import java.util.regex.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import org.htmlparser.*;
import org.htmlparser.util.*;
import org.htmlparser.filters.*;
//import org.htmlparser.visitors.UrlModifyingVisitor;


public class WikipediaBlurb {

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

	// get rid of references
	//nl.keepAllNodesThatMatch(new NotFilter(new HasAttributeFilter("class", "reference")), true);

	String result = "";
	for (SimpleNodeIterator i = nl.elements(); i.hasMoreNodes();) {
	    result += i.nextNode().toHtml();
	}

	return result;
	/*
	UrlModifyingVisitor foo = new UrlModifyingVisitor(baseUrl);
	try {
	    parser.visitAllNodesWith(foo);
	} catch (ParserException e) {
	    throw new WikipediaException(e);
	}
	return foo.getModifiedResult();
	*/
    }
}
