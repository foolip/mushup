package org.musicbrainz.neo;

import org.musicbrainz.model.*;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;
import org.neo4j.api.core.Direction;
import org.neo4j.util.index.IndexService;
import org.neo4j.util.NeoRelationshipSet;
import org.neo4j.util.NodeWrapperRelationshipSet;

public class NeoArtist extends NeoEntity implements Artist {
	private static final String KEY_TYPE = "type";
	private static final String KEY_NAME = "name";
	private static final String KEY_SORT_NAME = "sortName";
	private static final String KEY_DISAMBIGUATION = "disambiguation";
	private static final String KEY_DATE_BEGIN= "beginDate";
	private static final String KEY_DATE_END= "endDate";
	
	private final NeoRelationshipSet<NeoArtistAlias> aliases;
	private final NeoRelationshipSet<NeoRelease> releases;
	
	NeoArtist(NeoService neo, Node underlyingNode, IndexService indexService) {
		super(neo, underlyingNode, indexService);
		aliases = new NodeWrapperRelationshipSet<NeoArtistAlias>(getNeo(), getUnderlyingNode(),
				RelTypes.ARTIST_ALIAS, Direction.OUTGOING, NeoArtistAlias.class);
		releases = new NodeWrapperRelationshipSet<NeoRelease>(getNeo(), getUnderlyingNode(),
				RelTypes.ARTIST_RELEASE, Direction.OUTGOING, NeoRelease.class);
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
		return (String)getUnderlyingNode().getProperty(KEY_NAME, null);
	}

	public void setName(String name) {
		if (name == null || name.equals(""))
			getUnderlyingNode().removeProperty(KEY_NAME);
		else
			getUnderlyingNode().setProperty(KEY_NAME, name);
		// remove sort name if it is redundant
		//if (name.equals((String)getUnderlyingNode().getProperty(KEY_SORT_NAME, null)))
		//	getUnderlyingNode().removeProperty(KEY_SORT_NAME);
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
		return (String)getUnderlyingNode().getProperty(KEY_DISAMBIGUATION, null);
	}

	public void setDisambiguation(String disamb) {
		setOrRemoveProperty(KEY_DISAMBIGUATION, disamb);
	}
	
	public String getBeginDate() {
		return (String)getUnderlyingNode().getProperty(KEY_DATE_BEGIN, null);
	}

	public void setBeginDate(String date) {
		setOrRemoveProperty(KEY_DATE_BEGIN, date);
	}

	public String getEndDate() {
		return (String)getUnderlyingNode().getProperty(KEY_DATE_BEGIN, null);
	}

	public void setEndDate(String date) {
		setOrRemoveProperty(KEY_DATE_END, date);
	}
	
	public Iterable<NeoArtistAlias> getAliases() {
		return aliases;
	}

	public void addAlias(ArtistAlias alias) {
		aliases.add((NeoArtistAlias)alias);
	}

	public Iterable<NeoRelease> getReleases() {
		return releases;
	}

	public void addRelease(Release release) {
		releases.add((NeoRelease)release);
	}
	
	private void setOrRemoveProperty(String key, Object value) {
		if (value == null)
			getUnderlyingNode().removeProperty(key);
		else
			getUnderlyingNode().setProperty(key, value);
	}
}
