package gr.modus.edeliveryserver.db.business;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.edelivery.edeliveryserver.business.SendMessageBS;
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatus;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;
import com.google.gson.Gson;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;

import gr.modus.edeliveryserver.db.MessageSendHandlerTest;


public class SendMessageBSTest {
	private static final Logger log = Logger.getLogger(SendMessageBSTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	MessageSendHandlerDB messageHandler; 
	MessageSendToAp messageSendToAp = new MessageSendToAp();
	DocumentsSend docSend = new DocumentsSend();
	SendMessageBS bs;
	ConnectionWrapper connWrapper;
	DocumentSendHandlerDB   docSendHandler;
	
	@Before
	public void setUp() throws Exception {
		log.info("MSSQL Tests started");
    	//BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        ds.setUrl("jdbc:sqlserver://testsrv;databaseName=edeliveryserver;instanceName=SQLEXPRESS");
        ds.setUsername("papyrosm3");
        ds.setPassword("papyrosm3");
        ds.setMaxTotal(10);
        ds.setMaxIdle(5);
        ds.setInitialSize(5);
        ds.setValidationQuery("SELECT 1");
        
        
        String actualDocumentFilepath = "F:/testDocument/pdf/d_ok_imi.pdf";
        docSend.setActualDocumentFilepath(actualDocumentFilepath);
        docSend.setDocId(55132);
        docSend.setDocumentComments("comments");
        UUID uniqueId = UUID.randomUUID();
        docSend.setMessageId(55132);
        docSend.setMessageUniqueId(uniqueId.toString());
        docSend.setDocumentTitle("title");
        docSend.setDocumentType("type");
        docSend.setDocumentOrganizationEtiquette("test etiquette");
        DocumentStatus docStatus = new DocumentStatus();
        docStatus.setId(1);
        docSend.setDocumentStatus(docStatus);
        
        
        messageSendToAp.setMessageUniqueId("c97f125e-d5d1-4407-bd00-ee309628e58a");
        EdeliveryDatasource eds = new EdeliveryDatasource(); 
        eds.setEdeliveryDatasource(ds);
        connWrapper = new ConnectionWrapper(eds);
        messageHandler = new MessageSendHandlerDB(connWrapper);
        docSendHandler = new DocumentSendHandlerDB(connWrapper);
        bs = new SendMessageBS( docSendHandler , messageHandler,connWrapper);
        
        
	}
	
	
	
	
	public void insertSend() throws SQLException{
		Connection conn= null;
		try{
			conn = this.connWrapper.getConnection();
			bs.insertMessage2Send(docSend,conn);
		}
		finally{
			if(conn != null){
				conn.close();
			}
		}
	}
	
	
	
	public void selectNext() throws SQLException{
		Connection conn = null;
		try{
			conn = this.connWrapper.getConnection();
			DocumentsSend docSend = bs.selectNextById(DocumentStatuses.QUEUED,conn);
			System.out.println(new Gson().toJson(docSend));
		}
		finally{
			if(conn != null){
				conn.close();
			}
		}
		
	}
	
	
}
