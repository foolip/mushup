package org.musicbrainz.hibernate;

public class HibernateReleaseTrack {
	private int row;
	private HibernateRelease release;
	private HibernateTrack track;
	private int trackNumber;
	
	HibernateReleaseTrack() {}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}
	
	public HibernateRelease getRelease() {
		return release;
	}

	public void setRelease(HibernateRelease release) {
		this.release = release;
	}

	public HibernateTrack getTrack() {
		return track;
	}

	public void setTrack(HibernateTrack track) {
		this.track = track;
	}

	public int getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(int trackNumber) {
		this.trackNumber = trackNumber;
	}
}
