package org.musicbrainz.webservice;

import org.musicbrainz.model.*;
import org.musicbrainz.wsxml.*;

import java.io.*;
import java.net.*;
import java.util.*;

class WebService {
    public static final String ARTIST = "artist";

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

	// FIXME: remove
	System.out.println(url);
	try {
	    return new URL(url);
	} catch (MalformedURLException e) {
	    throw new WebServiceException(e);
	}
    }

    public Entity get(String entity, UUID id, Filter filter) throws WebServiceException {
	URL url = this.makeURL(entity, id, filter);
	MbXmlParser parser = new MbXmlParser();
	InputStream is;
	try {
	    is = url.openStream();
	    return parser.parse(is);
	} catch (IOException e) {
	    throw new ConnectionException(e);
	} catch (ParserException e) {
	    throw new WebServiceException(e);
	}
    }

    public Artist getArtist(UUID id) throws WebServiceException {
	Entity e = get("artist", id, null);
	if (e instanceof Artist)
	    return (Artist)e;
	return null;
    }
}
