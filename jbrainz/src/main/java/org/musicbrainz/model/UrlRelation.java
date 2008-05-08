package org.musicbrainz.model;

public interface UrlRelation extends Relation {
    public static final String TYPE_WIKIPEDIA = "http://musicbrainz.org/ns/mmd-1.0#Wikipedia";

    public String getUrl();
    public void setUrl(String url);
}
