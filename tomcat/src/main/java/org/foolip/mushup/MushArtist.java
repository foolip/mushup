package org.foolip.mushup;

import org.musicbrainz.model.Artist;
import org.neo4j.api.core.Node;
import org.neo4j.util.index.IndexService;

public class MushArtist extends MushEntity implements Artist {
    private static final String KEY_TYPE = "type";
    private static final String KEY_NAME = "name";
    private static final String KEY_SORT_NAME = "sortName";

    MushArtist(Node underlyingNode, IndexService indexService) {
	super(underlyingNode, indexService);
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public String getType() {
	return (String)underlyingNode.getProperty(KEY_TYPE);
    }

    public void setType(String type) {
	underlyingNode.setProperty(KEY_TYPE, type);
    }

    public String getName() {
	return (String)underlyingNode.getProperty(KEY_NAME);
    }

    public void setName(String name) {
	underlyingNode.setProperty(KEY_NAME, name);
    }

    public String getSortName() {
	return (String)underlyingNode.getProperty(KEY_SORT_NAME);
    }

    public void setSortName(String sortName) {
	underlyingNode.setProperty(KEY_SORT_NAME, sortName);
    }
}
