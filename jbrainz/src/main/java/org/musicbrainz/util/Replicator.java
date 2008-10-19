package org.musicbrainz.util;

import org.hibernate.*;
import org.musicbrainz.model.*;
import org.musicbrainz.hibernate.*;
import org.musicbrainz.neo.*;
import org.neo4j.api.core.*;
import org.neo4j.api.core.Transaction;
import org.neo4j.util.index.*;

import java.util.*;

public class Replicator {
	public static void main(String[] args) {
		/*
		System.out.println("Starting neo service");
		NeoService neo = new EmbeddedNeo("neostore");
		System.out.println("Starting index service");
		IndexService indexService = new NeoIndexService(neo);
		Transaction tx;
		ArtistFactory artistFactory;
		ArtistAliasFactory artistAliasFactory;
		ReleaseFactory releaseFactory;
		TrackFactory trackFactory;
		tx = neo.beginTx();
		try {
		    artistFactory = new NeoArtistFactory(neo, indexService);
		    artistAliasFactory = new NeoArtistAliasFactory(neo, indexService);
		    releaseFactory = new NeoReleaseFactory(neo, indexService);
		    trackFactory = new NeoTrackFactory(neo, indexService);
		    tx.success();
		} finally {
		    tx.finish();
		}
		*/

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Query q = session.createQuery("from HibernateArtist order by row");
		q.setFetchSize(100).setFirstResult(0).setMaxResults(100);
		Iterator<?> artists = q.iterate();
		int count = 0;
		while (artists.hasNext() && count++ < 1000) {
			// copy to neo
			//tx = neo.beginTx();
			try {
				Artist sourceArtist = (Artist)artists.next();
				System.out.println("artist " + sourceArtist.getName());
				/*
				Artist targetArtist = artistFactory.createArtist();
				targetArtist.setId(sourceArtist.getId());
				if (sourceArtist.getType() != Artist.Type.UNKNOWN)
					targetArtist.setType(sourceArtist.getType());
				targetArtist.setName(sourceArtist.getName());
				targetArtist.setSortName(sourceArtist.getSortName());
				if (sourceArtist.getDisambiguation() != null)
					targetArtist.setDisambiguation(sourceArtist.getDisambiguation());
					*/
				/*
				for (ArtistAlias sourceAlias : sourceArtist.getAliases()) {
					System.out.println("\talias " + " " + sourceAlias.getName());
					ArtistAlias targetAlias = artistAliasFactory.createArtistAlias();
					targetAlias.setName(sourceAlias.getName());
					targetArtist.addAlias(targetAlias);
				}
				for (Release sourceRelease : sourceArtist.getReleases()) {
					System.out.println("\trelease " + sourceRelease.getId() + " " + sourceRelease.getTitle());
					Release targetRelease = releaseFactory.createRelease();
					targetRelease.setId(sourceRelease.getId());
					targetRelease.setTitle(sourceRelease.getTitle());
					for (Map.Entry<Integer,? extends Track> track : sourceRelease.getTracks().entrySet()) {
						int trackNumber = track.getKey();
						Track sourceTrack = track.getValue();
						System.out.println("\t\ttrack " + sourceTrack.getId() + " #" + trackNumber + " " + sourceTrack.getTitle());
						Track targetTrack = trackFactory.createTrack();
						targetTrack.setTitle(sourceTrack.getTitle());
						targetRelease.addTrack(targetTrack, trackNumber);
					}
					targetArtist.addRelease(targetRelease);
				}
				*/
				//tx.success();
			} finally {
				//tx.finish();
			}
		}

		session.getTransaction().commit();

		HibernateUtil.getSessionFactory().close();
		/*
		System.out.println("Shutting down index service");
		indexService.shutdown();
		System.out.println("Shutting down neo service");
		neo.shutdown();\
		*/
	}
}
