package org.foolip.mushup;

import javax.servlet.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.neo4j.api.core.*;
import org.neo4j.util.index.*;

public final class ContextListener implements ServletContextListener {

    private final Log log = LogFactory.getLog(this.getClass());

    public void contextInitialized(ServletContextEvent event) {

	log.debug("Starting neo service");
	NeoService neo = new EmbeddedNeo("/tmp/neostore");
	neo.enableRemoteShell();
	event.getServletContext().setAttribute("neoService", neo);

	log.debug("Starting index service");
	IndexService indexService = new NeoIndexService(neo);
	event.getServletContext().setAttribute("indexService", indexService);

	Transaction tx = Transaction.begin();
	try {
	    Replicator repl = new Replicator(neo, indexService);
	    event.getServletContext().setAttribute("replicator", repl);
	    tx.success();
	} finally {
	    tx.finish();
	}
    }

    public void contextDestroyed(ServletContextEvent event) {

	log.debug("Shutting down index service");
	IndexService indexService =
	    (IndexService)event.getServletContext().getAttribute("indexService");
	if (indexService != null)
	    indexService.shutdown();

	log.debug("Shutting down neo service");
	NeoService neo = (NeoService)event.getServletContext().getAttribute("neoService");
	if (neo != null)
	    neo.shutdown();
    }
}
