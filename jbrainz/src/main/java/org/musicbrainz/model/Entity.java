package org.musicbrainz.model;

import java.util.UUID;

public abstract interface Entity {
    public UUID getId();
    public void setId(UUID id);

    public Iterable<UrlRelation> getUrlRelations();
    public void addUrlRelation(UrlRelation urlRel);
}
