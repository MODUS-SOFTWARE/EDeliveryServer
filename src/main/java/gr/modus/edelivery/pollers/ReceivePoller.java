package gr.modus.edelivery.pollers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.edelivery.edeliveryserver.business.EdeliveryBS;
import com.edelivery.edeliveryserver.configuration.EdeliverySettings;
 


@Singleton
@Startup
@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
public class ReceivePoller {

	private static final Logger LOG = Logger.getLogger(SendPoller.class.getName());
	DataSource dataSource;
	private Boolean poll;
	
	 

	@Inject
	EdeliverySettings settings;

	@Inject
	EdeliveryBS edeliveryUtils;

	

	
	
	public ReceivePoller(){
		
	}
	
	@PostConstruct
	private void construct()  {
		init();
	}

	public void init() {
		LOG.log(Level.INFO, "Initializing ReceivePoller Poller.");
		try {
			
			// InitialContext context = new InitialContext();

			poll = true;
			start();
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, "Error happen. Poller probably is not running.  ", ex);
			stop();
		}
	}

	@PreDestroy
	private void destroy() {
		poll = false;

	}

	public void stop() {
		this.poll = false;

	}

	private void start() throws Exception {
		LOG.log(Level.INFO, "Indexing poller started.");

		long loopall = 1;
		long loopnotif = 1;
		long loopindex = 1;
		long loopupdateStatus = 1;
		long loopindexedDocs = 1;

		try {

			while (poll) {
				action();
			}
		} finally {

		}
	}

	public void action() {
		try {

			if (this.edeliveryUtils == null) {
				this.edeliveryUtils = new EdeliveryBS();
			}
			//this.edeliveryUtils.receiveSBD(msg2Get, conn); 
			this.edeliveryUtils.receiveNextMessage(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			LOG.log(Level.INFO, null, ex);
		}
	}

}
