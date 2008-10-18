package org.musicbrainz.hibernate;

import org.musicbrainz.model.ArtistAlias;

public class HibernateArtistAlias implements ArtistAlias {
	private int row;
	private String name;

	public HibernateArtistAlias() {}
	
	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
