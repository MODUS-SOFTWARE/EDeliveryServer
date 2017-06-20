package gr.modus.edeliveryserver.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;

import com.edelivery.edeliveryserver.db.entityhandlers.MessageReceivedFromApHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;
import com.google.gson.Gson;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;

public class MessageReceivedFromApHandlerDBTest {
	private static final Logger log = Logger.getLogger(MessageSendHandlerTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	
	List<MessageReceivedFromAp> messageReceivedFromApList = new ArrayList<MessageReceivedFromAp>();
	
	MessageReceivedFromApHandlerDB handler;
	ConnectionWrapper connWrapper;
	
	
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
        
        MessageReceivedFromAp msg1 = new MessageReceivedFromAp();
        msg1.setId(1);
        msg1.setMessageUniqueId("9933_test1-20170614112334473@local_delivery");
        
        MessageReceivedFromAp msg2 = new MessageReceivedFromAp();
        msg2.setId(2);
        msg2.setMessageUniqueId("9933_test1-20170614112322156@local_delivery");
        
        
        messageReceivedFromApList.add(msg1);
        messageReceivedFromApList.add(msg2);
        
        
        EdeliveryDatasource eds = new EdeliveryDatasource(); 
        eds.setEdeliveryDatasource(ds);
        connWrapper = new ConnectionWrapper(eds);
        handler = new MessageReceivedFromApHandlerDB(connWrapper);
        
        
	}
	
	
	
	
	public void selectMsgGet() throws SQLException{
		List<MessageReceivedFromAp>  msg2Get = handler.select(messageReceivedFromApList);
		System.out.println(new Gson().toJson(msg2Get));
	}
}
