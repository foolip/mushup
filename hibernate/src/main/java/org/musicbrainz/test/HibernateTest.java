package org.musicbrainz.test;

import org.hibernate.*;
import org.musicbrainz.model.*;
import org.musicbrainz.hibernate.*;

import java.util.*;

public class HibernateTest {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Iterator artists = session.createQuery("from HibernateArtist").iterate();
		while (artists.hasNext()) {
			Artist a = (Artist)artists.next();
			System.out.println(a.getId());
			System.out.println(a.getName());
			System.out.println(a.getSortName());
			System.out.println(a.getDisambiguation());
		}

		session.getTransaction().commit();

		HibernateUtil.getSessionFactory().close();
	}
}
