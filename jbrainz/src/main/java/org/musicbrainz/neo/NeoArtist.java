package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Direction;
import org.neo4j.util.index.IndexService;
import org.neo4j.util.NeoRelationshipSet;
import org.neo4j.util.NodeWrapperRelationshipSet;

import java.util.*;

public class NeoArtist extends NeoEntity implements Artist {
	private static final String KEY_TYPE = "type";
	private static final String KEY_NAME = "name";
	private static final String KEY_SORT_NAME = "sortName";
	private static final String KEY_DISAMBIGUATION = "disambiguation";
	
	private final NeoRelationshipSet<NeoArtistAlias> aliases;

	NeoArtist(NeoService neo, Node underlyingNode, IndexService indexService) {
		super(neo, underlyingNode, indexService);
		aliases = new NodeWrapperRelationshipSet<NeoArtistAlias>(getNeo(), getUnderlyingNode(),
				RelTypes.ALIAS, Direction.OUTGOING, NeoArtistAlias.class);
	}

	public Type getType() {
		if (getUnderlyingNode().hasProperty(KEY_TYPE)) {
			char type = ((Character)getUnderlyingNode().getProperty(KEY_TYPE)).charValue();
			if (type == 'P')
				return Type.PERSON;
			else if (type == 'G')
				return Type.GROUP;
		}
		return Type.UNKNOWN;
	}

	public void setType(Type type) {
		switch (type) {
		case PERSON:
			getUnderlyingNode().setProperty(KEY_TYPE, 'P');
			break;
		case GROUP:
			getUnderlyingNode().setProperty(KEY_TYPE, 'G');
			break;
		default:
			getUnderlyingNode().removeProperty(KEY_TYPE);
		break;
		}
	}

	public String getName() {
		return (String)getUnderlyingNode().getProperty(KEY_NAME);
	}

	public void setName(String name) {
		if (name == null || name.equals(""))
			getUnderlyingNode().removeProperty(KEY_NAME);
		else
			getUnderlyingNode().setProperty(KEY_NAME, name);
		// remove sort name if it is redundant
		if (name.equals((String)getUnderlyingNode().getProperty(KEY_SORT_NAME, null)))
			getUnderlyingNode().removeProperty(KEY_SORT_NAME);
	}

	public String getSortName() {
		if (getUnderlyingNode().hasProperty(KEY_SORT_NAME))
			return (String)getUnderlyingNode().getProperty(KEY_SORT_NAME);
		return getName();
	}

	public void setSortName(String sortName) {
		if (sortName == null || sortName.equals("") || sortName.equals(getName()))
			getUnderlyingNode().removeProperty(KEY_SORT_NAME);
		else
			getUnderlyingNode().setProperty(KEY_SORT_NAME, sortName);
	}

	public String getDisambiguation() {
		return (String)getUnderlyingNode().getProperty(KEY_DISAMBIGUATION);
	}

	public void setDisambiguation(String disamb) {
		if (disamb == null || disamb.equals(""))
			getUnderlyingNode().removeProperty(KEY_DISAMBIGUATION);
		else
			getUnderlyingNode().setProperty(KEY_DISAMBIGUATION, disamb);
	}

	public Iterable<NeoArtistAlias> getAliases() {
		return aliases;
	}

	public void addAlias(ArtistAlias alias) {
		aliases.add((NeoArtistAlias)alias);
	}

	public Iterable<Release> getReleases() {
		// FIXME: why does this suck so badly?
		/*
		LinkedList<Release> releases = new LinkedList<Release>();
		for (Relationship rel : getUnderlyingNode().
				getRelationships(RelTypes.RELEASE, Direction.OUTGOING)) {
			releases.add(new NeoRelease(rel.getEndNode(), this.indexService));
		}
		return releases;
		*/
		return null;
	}

	public void addRelease(Release rel) {
		getUnderlyingNode().createRelationshipTo(((NeoRelease)rel).getUnderlyingNode(), RelTypes.RELEASE);
	}
}
