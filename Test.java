import org.musicbrainz.model.*;
import org.musicbrainz.neo.*;

import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

import java.sql.*;
import java.util.*;

public class Test {
	private final static int BATCH_SIZE = 4096;
	
	private static void replicateArtists(Connection conn, NeoService neo) throws SQLException {
		ArtistFactory af;
		Transaction tx = neo.beginTx();
		try {
			af = new NeoArtistFactory(neo, null);
			tx.success();
		} finally {
			tx.finish();
		}
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM artist LIMIT 0");
		int idIndex = rs.findColumn("id");
		int mbidIndex = rs.findColumn("gid");
		int typeIndex = rs.findColumn("type");
		int nameIndex = rs.findColumn("name");
		int sortNameIndex = rs.findColumn("sortname");
		int disambIndex = rs.findColumn("resolution");
		int beginDateIndex = rs.findColumn("begindate");
		int endDateIndex = rs.findColumn("enddate");
		rs.close();

		int count = 0;
		boolean more = true;
		for (int batch = 0; more; batch++) {
			rs = st.executeQuery("SELECT * FROM artist OFFSET " + batch*BATCH_SIZE + " LIMIT " + BATCH_SIZE);
			more = false;
			
			tx = neo.beginTx();
			try {
				while (rs.next()) {
					more = (rs.getRow() == BATCH_SIZE);
					
				    Artist artist = af.createArtist();
					artist.setId(UUID.fromString(rs.getString(mbidIndex)));
					int type = rs.getInt(typeIndex);
					switch (type) {
					case 1:
					    artist.setType(Artist.Type.PERSON);
					    break;
					case 2:
						artist.setType(Artist.Type.GROUP);
						break;
					}
					String name = rs.getString(nameIndex);
					artist.setName(name);
					String sortName = rs.getString(sortNameIndex);
					if (sortName != null && !sortName.equals(name))
						artist.setSortName(sortName);
					String disamb = rs.getString(disambIndex);
					if (disamb != null)
					    artist.setDisambiguation(disamb);
					String beginDate = rs.getString(beginDateIndex);
					if (beginDate != null)
						artist.setBeginDate(beginDate);
					String endDate = rs.getString(endDateIndex);
					if (endDate != null)
						artist.setEndDate(endDate);
					
					count++;
				}
				tx.success();
			} finally {
				tx.finish();
			}
			rs.close();
			System.out.println("Replicated " + count + " artists...");
		}
		st.close();
	}
	
	public static void main(String[] args) {
		System.out.println("Starting neo service");
		NeoService neo = new EmbeddedNeo("neostore");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:postgresql:musicbrainz?user=musicbrainz&password=musicbrainz";

		try {
			Connection conn = DriverManager.getConnection(url);
			replicateArtists(conn, neo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Shutting down neo service");
		neo.shutdown();
	}
}
