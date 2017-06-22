package gr.modus.edeliveryserver.db.business;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.edelivery.edeliveryserver.business.SendMessageBS;

import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatus;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;
import com.google.gson.Gson;


import gr.modus.edeliveryserver.db.MessageSendHandlerTest;


public class SendMessageBSTest {
	private static final Logger log = Logger.getLogger(SendMessageBSTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	MessageSendHandlerDB messageHandler; 
	MessageSendToAp messageSendToAp = new MessageSendToAp();
	DocumentsSend docSend = new DocumentsSend();
	SendMessageBS bs;
	
	DocumentSendHandlerDB   docSendHandler;
	
	@Before
	public void setUp() throws Exception {
		log.info("MSSQL Tests started");
    	//BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        ds.setUrl("jdbc:sqlserver://testsrv;databaseName=papyrosm3_test;instanceName=SQLEXPRESS");
        ds.setUsername("papyrosm3");
        ds.setPassword("papyrosm3");
        ds.setMaxTotal(10);
        ds.setMaxIdle(5);
        ds.setInitialSize(5);
        ds.setValidationQuery("SELECT 1");
        
        ConstantsDB.setElds(ds);
        String actualDocumentFilepath = "F:/testDocument/pdf/d_ok_imi.pdf";
        docSend.setActualDocumentFilepath(actualDocumentFilepath);
        docSend.setDocId(55161);
        docSend.setDocumentComments("comments");
        UUID uniqueId = UUID.randomUUID();
        docSend.setMessageId(55161);
        docSend.setMessageUniqueId(uniqueId.toString());
        docSend.setDocumentTitle("Java Test");
        docSend.setDocumentType("type");
        docSend.setDocumentOrganizationEtiquette("1234");
        DocumentStatus docStatus = new DocumentStatus("0");
        docStatus.setId(1);
        docSend.setDocumentStatus(docStatus);
        
        
        messageSendToAp.setMessageUniqueId("c97f125e-d5d1-4407-bd00-ee309628e58b");
        messageHandler = new MessageSendHandlerDB();
        docSendHandler = new DocumentSendHandlerDB();
        bs = new SendMessageBS( docSendHandler , messageHandler);
        
        
	}
	
	
	
	@Test
	public void insertSend() throws SQLException{
		Connection conn= null;
		try{
			conn =ConstantsDB.getElds().getConnection();
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
			conn =ConstantsDB.getElds().getConnection();
			DocumentsSend docSend = bs.selectNextById(DocumentStatuses.QUEUED,conn);
			//System.out.println(new Gson().toJson(docSend));
		}
		finally{
			if(conn != null){
				conn.close();
			}
		}
		
	}
	
	
}
