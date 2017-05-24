package com.edelivery.edeliveryserver.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandler;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;
import com.modus.edeliveryserver.papyros.servers.DocumentServerClient;

import gr.modus.edelivery.papyros.servers.exceptions.DSException;

@ApplicationScoped
public class SBDUtils {
	
	
	EdeliveryDatasource eds;
	DocumentSendHandler docSendHd;
	DocumentServerClient docClient;
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	
	public SBDUtils(){
	}

	@Inject
	public SBDUtils(EdeliveryDatasource eds,DocumentSendHandler docSendHd ,DocumentServerClient docClient
			,EDeliveryServerConfiguration eDeliveryServerConfiguration){
		this.eds = eds;
		this.docSendHd = docSendHd;
		this.docClient = docClient;
		this.eDeliveryServerConfiguration = eDeliveryServerConfiguration;
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
	 */
	public void sendNextSBD(){
		//TODO sendNextSBD
		//get nextid.
		//create SBD
		//call adapter to send it
		
		
	}
	
	
	
	public void sendSBD(String  sdbStr){//
		//call client to send SBD
		
	}
	
	
}
