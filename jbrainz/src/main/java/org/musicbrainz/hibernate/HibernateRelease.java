package org.musicbrainz.hibernate;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.musicbrainz.model.Artist;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.Track;

public class HibernateRelease extends HibernateEntity implements Release {
	private String title;
	private HibernateArtist artist;
	private Set<HibernateReleaseTrack> releaseTracks;
	
	HibernateRelease() {
		setReleaseTracks(new HashSet<HibernateReleaseTrack>());
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setReleaseTracks(Set<HibernateReleaseTrack> releaseTracks) {
		this.releaseTracks = releaseTracks;
	}

	public Set<HibernateReleaseTrack> getReleaseTracks() {
		return releaseTracks;
	}

	public void setArtist(Artist artist) {
		this.artist = (HibernateArtist)artist;
	}

	public HibernateArtist getArtist() {
		return artist;
	}

	public SortedMap<Integer, HibernateTrack> getTracks() {
		SortedMap<Integer, HibernateTrack> tracks = new TreeMap<Integer, HibernateTrack>();
		for (HibernateReleaseTrack releaseTrack : getReleaseTracks()) {
			int trackNumber = releaseTrack.getTrackNumber();
			HibernateTrack track = releaseTrack.getTrack();
			tracks.put(trackNumber, track);
		}
		return tracks;
	}
	
	public void addTrack(Track track, int number) {
		HibernateReleaseTrack releaseTrack = new HibernateReleaseTrack();
		releaseTrack.setTrackNumber(number);
		releaseTrack.setTrack((HibernateTrack)track);
		getReleaseTracks().add(releaseTrack);
	}
}