package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/* wrap Query and limit rate to 1 request per second */
public class MushQuery extends Query {
    private final Log log = LogFactory.getLog(this.getClass());

    private static MushQuery instance;
    private final long minDelay = 1000;
    private long lastTime = 0;

    private MushQuery() {
	super();
    }

    public static synchronized MushQuery getInstance() {
	if (instance == null)
	    instance = new MushQuery();
	return instance;
    }

    public Object clone() throws CloneNotSupportedException {
	throw new CloneNotSupportedException();
    }

    /* wait until time is at least lastTime+minDelay */
    private synchronized void delay() {
	while (true) {
	    long now = new Date().getTime();
	    long sleepTime = minDelay - (now - lastTime);
	    if (sleepTime > 0) {
		try {
		    log.debug("Sleeping " + sleepTime + "ms before next MusicBrainz Webservice query");
		    Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
		}
	    } else {
		lastTime = now;
		break;
	    }
	}
    }

    public Artist getArtistById(UUID id, Includes inc) throws WebServiceException {
	delay();
	return super.getArtistById(id, inc);
    }

    public Artist getArtistById(UUID id) throws WebServiceException {
	delay();
	return super.getArtistById(id);
    }

    public Iterable<ArtistResult> getArtists(ArtistFilter filter) throws WebServiceException {
	delay();
	return super.getArtists(filter);
    }
}
