package org.foolip.mushup;

import org.neo4j.api.core.*;
import java.util.*;

public class DynamicRelationshipType implements RelationshipType
{
    private static Map<String, RelationshipType> types = new HashMap<String, RelationshipType>();

    private String name;

    private DynamicRelationshipType(String name) {
	this.name = name;
    }

    public String name() {
	return name;
    }

    public static synchronized RelationshipType getRelationshipType(String name)
    {
	RelationshipType type = types.get(name);
	if (type == null) {
	    type = new DynamicRelationshipType(name);
	    types.put(name, type);
	}
	return type;
    }
}
