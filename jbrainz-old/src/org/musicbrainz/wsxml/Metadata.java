package org.musicbrainz.wsxml;

import java.util.*;

import org.musicbrainz.model.*;

public class Metadata {
    private Artist artist;
    private List<ArtistResult> artistResults;
    private int artistResultsOffset;
    private int artistResultsCount;

    private Release release;

    public Artist getArtist() {
	return this.artist;
    }

    public void setArtist(Artist artist) {
	this.artist = artist;
    }

    public List<ArtistResult> getArtistResults() {
	return this.artistResults;
    }

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
}
