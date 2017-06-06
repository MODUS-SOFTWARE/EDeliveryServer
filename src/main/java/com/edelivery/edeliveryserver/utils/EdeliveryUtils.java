package com.edelivery.edeliveryserver.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;


import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandler;

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

@ApplicationScoped
public class EdeliveryUtils {
	
	private static final Logger LOG = Logger.getLogger(EdeliveryUtils.class.getName());
	 
	EdeliveryDatasource eds;
	DocumentSendHandler docSendHd;
	DocumentServerClient docClient;
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	AsyncHttpClient httpClient;
	Serializer serializer ;
	String basepath = "http://192.168.20.10:8080/APREST";
	String user="sp1";
    String password="sp1";
    Authorization auth ;
	public EdeliveryUtils(){
	}

	@Inject
	public EdeliveryUtils(EdeliveryDatasource eds,DocumentSendHandler docSendHd ,DocumentServerClient docClient
			,EDeliveryServerConfiguration eDeliveryServerConfiguration){
		this.eds = eds;
		this.docSendHd = docSendHd;
		this.docClient = docClient;
		this.eDeliveryServerConfiguration = eDeliveryServerConfiguration;
		this.httpClient = new DefaultAsyncHttpClient();
		this.serializer =  new com.modus.edeliveryserver.serializer.JacksonSerializer(new ObjectMapper());
		basepath = eDeliveryServerConfiguration.getConnector();
	    auth = new Authorization(user,password);
	}

	
	/**
	 * Επιστρέφει string που αναπαριστά ένα SBD, που αποτελείται από το 
	 * SBDH και το Payload 
	 * @param messageId
	 * @return
	 * @throws SQLException 
	 * @throws DSException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String createSBD() throws SQLException, FileNotFoundException, IOException, DSException{
		//
		String sbdStr = null;
		//select data from db. -> documents_send μοντελο.
		//DocumentsSend  docSend = this.docSendHd.getDocumentSendByMsgId(messageId);
		//to messageId είναι το docId.
		
		//select data from documentserver -> File at file system 
		String extension = "";
		
		//String filename = docClient.getDocument2File(messageId, extension) ;
		//docSend.setActualDocumentFilepath(filename);
		//convert model to String. DocumentsSend - > REMDispatch  /  SBD - > SBDString.
		
		
		//use client to send it to connector
		
		//create SDB

		//send sbd
		
		return sbdStr;
	}
	
	/**
	 * Λαμβάνει το επόμενο έγγραφο από τον πίνακα των εξερχομένων. 
	 * Δημιουργεί το SBD.
	 * Και το στελνει στον connector. 
	 *  
	 * @return 
	 * @throws DatatypeConfigurationException 
	 * @throws JAXBException 
	 * @throws MalformedURLException 
	 */
	public void sendNextSBD() throws MalformedURLException, JAXBException, DatatypeConfigurationException{
		//TODO sendNextSBD
		//get nextid.
		//create SBD
		//call adapter to send it
		 
	        
	       
	        EDeliveryClient deliveryClient = new EDeliveryClientImplementation(httpClient,
	                 serializer,
	                 new SmpParticipantConsumer(httpClient, serializer, basepath),
	                 new SbdConsumer(httpClient, serializer, basepath));
	    
	        ///fill sbdparams
	        //message params 
	        //auth object 
	        SBDParams sbdParams = new SBDParams(); 
	       
	        MessageParams params = new MessageParams(); 
	    	params.seteSensConfigFilename("C:\\eclipseProj\\edelivery\\GenericADAdapter-master\\main\\resource\\eSensConfig.xml");
	    	params.setOriginatorName("panos");
	    	params.setOriginatorEmail("panos@modus.gr");
	    	params.setDestinatorName("anagnosg");
	    	params.setDestinatorName("anagnosg@modus.gr");
	    	params.setFilename("F:\\testDocument\\test_upload.pdf");
	    	params.setMsgId("123");
	    	params.setMsgIdentification("1234");
	    	params.setNormalizedDocSubject("Esen");
	    	params.setNormalizedDocComments("comments");
	    	params.setSamSenderId("123");
	      
	        deliveryClient.sendMessage(sbdParams, params, auth);
	        LOG.log(Level.INFO,"message send");
	}
	
	
	
	public void sendSBD(String  sdbStr){//
		//call client to send SBD
		
	}
	
	
}
