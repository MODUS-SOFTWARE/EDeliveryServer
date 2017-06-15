package gr.modus.edeliveryclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.edelivery.edeliveryserver.business.EdeliveryBS;
import com.edelivery.edeliveryserver.business.SendMessageBS;
import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.BSDHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentReceivedHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;
import com.google.gson.Gson;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;
import com.modus.edeliveryserver.papyros.servers.DocumentServerClient;

import gr.modus.edelivery.papyros.servers.exceptions.DSException;

public class EdeliveryClientTest {
	
	private static final Logger log = Logger.getLogger(EdeliveryClientTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();
	
	DocumentsSend docSend;
	SendMessageBS bs;
	ConnectionWrapper connWrapper;
	DocumentSendHandlerDB   docSendHandler;
	MessageSendHandlerDB messageHandler; 
	DocumentServerClient docClient;
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	EdeliveryBS edelBS;
	BSDHandlerDB bsdHandler;
	
	MessageReceivedFromAp msg2Get;
	DocumentReceivedHandlerDB docReceivedHandler;
	
	@Before
	public void setUp() throws Exception {
		log.info("MSSQL Tests started");
        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        ds.setUrl("jdbc:sqlserver://testsrv;databaseName=edeliveryserver;instanceName=SQLEXPRESS");
        ds.setUsername("papyrosm3");
        ds.setPassword("papyrosm3");
        ds.setMaxTotal(10);
        ds.setMaxIdle(5);
        ds.setInitialSize(5);
        ds.setValidationQuery("SELECT 1");
        
        EdeliveryDatasource eds = new EdeliveryDatasource(); 
        eds.setEdeliveryDatasource(ds);
        
        connWrapper = new ConnectionWrapper(eds);
        messageHandler = new MessageSendHandlerDB(connWrapper);
        docSendHandler = new DocumentSendHandlerDB(connWrapper);
        bs = new SendMessageBS(connWrapper, docSendHandler , messageHandler);
        bsdHandler = new BSDHandlerDB(connWrapper);
        
        docSend = bs.selectNextById();
		System.out.println(new Gson().toJson(docSend));
		
		eDeliveryServerConfiguration = new EDeliveryServerConfiguration();
		eDeliveryServerConfiguration.load();
		docClient = new DocumentServerClient(eDeliveryServerConfiguration);
		docReceivedHandler = new DocumentReceivedHandlerDB(connWrapper);
		edelBS = new EdeliveryBS( connWrapper,  docSendHandler, docClient,eDeliveryServerConfiguration,bsdHandler,docReceivedHandler);

		msg2Get = new MessageReceivedFromAp();
		msg2Get.setId(1);
		msg2Get.setMessageUniqueId("9933_test1-20170614112334473@local_delivery");
		
	}

	
	@Test
	public void testSendMessage(){
		try {
			edelBS.sendSBD(docSend);
			System.out.println("end");
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 

	
	@Test
	public void testReceiveMessage() throws XPathExpressionException{
		try {
			edelBS.receiveSBD(msg2Get);
			System.out.println("end");
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
}
