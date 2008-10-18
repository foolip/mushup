package org.musicbrainz.neo;

import org.neo4j.api.core.*;

enum RelTypes implements RelationshipType
{
    MUSICBRAINZ, ARTISTS, ARTIST, ALIAS, RELEASES, RELEASE,
    // MusicBrainz relationships
    Wikipedia
}
