package org.musicbrainz.test;

import org.hibernate.*;
import org.musicbrainz.model.*;
import org.musicbrainz.hibernate.*;
import org.musicbrainz.neo.*;
import org.neo4j.api.core.*;
import org.neo4j.api.core.Transaction;
import org.neo4j.util.index.*;

import java.util.*;

public class HibernateTest {
	public static void main(String[] args) {
		System.out.println("Starting neo service");
		NeoService neo = new EmbeddedNeo("neostore");
		System.out.println("Starting index service");
		IndexService indexService = new NeoIndexService(neo);
		Transaction tx;
		ArtistFactory artistFactory;
		ArtistAliasFactory artistAliasFactory;
		ReleaseFactory releaseFactory;
		tx = neo.beginTx();
		try {
		    artistFactory = new NeoArtistFactory(neo, indexService);
		    artistAliasFactory = new NeoArtistAliasFactory(neo, indexService);
		    releaseFactory = new NeoReleaseFactory(neo, indexService);
		    tx.success();
		} finally {
		    tx.finish();
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Iterator artists = session.createQuery("from HibernateArtist").iterate();
		int limit = 1000;
		while (artists.hasNext() && limit > 0) {
			// copy to neo
			tx = neo.beginTx();
			try {
				int batch = 128;
				while(artists.hasNext() && batch-- > 0 && limit-- > 0) {
					HibernateArtist sourceArtist = (HibernateArtist)artists.next();
					System.out.println("artist #" + sourceArtist.getRow() + " " + sourceArtist.getName());
					Artist targetArtist = artistFactory.createArtist();
					targetArtist.setId(sourceArtist.getId());
					targetArtist.setType(sourceArtist.getType());
					targetArtist.setName(sourceArtist.getName());
					targetArtist.setSortName(sourceArtist.getSortName());
					targetArtist.setDisambiguation(sourceArtist.getDisambiguation());
					for (HibernateArtistAlias sourceAlias : sourceArtist.getAliases()) {
						System.out.println("\talias #" + sourceAlias.getRow() + " " + sourceAlias.getName());
						ArtistAlias targetAlias = artistAliasFactory.createArtistAlias();
						targetAlias.setName(sourceAlias.getName());
						targetArtist.addAlias(targetAlias);
					}
					for (HibernateRelease sourceRelease : sourceArtist.getReleases()) {
						System.out.println("\trelease #" + sourceRelease.getRow() + " " + sourceRelease.getTitle());
						Release targetRelease = releaseFactory.createRelease();
						targetRelease.setId(sourceRelease.getId());
						targetRelease.setTitle(sourceRelease.getTitle());
						targetArtist.addRelease(targetRelease);
					}
				}
				tx.success();
			} finally {
				tx.finish();
			}
		}

		session.getTransaction().commit();

		HibernateUtil.getSessionFactory().close();
		
		System.out.println("Shutting down index service");
		indexService.shutdown();
		System.out.println("Shutting down neo service");
		neo.shutdown();
	}
}
