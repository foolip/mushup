package org.musicbrainz.webservice;

import org.musicbrainz.model.*;
import org.musicbrainz.wsxml.*;

import java.io.*;
import java.net.*;
import java.util.UUID;

public class WebService {
    private String host;
    private int port;

    public WebService() {
	this.host = "musicbrainz.org";
	this.port = 80;
    }

    private URL makeURL(String entity, UUID id) throws WebServiceException {
	String url = "http://" + this.host;
	if (this.port != 80)
	    url += ":" + this.port;
	url += "/ws/1/" + entity + "/" + id + "?type=xml";
	// FIXME: remove
	System.out.println(url);
	try {
	    return new URL(url);
	} catch (MalformedURLException e) {
	    throw new WebServiceException(e);
	}
    }

    private Entity get(String entity, UUID id) throws WebServiceException {
	URL url = this.makeURL(entity, id);
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
	Entity e = get("artist", id);
	if (e instanceof Artist)
	    return (Artist)e;
	return null;
    }
}
