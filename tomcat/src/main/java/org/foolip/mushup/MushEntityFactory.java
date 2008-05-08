package org.foolip.mushup;

import org.musicbrainz.model.*;
import org.neo4j.api.core.*;
import org.neo4j.util.index.IndexService;

abstract class MushEntityFactory implements EntityFactory {
    protected final NeoService neo;
    protected final IndexService indexService;
    protected final Node mbNode;

    MushEntityFactory(NeoService neo, IndexService indexService) {
	this.neo = neo;
	this.indexService = indexService;

	Relationship rel = neo.getReferenceNode().getSingleRelationship
	    (RelTypes.MUSICBRAINZ, Direction.OUTGOING);
	
        if (rel == null) {
            mbNode = neo.createNode();
            neo.getReferenceNode().createRelationshipTo
		(mbNode, RelTypes.MUSICBRAINZ);
        } else {
	    mbNode = rel.getEndNode();
	}
    }

    protected void copyRelations(Entity entity, MushEntity mushEntity) {
	for (UrlRelation urlRel : entity.getUrlRelations()) {
	    MushUrlRelation mushUrlRel = new MushUrlRelation(neo.createNode());
	    mushUrlRel.setType(urlRel.getType());
	    mushUrlRel.setUrl(urlRel.getUrl());
	    mushEntity.addUrlRelation(mushUrlRel);
	}
    }
}
