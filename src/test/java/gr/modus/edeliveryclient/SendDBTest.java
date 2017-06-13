package gr.modus.edeliveryclient;

import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

public class SendDBTest {
	
	private static final Logger log = Logger.getLogger(SendDBTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	
	
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
	}

	
	@Test
	public void testinsertSendMessage(){
		
	} 

}
