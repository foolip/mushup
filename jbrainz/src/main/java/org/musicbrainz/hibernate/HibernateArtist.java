package org.musicbrainz.hibernate;

import org.musicbrainz.model.Artist;
import org.musicbrainz.model.ArtistAlias;
import org.musicbrainz.model.Release;
import java.util.Set;
import java.util.HashSet;

public class HibernateArtist extends HibernateEntity implements Artist {
	private Type type;
	private String name;
	private String sortName;
	private String disamb;
	private Set<HibernateArtistAlias> aliases;
	private Set<HibernateRelease> releases;
	
	public HibernateArtist() {
		aliases = new HashSet<HibernateArtistAlias>();
		releases = new HashSet<HibernateRelease>();
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getDisambiguation() {
		return this.disamb;
	}

	public void setDisambiguation(String disamb) {
		this.disamb = disamb;
	}

	public void setReleases(Set<HibernateRelease> releases) {
		this.releases = releases;
	}
	
	public Set<HibernateRelease> getReleases() {
		return releases;
	}

	public void addRelease(Release release) {
		getReleases().add((HibernateRelease)release);
	}

	public void setAliases(Set<HibernateArtistAlias> aliases) {
		this.aliases = aliases;
	}

	public Set<HibernateArtistAlias> getAliases() {
		return aliases;
	}

	public void addAlias(ArtistAlias alias) {
		getAliases().add((HibernateArtistAlias)alias);
	}
}
