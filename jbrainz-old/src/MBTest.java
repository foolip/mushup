import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;
import org.musicbrainz.wsxml.*;

import java.util.*;

public class MBTest {
    public static void main(String args[]) throws Exception {
	Query q = new Query();
	/*	
	Artist a;

	a = q.getArtistById("c0b2500e-0cef-4130-869d-732b23ed9df5");
	print(a);
	*/ 
	
	ArtistFilter af = new ArtistFilter();
	af.setName("mono");
	for (ArtistResult ar : q.getArtists(af)) {
	    print(ar);
	}

	Release r;
	r = q.getReleaseById("1cf0f65a-f40b-4fba-a83f-559da2c86310");
	print(r);
    }

    private static void print(Artist a) {
	System.out.println("ID: " + a.getId());
	System.out.println("Type: " + a.getType());
	System.out.println("Name: " + a.getName());
	System.out.println("Sort Name: " + a.getSortName());
    }

    private static void print(ArtistResult ar) {
	System.out.println("Score: " + ar.getScore());
	print(ar.getArtist());
    }

    private static void print(Release r) {
	System.out.println("ID: " + r.getId());
	for (String type : r.getTypes()) {
	    System.out.println("Type: " + type);
	}
	System.out.println("Title: " + r.getTitle());
    }
}
