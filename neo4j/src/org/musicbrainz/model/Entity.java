package org.musicbrainz.model;

import java.util.UUID;
import org.musicbrainz.Utils;

public class Entity {
    private UUID id;

    public UUID getId() {
	return this.id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

    public void setId(String id) {
	this.setId(Utils.extractUUID(id));
    }
}
