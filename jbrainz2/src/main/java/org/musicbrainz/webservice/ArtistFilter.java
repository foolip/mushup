package org.musicbrainz.webservice;

import java.util.*;

public class ArtistFilter extends Filter {
    private String name;
    private String query;

    public void setName(String name) {
	this.name = name;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    Map<String, String> getParameters() {
	Map<String, String> m = super.getParameters();
	if (name != null)
	    m.put("name", name);
	if (query != null)
	    m.put("query", query);
	return m;
    }
}
