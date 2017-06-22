package gr.modus.edeliveryclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
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
import com.edelivery.edeliveryserver.business.SecurityBs;
import com.edelivery.edeliveryserver.business.SendMessageBS;
import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.BSDHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentReceivedHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.EvidenceHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageReceivedFromApHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;
import com.google.gson.Gson;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;
import com.modus.edeliveryserver.papyros.servers.DocumentServerClient;

import gr.modus.edelivery.papyros.servers.exceptions.DSException;
import gr.modus.edelivery.papyros.servers.exceptions.InvalidInputException;

public class EdeliveryClientTest {

	private static final Logger log = Logger.getLogger(EdeliveryClientTest.class.getName());
	private BasicDataSource ds = new BasicDataSource();

	DocumentsSend docSend;
	SendMessageBS bs;
	ConnectionWrapper connWrapper;
	DocumentSendHandlerDB docSendHandler;
	MessageSendHandlerDB messageHandler;
	DocumentServerClient docClient;
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	EdeliveryBS edelBS;
	BSDHandlerDB bsdHandler;

	MessageReceivedFromAp msg2Get;
	DocumentReceivedHandlerDB docReceivedHandler;
	EvidenceHandlerDB eviHandler;
	MessageReceivedFromApHandlerDB messageReceivedHandler;
	
	@Before
	public void setUp() throws Exception {
		log.info("MSSQL Tests started");
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUrl("jdbc:sqlserver://testsrv;databaseName=papyrosm3_test;instanceName=SQLEXPRESS");
		ds.setUsername("papyrosm3");
		ds.setPassword("papyrosm3");
		ds.setMaxTotal(10);
		ds.setMaxIdle(5);
		ds.setInitialSize(5);
		ds.setValidationQuery("SELECT 1");
		eDeliveryServerConfiguration = new EDeliveryServerConfiguration();
		eDeliveryServerConfiguration.load();
		EdeliveryDatasource eds = new EdeliveryDatasource();
		eds.setEdeliveryDatasource(ds);

		 ConstantsDB.setElds(ds); 

		connWrapper = new ConnectionWrapper(eds);
		messageHandler = new MessageSendHandlerDB(connWrapper);
		docSendHandler = new DocumentSendHandlerDB(connWrapper);
		bs = new SendMessageBS(docSendHandler, messageHandler, connWrapper);
		bsdHandler = new BSDHandlerDB(connWrapper, eDeliveryServerConfiguration);
		try (Connection conn = connWrapper.getConnection()) {
			docSend = bs.selectNextById(DocumentStatuses.QUEUED, conn);
		}
		//System.out.println(new Gson().toJson(docSend));

		
		docClient = new DocumentServerClient(eDeliveryServerConfiguration);
		docReceivedHandler = new DocumentReceivedHandlerDB(connWrapper);
		eviHandler = new EvidenceHandlerDB();
		messageReceivedHandler = new MessageReceivedFromApHandlerDB();
		edelBS = new EdeliveryBS(connWrapper, docSendHandler, docClient, eDeliveryServerConfiguration, bsdHandler,
				docReceivedHandler,eviHandler,messageReceivedHandler);

		msg2Get = new MessageReceivedFromAp();
		msg2Get.setId(1);
		msg2Get.setMessageUniqueId("9933_test1-20170619155009133@local_delivery");
		// SecurityBs.doTrustToCertificates();
	}

	
	public void testSendMessage() {

		try (Connection conn = this.connWrapper.getConnection()) {
			edelBS.sendSBD(docSend, conn);
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void testGetEviApMessage() {

		try  {
			edelBS.receiveEvidenceAp("9933_test1-20170619155009133@local_delivery", null); 
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testReceiveMessage() throws XPathExpressionException {
		try (Connection conn = this.connWrapper.getConnection()) {
			edelBS.receiveSBD(msg2Get, conn);
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
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test
	public void testReceiveNextMessage() throws XPathExpressionException {
		try (Connection conn = ConstantsDB.getElds().getConnection()) {
			edelBS.receiveNextMessage(conn);
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
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

}
