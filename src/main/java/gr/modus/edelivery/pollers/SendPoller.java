package gr.modus.edelivery.pollers;


import java.sql.Connection;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.edelivery.edeliveryserver.business.EdeliveryBS;
import com.edelivery.edeliveryserver.configuration.EdeliverySettings;
 
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import javax.enterprise.concurrent.ManagedExecutorService;





@Singleton
@Startup
@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
public class SendPoller {
	/**
	 *
	 * @author chomenidis
	 */

	private static final Logger LOG = Logger.getLogger(SendPoller.class.getName());
	@Resource
    ManagedExecutorService managedExecutor;
	private Boolean poll;
	EdeliverySettings settings;
	EdeliveryBS edeliveryUtils;
	private Future future;
	DocumentSendHandlerDB   docSendHandler;
 
	
	public SendPoller(){}
	
	
	@Inject
	public SendPoller(EdeliverySettings settings,EdeliveryBS edeliveryUtils,DocumentSendHandlerDB   docSendHandler
		 
			){
		this.settings=settings;
		this.edeliveryUtils = edeliveryUtils;
		
		this.docSendHandler = docSendHandler;
	 
		
	}
	
	@PostConstruct
	private void construct()  {
		init();
	}

	public void init() {
		LOG.log(Level.INFO, "Initializing Sender Poller.");
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
		if(future!=null){
    		future.cancel(true);
    	}

	}

	public void stop() {
		this.poll = false;

	}

	private void start() throws Exception {
		LOG.info("start poller");
    	future = managedExecutor.submit(() -> {
            try {
				_start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

		 
	}


	private void _start() throws Exception {
		

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
	
	
	
	
	@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
	public void action() {
		//Connection conn = null;
		try {

			
	
			
			
			if (this.edeliveryUtils == null) {
				this.edeliveryUtils = new EdeliveryBS();
			}
			//this.edeliveryUtils.sendSBD(); TODO
			DocumentsSend docSend = docSendHandler.selectNextById(DocumentStatuses.QUEUED,null);
			if(docSend!=null){
				this.edeliveryUtils.sendSBD(docSend,null);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		finally{
		
		}

		
		LOG.log(Level.INFO, "testing");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			LOG.log(Level.INFO, null, ex);
		}
	}

}
