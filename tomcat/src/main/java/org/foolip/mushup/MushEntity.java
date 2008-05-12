package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.util.UUID;

public abstract class MushEntity implements Entity {
    protected final Node underlyingNode;
    protected final IndexService indexService;

    private static final String KEY_MBID = "mbid";

    MushEntity(Node underlyingNode, IndexService indexService) {
        this.underlyingNode = underlyingNode;
        this.indexService = indexService;
    }

    Node getUnderlyingNode() {
        return underlyingNode;
    }

    public UUID getId() {
	return UUID.fromString((String)underlyingNode.getProperty(KEY_MBID));
    }

    public void setId(UUID id) {
	underlyingNode.setProperty(KEY_MBID, id.toString());

	// FIXME: remove possible old value?
	indexService.index(underlyingNode, "UUID", id.getLeastSignificantBits());
    }

    public Iterable<UrlRelation> getUrlRelations() {
	return null; // FIXME: some iterator thing
    }

    public void addUrlRelation(UrlRelation urlRel) {
	String type = urlRel.getType();
	if (type.startsWith(NS.REL_1)) {
	    type = type.substring(NS.REL_1.length());
	    RelationshipType relType =  DynamicRelationshipType.getRelationshipType(type);
	    Node urlNode = ((MushUrlRelation)urlRel).getUnderlyingNode();
	    underlyingNode.createRelationshipTo(urlNode, relType);
	}
    }

    /* get corresponding wikipedia node in the specified language, or
     * null if there is no such relationship */
    private Node getWikipediaNode(String lang) {
	for (Relationship rel : underlyingNode.getRelationships
		 (RelTypes.Wikipedia, Direction.OUTGOING)) {
	    Node node = rel.getEndNode();
	    if (node.hasProperty("url")) {
		String url = (String)node.getProperty("url");
		if (url.startsWith("http://" + lang + ".wikipedia.org/")) {
		    return node;
		}
	    }
	}
	return null;
    }

    public String getWikipediaUrl() {
	Node node = getWikipediaNode("en");
	if (node == null || !node.hasProperty("url"))
	    return null;
	return (String)node.getProperty("url");
    }

    /* connect to wikipedia and get the blurb (takes time!) */
    public void updateWikipediaBlurb() throws WikipediaException {
	Node node = getWikipediaNode("en");

	if (node == null)
	    return;

	// FIXME: maybe add some kind of timestamp for updating
	if (node.hasProperty("blurb"))
	    return;

	// FIXME: this error should be logged
	if (!node.hasProperty("url"))
	    return;

	String url = (String)node.getProperty("url");
	String blurb = WikipediaBlurb.getBlurb(url);
	node.setProperty("blurb", blurb);
    }

    public String getWikipediaBlurb() {
	Node node = getWikipediaNode("en");
	if (node == null || !node.hasProperty("blurb"))
	    return null;
	return (String)node.getProperty("blurb");
    }
}
