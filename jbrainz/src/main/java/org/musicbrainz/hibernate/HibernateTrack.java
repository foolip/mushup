package org.musicbrainz.hibernate;

import java.util.HashSet;
import java.util.Set;

import org.musicbrainz.model.*;

public class HibernateTrack extends HibernateEntity implements Track {
	private String title;
	private Set<HibernateReleaseTrack> releaseTracks;
	
	HibernateTrack() {
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
}
