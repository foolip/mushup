package org.musicbrainz.hibernate;

import org.musicbrainz.model.*;

import java.util.UUID;

public class HibernateEntity implements Entity {
	private int row;
	private UUID mbid;

	public HibernateEntity() {}

	public int getRow() {
		return this.row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public UUID getId() {
		return this.mbid;
	}

	public void setId(UUID id) {
		this.mbid = id;
	}

	public UUID getUuid() {
		return getId();
	}

	public void setUuid(UUID id) {
		setId(id);
	}

	public Iterable<UrlRelation> getUrlRelations() {
		return null;
	}

	public void addUrlRelation(UrlRelation urlRel) {

	}
}
