package gr.modus.edelivery.pollers;


import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;





@Singleton
@Startup
@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
public class EvidencePoller {
	/**
	 *
	 * @author chomenidis
	 */

	private static final Logger LOG = Logger.getLogger(EvidencePoller.class.getName());
	DataSource dataSource;
	private Boolean poll;
	EdeliverySettings settings;
	EdeliveryBS edeliveryUtils;
	EdeliveryDatasource edatasource;
	DocumentSendHandlerDB   docSendHandler;
	ConnectionWrapper connWrapper;
	
	public EvidencePoller(){}
	
	
	@Inject
	public EvidencePoller(EdeliverySettings settings,EdeliveryBS edeliveryUtils,EdeliveryDatasource edatasource,DocumentSendHandlerDB   docSendHandler
			,ConnectionWrapper connWrapper
			){
		this.settings=settings;
		this.edeliveryUtils = edeliveryUtils;
		this.edatasource =edatasource ;
		this.docSendHandler = docSendHandler;
		this.connWrapper = connWrapper;
		
	}
	
	@PostConstruct
	private void construct()  {
		init();
	}

	public void init() {
		LOG.log(Level.INFO, "Initializing Evidence Poller.");
		try {

			// InitialContext context = new InitialContext();

			poll = false;
			start();
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, "Error happen. Evidence Poller probably is not running.  ", ex);
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
	
	
	
	
	@TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
	public void action() {
		//Connection conn = null;
		try {

			
			//conn = this.connWrapper.getConnection();
			
			
			if (this.edeliveryUtils == null) {
				this.edeliveryUtils = new EdeliveryBS();
			}
			//this.edeliveryUtils.sendSBD(); TODO
			DocumentsSend docSend = docSendHandler.selectNextById(DocumentStatuses.SEND,null);
			if(docSend!=null){
				this.edeliveryUtils.receiveEvidenceAp(docSend.getMessageUniqueId(), null);
				LOG.log(Level.INFO,"");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		finally{
			/*if(conn !=null ){
				try{
					conn.close();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}*/
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			LOG.log(Level.INFO, null, ex);
		}
	}

}
