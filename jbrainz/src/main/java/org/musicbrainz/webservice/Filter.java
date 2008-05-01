package org.musicbrainz.webservice;

import java.util.*;

public abstract class Filter {
    private int limit;
    private int offset;

    public void setLimit(int limit) {
	this.limit = limit;
    }

    public void setOffset(int offset) {
	this.offset = offset;
    }

    Map<String, String> getParameters() {
	Map<String, String> m = new HashMap<String, String>();
	if (limit != 0)
	    m.put("limit", Integer.toString(limit));
	if (offset != 0)
	    m.put("offset", Integer.toString(offset));
	return m;
    }
}
