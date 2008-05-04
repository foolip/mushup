package org.foolip.mushup;

import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.util.UUID;

class MushUtil {
    private static NeoService neo;
    private static IndexService index;

    private MushUtil() { /* do not want! */ }

    static synchronized NeoService getNeoService() {
	if (neo == null)
	    neo = new EmbeddedNeo("/tmp/neostore");
	return neo;
    }

    static synchronized IndexService getIndexService() {
	if (index == null)
	    index = new NeoIndexService(getNeoService());
	return index;
    }

    static long[] uuidToLongLong(UUID uuid) {
	long[] foo = {uuid.getMostSignificantBits(),
		      uuid.getLeastSignificantBits()};
	return foo;
    }
}
