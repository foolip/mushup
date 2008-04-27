package org.musicbrainz;

import java.util.regex.*;
import java.util.UUID;

public class Utils {
    public static UUID extractUUID(String s) {
	Pattern p = Pattern.compile("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}",
				    Pattern.CASE_INSENSITIVE);
	Matcher m = p.matcher(s);
	boolean found = m.find();
	if (found) {
	    return UUID.fromString(m.group());
	}
	throw new IllegalArgumentException("No UUID found in string");
    }
}
