package org.musicbrainz;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.musicbrainz.model.*;
import org.musicbrainz.neo.*;

import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.io.File;
import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class NeoTest
extends TestCase
{
	private String neodir;
	private NeoService neo;
	private IndexService indexService;

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public NeoTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( NeoTest.class );
	}

	protected void setUp() {
		this.neodir = System.getProperty("java.io.tmpdir") + File.separator + "neostore";
		this.neo = new EmbeddedNeo(this.neodir);
		this.indexService = new NeoIndexService(neo);
	}

	private void deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
		dir.delete();
	}

	protected void tearDown() {
		this.indexService.shutdown();
		this.neo.shutdown();
		deleteDir(new File(this.neodir));
	}

	public void testArtist()
	{
		Transaction tx = neo.beginTx();
		try {
			ArtistFactory af = new NeoArtistFactory(this.neo, this.indexService);
			Artist a = af.createArtist();
			UUID uuid = UUID.randomUUID();
			a.setId(uuid);
			assertEquals("UUID", uuid, a.getId());
			a.setName("The Beatles");
			assertEquals("Name", "The Beatles", a.getName());
			a.setSortName("Beatles, The");
			assertEquals("Sort Name", "Beatles, The", a.getSortName());
			assertEquals("Type", Artist.Type.UNKNOWN, a.getType());
			a.setType(Artist.Type.PERSON);
			assertEquals("Type", Artist.Type.PERSON, a.getType());
			a.setType(Artist.Type.GROUP);
			assertEquals("Type", Artist.Type.GROUP, a.getType());
			a.setType(Artist.Type.UNKNOWN);
			assertEquals("Type", Artist.Type.UNKNOWN, a.getType());
			tx.success();
		} finally {
			tx.finish();
		}
	}
	
	public void testArtistAlias()
	{
		Transaction tx = neo.beginTx();
		try {
			ArtistFactory af = new NeoArtistFactory(this.neo, this.indexService);
			ArtistAliasFactory aaf = new NeoArtistAliasFactory(this.neo, this.indexService);
			Artist a = af.createArtist();
			UUID uuid = UUID.randomUUID();
			a.setId(uuid);
			a.setName("Foo Bar");
			ArtistAlias aa = aaf.createArtistAlias();
			aa.setName("foobar");
			a.addAlias(aa);
			aa = aaf.createArtistAlias();
			aa.setName("FOOBAR");
			a.addAlias(aa);
			boolean found_foobar = false;
			boolean found_FOOBAR = false;
			for (ArtistAlias alias : a.getAliases()) {
				if (alias.getName().equals("foobar"))
					found_foobar = true;
				else if (alias.getName().equals("FOOBAR"))
					found_FOOBAR = true;
			}
			assertTrue(found_foobar);
			assertTrue(found_FOOBAR);
			tx.success();
		} finally {
			tx.finish();
		}
	}
}
