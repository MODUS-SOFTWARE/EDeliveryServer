package gr.modus.edeliveryserver.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;


import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.EvidenceHandlerDB;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatus;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.Evidence;


import gr.modus.edeliveryclient.EdeliveryClientTest;

public class EvidenceDBTest {
	private static final Logger log = Logger.getLogger(EdeliveryClientTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	
	Evidence evidenceObj = new Evidence();
	
	EvidenceHandlerDB evidenceHan;
	
 
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
        evidenceHan = new EvidenceHandlerDB();
        
        String actualDocumentFilepath = "F:/testDocument/pdf/d_ok_imi.pdf";
        evidenceObj.setActual_document_filepath(actualDocumentFilepath);
        evidenceObj.setDocId(55132);
        evidenceObj.setEventCode("DELIVERY");
        UUID uniqueId = UUID.randomUUID();
        evidenceObj.setEvidence_id(uniqueId.toString());
        evidenceObj.setEvidence_name("test name");
        evidenceObj.setEvidence_time("10-03-2017");
        UUID uniqueId2 = UUID.randomUUID();
        evidenceObj.setReference_document(uniqueId2.toString());
        
	}
	
	
	
	@Test
	public void insert() throws SQLException{
		Connection conn=null;
		boolean closeConnection=false;
		try{
			if(conn==null){
				
				closeConnection=true;
			}
			evidenceHan.insert(evidenceObj,null);
		}
		finally{
			if(conn!=null && closeConnection){
				conn.close();
			}
		}
	}
}
