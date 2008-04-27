import org.neo4j.api.core.*;

/**
 * Example class that constructs a simple node space with message attributes and then prints them.
 */
public class NeoTest {

    public enum MyRelationshipTypes implements RelationshipType {
        KNOWS
    }

    public static void main(String[] args) {
        final NeoService neo = new EmbeddedNeo("neobase");

	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		public void run() {
		    System.out.println("Shutting down...");
		    neo.shutdown();
		}
	    }));

	neo.enableRemoteShell();

	// It appears that some transaction must be done for the shell
	// to work, so we get the reference node.
	Transaction tx = Transaction.begin();
	try {
	    Node referenceNode = neo.getReferenceNode();
	    tx.success();
	} finally {
	    tx.finish();
	}

	while(true) {
	    try{
		Thread.sleep(100);
	    } catch (InterruptedException e) {
		// ignore 
	    }
	}
    }
}
