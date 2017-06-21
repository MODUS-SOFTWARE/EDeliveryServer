package gr.modus.edeliveryserver.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatus;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;

import gr.modus.edeliveryclient.EdeliveryClientTest;

public class DocumentSendDBTest {
	private static final Logger log = Logger.getLogger(EdeliveryClientTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	
	DocumentsSend docSend = new DocumentsSend();
	
	DocumentSendHandlerDB dhan;
	ConnectionWrapper connWrapper ; 
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
        
        //ConstantsDB.elds = ds;
        
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
        docStatus.setId(0);docStatus.setStatus(""+DocumentStatuses.QUEUED.getValue());
        docSend.setId(7);
        
        docSend.setDocumentStatus(docStatus);
        EdeliveryDatasource eds = new EdeliveryDatasource(); 
        eds.setEdeliveryDatasource(ds);

        connWrapper = new ConnectionWrapper(eds);
        dhan = new DocumentSendHandlerDB(connWrapper);
        
        
	}
	
	
	
	@Test
	public void insertSend() throws SQLException{
		Connection conn=null;
		boolean closeConnection=false;
		try{
			if(conn==null){
				conn = this.connWrapper.getConnection();
				closeConnection=true;
			}
			dhan.insert(docSend,conn);
		}
		finally{
			if(conn!=null && closeConnection){
				conn.close();
			}
		}
	}
	
	
	
	public void updateStatus() throws SQLException{
		Connection conn = null;
		try{
			conn = this.connWrapper.getConnection();
			docSend.setDocumentStatus(new DocumentStatus(DocumentStatuses.SEND.getValue()));
			dhan.updateStatus(docSend, conn);
			log.info("end updateStatus");
		}
		finally{
			if(conn!=null){
				conn.close();
			}
		}
	}
	
}
