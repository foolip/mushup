package org.musicbrainz.webservice;

import org.musicbrainz.model.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;

class WebService {
    private String host;
    private int port;

    public WebService() {
	this.host = "musicbrainz.org";
	this.port = 80;
    }

    private URL makeURL(String entity, UUID id, Filter filter) throws WebServiceException {
	String url = "http://" + host;
	if (port != 80)
	    url += ":" + port;
	url += "/ws/1/" + entity + "/";

	if (id != null) {
	    url += id;
	}
	url += "?type=xml";

	if (filter != null) {
	    try {
		for (Map.Entry<String, String> e : filter.getParameters().entrySet()) {
		    url += "&" + URLEncoder.encode(e.getKey(), "UTF-8")
			+ "=" + URLEncoder.encode(e.getValue(), "UTF-8");
		}
	    } catch (UnsupportedEncodingException e) {
		throw new WebServiceException(e);
	    }
	}

	try {
	    return new URL(url);
	} catch (MalformedURLException e) {
	    throw new WebServiceException(e);
	}
    }

    public InputStream get(String entity, UUID id, Filter filter) throws WebServiceException {
	URL url = this.makeURL(entity, id, filter);
	try {
	    return url.openStream();
	} catch (IOException e) {
	    throw new WebServiceException(e);
	}
    }

    public MMDDocument getMMD(String entity, UUID id, Filter filter)
	throws WebServiceException {
	try {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setValidating(false);
	    factory.setNamespaceAware(true);
	    InputStream stream = get(entity, id, filter);
	    return new MMDDocument(factory.newDocumentBuilder().parse(stream));
	} catch (ParserConfigurationException e) {
	    throw new WebServiceException(e);
	} catch (SAXException e) {
	    throw new ResponseException(e);
	} catch (IOException e) {
	    throw new WebServiceException(e);
	}
    }
}
