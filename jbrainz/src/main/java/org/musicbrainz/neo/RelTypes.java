package org.musicbrainz.neo;

import org.neo4j.api.core.*;

enum RelTypes implements RelationshipType
{
    MUSICBRAINZ, ARTISTS, ARTIST, ARTIST_ALIAS, RELEASES, RELEASE, TRACKS, TRACK,
    ARTIST_RELEASE, RELEASE_TRACK,
    // MusicBrainz relationships
    Wikipedia
}
