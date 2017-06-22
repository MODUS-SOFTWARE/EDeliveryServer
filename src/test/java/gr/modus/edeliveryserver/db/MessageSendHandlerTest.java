package gr.modus.edeliveryserver.db;

import java.sql.SQLException;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;



public class MessageSendHandlerTest {
	private static final Logger log = Logger.getLogger(MessageSendHandlerTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	
	MessageSendToAp messageSendToAp = new MessageSendToAp();
	
	MessageSendHandlerDB handler;
	
	
	
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
        messageSendToAp.setMessageUniqueId("a5e3caf0-c0f3-441c-8a43-2df530c5b42e");
        handler = new MessageSendHandlerDB();
        
      
		
	}
	
	
	
	@Test
	public void insertSend() throws SQLException{
		handler.insert(messageSendToAp);
	}
}
