package com.edelivery.edeliveryserver.business;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.BSDMessage;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modus.edeliveryclient.EDeliveryClient;
import com.modus.edeliveryclient.EDeliveryClientImplementation;
import com.modus.edeliveryclient.consumer.SbdConsumer;
import com.modus.edeliveryclient.consumer.SmpParticipantConsumer;
import com.modus.edeliveryclient.models.Authorization;
import com.modus.edeliveryclient.models.SBDParams;
import com.modus.edeliveryclient.serialize.Serializer;
//import com.modus.edeliveryclient.serializer.JacksonSerializer;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;
import com.modus.edeliveryserver.papyros.servers.DocumentServerClient;

import gr.modus.edelivery.adapter.messages.MessageParams;
import gr.modus.edelivery.papyros.servers.exceptions.DSException;
import gr.modus.edelivery.pollers.SendPoller;

@RequestScoped
public class EdeliveryBS {

	private static final Logger LOG = Logger.getLogger(EdeliveryBS.class.getName());

	ConnectionWrapper conWrapper;
	DocumentSendHandlerDB docSendHd;
	DocumentServerClient docClient;
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	AsyncHttpClient httpClient;
	Serializer serializer;
	String basepath = "http://192.168.20.10:8080/APREST";
	String user = "sp1";
	String password = "sp1";
	Authorization auth;
	EDeliveryClient deliveryClient;

	public EdeliveryBS() {
	}

	@Inject
	public EdeliveryBS(ConnectionWrapper conWrapper, DocumentSendHandlerDB docSendHd, DocumentServerClient docClient,
			EDeliveryServerConfiguration eDeliveryServerConfiguration) {
		this.conWrapper = conWrapper;
		this.docSendHd = docSendHd;
		this.docClient = docClient;
		this.eDeliveryServerConfiguration = eDeliveryServerConfiguration;
		this.httpClient = new DefaultAsyncHttpClient();
		this.serializer = new com.modus.edeliveryserver.serializer.JacksonSerializer(new ObjectMapper());
		basepath = eDeliveryServerConfiguration.getConnector();
		auth = new Authorization(user, password);
		deliveryClient = new EDeliveryClientImplementation(httpClient, serializer,
				new SmpParticipantConsumer(httpClient, serializer, basepath),
				new SbdConsumer(httpClient, serializer, basepath));
	}

	/**
	 * Επιστρέφει string που αναπαριστά ένα SBD, που αποτελείται από το SBDH και
	 * το Payload
	 * 
	 * @param messageId
	 * @return
	 * @throws SQLException
	 * @throws DSException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String createSBD() throws SQLException, FileNotFoundException, IOException, DSException {
		//
		String sbdStr = null;
		// select data from db. -> documents_send μοντελο.
		// DocumentsSend docSend =
		// this.docSendHd.getDocumentSendByMsgId(messageId);
		// to messageId είναι το docId.

		// select data from documentserver -> File at file system
		String extension = "";

		// String filename = docClient.getDocument2File(messageId, extension) ;
		// docSend.setActualDocumentFilepath(filename);
		// convert model to String. DocumentsSend - > REMDispatch / SBD - >
		// SBDString.

		// use client to send it to connector

		// create SDB

		// send sbd

		return sbdStr;
	}

	/**
	 * Λαμβάνει το επόμενο έγγραφο από τον πίνακα των εξερχομένων. Δημιουργεί το
	 * SBD. Και το στελνει στον connector.
	 * 
	 * @return
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 * @throws MalformedURLException
	 */
	public void sendSBD(DocumentsSend docSend)
			throws MalformedURLException, JAXBException, DatatypeConfigurationException {
		SBDParams sbdParams = new SBDParams();

		MessageParams params = new MessageParams();
		params.seteSensConfigFilename(this.eDeliveryServerConfiguration.getESensConfigFilename());
		params.setOriginatorName(eDeliveryServerConfiguration.getSenderName());
		params.setOriginatorEmail(eDeliveryServerConfiguration.getSenderEmail());
		params.setDestinatorName(docSend.getDocumentReceiverOrganization());
		params.setDestinatorEmail(docSend.getDocumentReceiverAuthority());
		params.setFilename(docSend.getActualDocumentFilepath());
		params.setMsgId(docSend.getMessageId() + "");
		params.setMsgIdentification(docSend.getMessageUniqueId());
		params.setNormalizedDocSubject(docSend.getDocumentType());
		params.setNormalizedDocComments(docSend.getDocumentComments());
		params.setSamSenderId(eDeliveryServerConfiguration.getSamSenderId());

		deliveryClient.sendMessage(sbdParams, params, auth);
		LOG.log(Level.INFO, "message send");
	}

	public void sendSBD(String sdbStr) {//
		// call client to send SBD

	}

	
	public static SBDParams  bsdObj2bsdParams(BSDMessage msg){
		SBDParams sbdParams = new SBDParams();
		sbdParams.setHeaderVersion(1);
		sbdParams.set
		
		return sbdParams;
	}
}
