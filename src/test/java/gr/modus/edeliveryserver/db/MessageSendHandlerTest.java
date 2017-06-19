package gr.modus.edeliveryserver.db;

import java.sql.SQLException;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;


public class MessageSendHandlerTest {
	private static final Logger log = Logger.getLogger(MessageSendHandlerTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	
	MessageSendToAp messageSendToAp = new MessageSendToAp();
	
	MessageSendHandlerDB handler;
	ConnectionWrapper connWrapper;
	
	
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
        
        messageSendToAp.setMessageUniqueId("c97f125e-d5d1-4407-bd00-ee309628e58a");
        EdeliveryDatasource eds = new EdeliveryDatasource(); 
        eds.setEdeliveryDatasource(ds);
        connWrapper = new ConnectionWrapper(eds);
        handler = new MessageSendHandlerDB(connWrapper);
        
        
	}
	
	
	
	
	public void insertSend() throws SQLException{
		handler.insert(messageSendToAp);
	}
}
