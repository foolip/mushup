package org.musicbrainz.test;

import org.musicbrainz.model.*;
import org.musicbrainz.neo.*;

import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.sql.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {
	System.out.println("Starting neo service");
	NeoService neo = new EmbeddedNeo("neostore");
	System.out.println("Starting index service");
	IndexService indexService = new NeoIndexService(neo);
	Transaction tx;
	ArtistFactory af;
	tx = neo.beginTx();
	try {
	    af = new NeoArtistFactory(neo, indexService);
	    tx.success();
	} finally {
	    tx.finish();
	}

	try {
	    Class.forName("org.postgresql.Driver");
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	String url = "jdbc:postgresql:musicbrainz?user=musicbrainz&password=musicbrainz";


	try {
	    Connection conn = DriverManager.getConnection(url);
	    Statement st = conn.createStatement();
	    st.setFetchSize(50);
	    ResultSet rs = st.executeQuery("SELECT * FROM artist LIMIT 0");
	    int idIndex = rs.findColumn("id");
	    int mbidIndex = rs.findColumn("gid");
	    int typeIndex = rs.findColumn("type");
	    int nameIndex = rs.findColumn("name");
	    int sortNameIndex = rs.findColumn("sortname");
	    int disambIndex = rs.findColumn("resolution");
	    while (rs.next()) {
		tx = neo.beginTx();
		try {
		    System.out.println("Processing " + rs.getString(nameIndex) + "...");
		    try {
			UUID mbid = UUID.fromString(rs.getString(mbidIndex));
			Artist artist = af.createArtist();
			artist.setId(mbid);
			int type = rs.getInt(typeIndex);
			switch (type) {
			case 1:
			    artist.setType(Artist.Type.PERSON);
			    break;
			case 2:
			    artist.setType(Artist.Type.GROUP);
			    break;
			default:
			    artist.setType(Artist.Type.UNKNOWN);
			    break;
			}
			artist.setName(rs.getString(nameIndex));
			artist.setSortName(rs.getString(sortNameIndex));
			String disamb = rs.getString(disambIndex);
			if (disamb != null && !disamb.equals(""))
			    artist.setDisambiguation(disamb);
		    } catch (IllegalArgumentException e) {
			e.printStackTrace();
		    }
		    tx.success();
		} finally {
		    tx.finish();
		}
	    }
	    rs.close();
	    st.close();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	System.out.println("Shutting down index service");
	indexService.shutdown();

	System.out.println("Shutting down neo service");
	neo.shutdown();
    }
}
