import org.musicbrainz.model.Artist;
import org.musicbrainz.webservice.Query;

public class MBTest {
    public static void main(String args[]) {
	Query q = new Query();
	try {
	    Artist a = q.getArtistById("c0b2500e-0cef-4130-869d-732b23ed9df5");
	    System.out.println("ID: " + a.getId());
	    System.out.println("Name: " + a.getName());
	    System.out.println("Sort Name: " + a.getSortName());
	} catch (Exception e) {
	}
    }
}
