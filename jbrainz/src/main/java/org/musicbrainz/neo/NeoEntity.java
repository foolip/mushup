package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.*;
import org.neo4j.util.NodeWrapperImpl;
import java.util.UUID;

public abstract class NeoEntity extends NodeWrapperImpl implements Entity {
	protected final IndexService indexService;

	private static final String KEY_MBID = "mbid";

	NeoEntity(NeoService neo, Node underlyingNode, IndexService indexService) {
		super(neo, underlyingNode);
		this.indexService = indexService;
	}

	public UUID getId() {
		long[] uuid = (long[])getUnderlyingNode().getProperty(KEY_MBID);
		if (uuid.length == 2)
			return new UUID(uuid[0], uuid[1]);
		return null;
	}

	public void setId(UUID id) {
		long[] uuid = {id.getMostSignificantBits(), id.getLeastSignificantBits()};
		getUnderlyingNode().setProperty(KEY_MBID, uuid);

		// FIXME: remove possible old value?
		//indexService.index(getUnderlyingNode(), "UUID", id.hashCode());
	}

	public Iterable<UrlRelation> getUrlRelations() {
		return null; // FIXME: some iterator thing
	}

	public void addUrlRelation(UrlRelation urlRel) {
		/*
	String type = urlRel.getType();
	if (type.startsWith(NS.REL_1)) {
	    type = type.substring(NS.REL_1.length());
	    RelationshipType relType =  DynamicRelationshipType.getRelationshipType(type);
	    Node urlNode = ((NeoUrlRelation)urlRel).getUnderlyingNode();
	    underlyingNode.createRelationshipTo(urlNode, relType);
	}
		 */
	}
}
