package gr.modus.edeliveryclient;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import com.edelivery.edeliveryserver.business.EdeliveryBS;
import com.edelivery.edeliveryserver.business.SendMessageBS;
import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.BSDHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.google.gson.Gson;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;
import com.modus.edeliveryserver.papyros.servers.DocumentServerClient;

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
		docClient = new DocumentServerClient();
		edelBS = new EdeliveryBS( connWrapper,  docSendHandler, docClient,eDeliveryServerConfiguration,bsdHandler);
			
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

}
