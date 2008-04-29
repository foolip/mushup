package org.musicbrainz.webservice;

import org.musicbrainz.model.*;

import java.util.*;
import org.w3c.dom.*;

// wraper for a MusicBrainz XML Metadata Format (MMD) Document
class MMDDocument {
    private MMDArtist artist;
    private LinkedList<ArtistResult> artistResults;

    /*
    private int artistResultsOffset;
    private int artistResultsCount;
    private Release release;
    */

    MMDDocument(Document doc) throws ResponseException {
	NodeList elems = doc.getElementsByTagNameNS(MMD.NS_MMD_1, "metadata");
	if (elems.getLength() == 0) {
	    throw new ResponseException("no mmd:metadata root element");
	}

	for (Node n : MMD.iter(elems.item(0).getChildNodes())) {
	    //System.out.println(n.getNodeName());
	    if (MMD.isMMD(n, "artist")) {
		artist = new MMDArtist(n);
	    /*
	    }
	    else if (isMMD(n, "release")) {
		md.setRelease(parseRelease(n));
	    */
	    } else if (MMD.isMMD(n, "artist-list")) {
		artistResults = new LinkedList<ArtistResult>();
		for (Node n2 : MMD.iter(n.getChildNodes())) {
		    if (MMD.isMMD(n2, "artist")) {
			artistResults.add(new MMDArtistResult(n2));
		    }
		}
	    }
	}
    }

    public Artist getArtist() {
	return artist;
    }

    public Iterable<ArtistResult> getArtistResults() {
	return this.artistResults;
    }

    /*
    public void setArtistResults(List<ArtistResult> artistResults) {
	this.artistResults = artistResults;
    }

    public int getArtistResultsOffset() {
	return this.artistResultsOffset;
    }

    public void setArtistResultsOffset(int offset) {
	this.artistResultsOffset = offset;
    }

    public int getArtistResultsCount() {
	return this.artistResultsCount;
    }

    public void setArtistResultsCount(int count) {
	this.artistResultsCount = count;
    }

    public Release getRelease() {
	return this.release;
    }

    public void setRelease(Release release) {
	this.release = release;
    }
    */
}
