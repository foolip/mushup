package org.musicbrainz.hibernate;

import org.musicbrainz.model.Artist;
import org.musicbrainz.model.ArtistAlias;
import org.musicbrainz.model.Release;
import org.musicbrainz.model.UrlRelation;

import java.util.Set;
import java.util.HashSet;

public class HibernateArtist extends HibernateEntity implements Artist {
	private Type type;
	private String name;
	private String sortName;
	private String disamb;
	private Set<HibernateArtistAlias> aliases = new HashSet<HibernateArtistAlias>();
	
	public HibernateArtist() {}

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

	public Iterable<Release> getReleases() {
		return null;
	}

	public void addRelease(Release rel) {

	}

	public void setAliasesSet(Set<HibernateArtistAlias> aliases) {
		this.aliases = aliases;
	}

	public Set<HibernateArtistAlias> getAliasesSet() {
		return aliases;
	}

	public void addAlias(ArtistAlias alias) {
		getAliasesSet().add((HibernateArtistAlias)alias);
	}

	public Iterable<HibernateArtistAlias> getAliases() {
		return getAliasesSet();
	}
}
