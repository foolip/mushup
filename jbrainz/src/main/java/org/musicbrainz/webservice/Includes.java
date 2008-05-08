package org.musicbrainz.webservice;

import java.util.*;

public class Includes {
    LinkedList<String> includes;

    public Includes() {
	includes = new LinkedList<String>();
    }

    public void include(String tag) {
	includes.add(tag);
    }

    Iterable<String> getIncludeTags() {
	return includes;
    }
}
