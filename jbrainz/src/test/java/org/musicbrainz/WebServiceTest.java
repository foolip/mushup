package org.musicbrainz.webservice;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.musicbrainz.model.*;
import org.musicbrainz.webservice.*;

import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class WebServiceTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public WebServiceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( WebServiceTest.class );
    }

    public void testGetAristById() throws WebServiceException
    {
	UUID id = UUID.fromString("b10bbbfc-cf9e-42e0-be17-e2c3e1d2600d");
	Artist a = new Query().getArtistById(id);
	assertEquals("MBID", id, a.getId());
	assertEquals("Type", Artist.Type.GROUP, a.getType());
	assertEquals("Name", "The Beatles", a.getName());
	assertEquals("Sort Name", "Beatles, The", a.getSortName());
    }

    public void testGetArists() throws WebServiceException
    {
	Query q = new Query();
	ArtistFilter af = new ArtistFilter();
	af.setName("beatles");
	for (ArtistResult ar : q.getArtists(af)) {
	    Artist a = ar.getArtist();
	    if (a.getId().equals(UUID.fromString("b10bbbfc-cf9e-42e0-be17-e2c3e1d2600d"))) {
		return;
	    }
	}
	fail("The Beatles were not found");
    }
}
