package org.musicbrainz.webservice;

import java.util.*;

public class ArtistFilter implements Filter {
    private String name;
    private String query;
    private int limit;
    private int offset;

    public void setName(String name) {
	this.name = name;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public void setLimit(int limit) {
	this.limit = limit;
    }

    public void setOffset(int offset) {
	this.offset = offset;
    }

    public Map<String, String> getParameters() {
	Map<String, String> m = new HashMap<String, String>();
	if (name != null)
	    m.put("name", name);
	if (query != null)
	    m.put("query", query);
	if (limit != 0)
	    m.put("limit", Integer.toString(limit));
	if (offset != 0)
	    m.put("offset", Integer.toString(offset));
	return m;
    }
}
