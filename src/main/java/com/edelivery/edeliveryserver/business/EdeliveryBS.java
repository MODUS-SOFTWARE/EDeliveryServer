package com.edelivery.edeliveryserver.business;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.BSDHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentReceivedHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.EvidenceHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageReceivedFromApHandlerDB;
import com.edelivery.edeliveryserver.db.models.BSDMessage;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatus;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsReceived;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.Evidence;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;
import com.edelivery.edeliveryserver.db.models.Participants;

import com.google.gson.Gson;
import com.modus.edelivery.utils.DeliveryNonDeliveryToRecipientExtractor;
import com.modus.edelivery.utils.DispatchExtractor;
import com.modus.edelivery.utils.SBDMessageWrapper;
import com.modus.edeliveryclient.EDeliveryClient;
import com.modus.edeliveryclient.EDeliveryClientImplementation;
import com.modus.edeliveryclient.consumer.SbdConsumer;
import com.modus.edeliveryclient.consumer.SmpParticipantConsumer;
import com.modus.edeliveryclient.models.Authorization;
import com.modus.edeliveryclient.models.EvidenceParams;
import com.modus.edeliveryclient.models.Messages;
import com.modus.edeliveryclient.models.ResponseMessage;
import com.modus.edeliveryclient.models.SBDParams;
import com.modus.edeliveryclient.serialize.Serializer;
import com.modus.edeliveryserver.papyros.servers.DocumentServerClient;

import JavaPapyrusR6ServerApi.DataTypes.DocumentApi;
import gr.modus.edelivery.adapter.messages.MessageParams;
import gr.modus.edelivery.papyros.servers.exceptions.DSException;
import gr.modus.edelivery.papyros.servers.exceptions.InvalidInputException;


@RequestScoped
public class EdeliveryBS {

	private static final Logger LOG = Logger.getLogger(EdeliveryBS.class.getName());

	ConnectionWrapper connWrapper;
	DocumentSendHandlerDB docSendHd;
	DocumentServerClient docClient;
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	AsyncHttpClient httpClient;
	Serializer serializer;
	String basepath = "http://192.168.20.10:8080/APREST";
	
	String user = "";
	String password = "";
	Authorization auth;
	EDeliveryClient deliveryClient;
	BSDHandlerDB bsdHandler;
	DocumentReceivedHandlerDB docReceivedHandler;
	EvidenceHandlerDB eviHandler;
	MessageReceivedFromApHandlerDB messageReceivedHandler;
	public EdeliveryBS() {
	}

	@Inject
	public EdeliveryBS(ConnectionWrapper conWrapper, DocumentSendHandlerDB docSendHd, DocumentServerClient docClient,
			EDeliveryServerConfiguration eDeliveryServerConfiguration,BSDHandlerDB bsdHandler
			,DocumentReceivedHandlerDB docReceivedHandler,EvidenceHandlerDB eviHandler
			,MessageReceivedFromApHandlerDB messageReceivedHandler
			) {
		this.eDeliveryServerConfiguration = eDeliveryServerConfiguration;
		this.user=this.eDeliveryServerConfiguration.getConnectorUser();
		this.password=this.eDeliveryServerConfiguration.getConnectorPassword();
		this.connWrapper = conWrapper;
		this.docSendHd = docSendHd;
		this.docClient = docClient;
		this.eviHandler = eviHandler;
		this.messageReceivedHandler = messageReceivedHandler;
		/*
		 * 
		 * */
		
		
		
		this.httpClient = new DefaultAsyncHttpClient();
		this.serializer = null;
		basepath = eDeliveryServerConfiguration.getConnector();
		auth = new Authorization(user, password);
		deliveryClient = new EDeliveryClientImplementation(httpClient, serializer,
				new SmpParticipantConsumer(httpClient, serializer, basepath),
				new SbdConsumer(httpClient, serializer, basepath));
		this.bsdHandler = bsdHandler;
		this.docReceivedHandler = docReceivedHandler;
		
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
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public void sendSBD(DocumentsSend docSend,Connection conn)
			throws MalformedURLException, JAXBException, DatatypeConfigurationException, InterruptedException, ExecutionException, SQLException {
		
		
		boolean  closeConnection = false; 
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try{
			SBDParams sbdParams;
			BSDMessage sbdMsg = bsdHandler.select(docSend.getMessageUniqueId());
			sbdParams = bsdObj2bsdParams(sbdMsg);
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
	
			CompletableFuture<ResponseMessage> responseC = deliveryClient.sendMessage(sbdParams, params, auth);
			
			
			
			LOG.log(Level.INFO, new Gson().toJson(responseC.get()));
			LOG.log(Level.INFO, "message send");
			docSend.setDocumentStatus(new DocumentStatus(DocumentStatuses.SEND .getValue()));
			docSendHd.updateStatus(docSend, null);
		}
		finally{
			if(conn!=null && closeConnection){
				conn.close();
			}
		}
		
	}

	
	public void receiveEvidenceAp(String  msgId, Connection conn)
			throws JAXBException, DatatypeConfigurationException, InterruptedException, ExecutionException, XPathExpressionException, IOException, DSException, SQLException {
		
		
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try{
			CompletableFuture<Object> result = deliveryClient.getMessageEvidenceAp(msgId, auth, true);
			String msg = (String)result.get();
			System.out.println(msg);
			DeliveryNonDeliveryToRecipientExtractor wrapper= new DeliveryNonDeliveryToRecipientExtractor(msg);
			EvidenceParams evidenceParams = wrapper.extractParams();
			
			String filename = "Devidence_"+evidenceParams.getEvidence_identifier();
			String file = msg;
			byte[] valueDecoded= file.getBytes() ;
			//byte[] dataFile = file.getBytes();
			DocumentApi docApi;
			try(InputStream in = new ByteArrayInputStream(valueDecoded);){ 
				docApi = docClient.insertCall(this.eDeliveryServerConfiguration.getEdeliveryUser(), filename, filename, in);
			//	insert document to db
			}
			
			
			Evidence evi;
			evi= eviParams2Evi( evidenceParams);
			evi.setDocId(docApi.getId());
			eviHandler.insert(evi, conn);
			
			/*File f = new File("c:/tmp/"+filename);
			try(FileOutputStream fout=new FileOutputStream(f);InputStream in = new ByteArrayInputStream(valueDecoded);){
				IOUtils.copy(in, fout);
			}*/
			
			
			LOG.log(Level.INFO, "params:"+new Gson().toJson(evi));
			LOG.log(Level.INFO, "message send");
		}
		finally{
			if(conn!=null&& closeConnection){
				conn.close();
				
			}
		}
	}
	
	public void receiveNextMessage(Connection conn) throws SQLException, InterruptedException, ExecutionException, XPathExpressionException, JAXBException, DatatypeConfigurationException, IOException, DSException, InvalidInputException{
		List<MessageReceivedFromAp> all = receivePending( conn);
		List<MessageReceivedFromAp> pending =  messageReceivedHandler.select(all);
		MessageReceivedFromAp msg = pending.get(0);
		receiveSBD(msg,conn);
	}
	public List<MessageReceivedFromAp> receivePending(Connection conn) throws SQLException, InterruptedException, ExecutionException{
		/*CompletableFuture<Messages> result = deliveryClient.getMesaggesPending(auth);
		Messages msg = new Messages();
		msg = (Messages) result.get();
		System.out.println(new Gson().toJson(msg));
		*/
		List<MessageReceivedFromAp> all = new ArrayList<MessageReceivedFromAp>();
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try{
			CompletableFuture<Messages> results = deliveryClient.getMesaggesPending(auth);
			Messages msg = results.get();
			for(int i = 0 ; i< msg.getMessages().getMessageId().length  ; i ++){
				String messageUniqueId = msg.getMessages().getMessageId()[i];
				//System.out.println(s);
				MessageReceivedFromAp messageAp= new MessageReceivedFromAp();
				messageAp.setMessageUniqueId(messageUniqueId);
				all.add(messageAp);	
			}
			/*List<MessageReceivedFromAp> all*/
		}
		finally{
			if(conn!=null&& closeConnection){
				conn.close();
				
			}
		}
		return all;
	}
	public void receiveSBD(MessageReceivedFromAp msg2Get, Connection conn)
			throws JAXBException, DatatypeConfigurationException, InterruptedException, ExecutionException, XPathExpressionException, IOException, DSException, SQLException, InvalidInputException {
		
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		MessageParams params = null;
		try{
			CompletableFuture<Object> result = deliveryClient.getMessageDefault(msg2Get.getMessageUniqueId(), auth,true);
			String msg = (String)result.get();
			System.out.println(msg);
			SBDMessageWrapper wrapper= new SBDMessageWrapper(msg);
			String payLoad = wrapper.getPayload(true);
			DispatchExtractor dispExtractor = new DispatchExtractor(payLoad); 
			params = dispExtractor.extractParams();
			//insert file to papyros 
			String filename = params.getFilename();
			String file = params.getFile();
			if(file.equals("")){
				throw new InvalidInputException("file is empty, message type is not valid");
			}
			byte[] valueDecoded= Base64.getDecoder().decode(file.getBytes() );
			//byte[] dataFile = file.getBytes();
			DocumentApi docApi;
			try(InputStream in = new ByteArrayInputStream(valueDecoded);){ 
				docApi = docClient.insertCall(this.eDeliveryServerConfiguration.getEdeliveryUser(), filename, filename, in);
			//	insert document to db
			}
			SBDParams sbdParams;
			sbdParams = wrapper.extractParams();
			BSDMessage BSDmsg  = bsdObj2bsdParams(sbdParams);
			DocumentsReceived docReceived;
			docReceived= messageParams2DocReceiv( params);
			docReceived.setDocId(docApi.getId());
			docReceived.setMessageUniqueId(msg2Get.getMessageUniqueId()); //TODO check
			docReceivedHandler.insert(docReceived);
			
			/*File f = new File("c:/tmp/"+filename);
			try(FileOutputStream fout=new FileOutputStream(f);InputStream in = new ByteArrayInputStream(valueDecoded);){
				IOUtils.copy(in, fout);
			}*/
			
			
			LOG.log(Level.INFO, "params:"+new Gson().toJson(params));
			LOG.log(Level.INFO, "message send");
		}
		catch(Exception ex){
			DocumentsReceived docReceived;
			docReceived= messageParams2DocReceiv( params);
			docReceived.setDocId(-1);
			docReceived.setDocumentStatus(new DocumentStatus(DocumentStatuses.FAILED.getValue()));
			docReceived.setMessageUniqueId(msg2Get.getMessageUniqueId()); //TODO check
			docReceivedHandler.insert(docReceived);
		}
		finally{
			if(conn!=null&& closeConnection){
				conn.close();
				
			}
		}
	}

	
	public static SBDParams  bsdObj2bsdParams(BSDMessage msg){
		SBDParams sbdParams = new SBDParams();
		sbdParams.setHeaderVersion(1.0f);
		sbdParams.setDocTypeVersion(1); //TODO
		sbdParams.setDocumentIdStandard(msg.getDi_standard());
		sbdParams.setDocumentInstanceIdentifier(msg.getDi_id());
		sbdParams.setDocumentType(msg.getDi_type());
		sbdParams.setManifestDescr(msg.getMan_descr());
		sbdParams.setManifestLanguage(msg.getMan_lang());
		sbdParams.setManiTypeQualCode(msg.getMan_type());
		sbdParams.setUniformResourceIdentifier(msg.getMan_uni());
		sbdParams.setParticipantIdentifierReceiverScheme(msg.getReceiver().getParticipantIdentifierScheme());
		sbdParams.setParticipantIdentifierReceiverValue(msg.getReceiver().getParticipantIdentifierValue());
		sbdParams.setParticipantIdentifierSenderScheme(msg.getSender().getParticipantIdentifierScheme());
		sbdParams.setParticipantIdentifierSenderValue(msg.getSender().getParticipantIdentifierValue());
		sbdParams.setScopeIdentifier(msg.getBsdScope().get(0).getSc_id());
		sbdParams.setScopeInstance(msg.getBsdScope().get(0).getSc_instance());
		sbdParams.setScopeType(msg.getBsdScope().get(0).getSc_type());
		
		sbdParams.setScopeIdentifier2(msg.getBsdScope().get(1).getSc_id());
		sbdParams.setScopeInstance2(msg.getBsdScope().get(1).getSc_instance());
		sbdParams.setScopeType2(msg.getBsdScope().get(1).getSc_type());
		return sbdParams;
	}
	public static BSDMessage  bsdObj2bsdParams(SBDParams params){
		BSDMessage  bsdMessage = new BSDMessage(); 
		Participants sender = new Participants(); 
		Participants receiver = new Participants();
		sender.setParticipantIdentifierScheme(params.getParticipantIdentifierReceiverScheme());
		sender.setParticipantIdentifierValue(params.getParticipantIdentifierSenderValue());
		receiver.setParticipantIdentifierScheme(params.getParticipantIdentifierReceiverScheme());
		receiver.setParticipantIdentifierValue(params.getDocumentInstanceIdentifier());
		
		/*SBDParams sbdParams = new SBDParams();
		sbdParams.setHeaderVersion(1.0f);
		sbdParams.setDocTypeVersion(1); //TODO
		sbdParams.setDocumentIdStandard(msg.getDi_standard());
		sbdParams.setDocumentInstanceIdentifier(msg.getDi_id());
		sbdParams.setDocumentType(msg.getDi_type());
		sbdParams.setManifestDescr(msg.getMan_descr());
		sbdParams.setManifestLanguage(msg.getMan_lang());
		sbdParams.setManiTypeQualCode(msg.getMan_type());
		sbdParams.setUniformResourceIdentifier(msg.getMan_uni());
		sbdParams.setParticipantIdentifierReceiverScheme(msg.getReceiver().getParticipantIdentifierScheme());
		sbdParams.setParticipantIdentifierReceiverValue(msg.getReceiver().getParticipantIdentifierValue());
		sbdParams.setParticipantIdentifierSenderScheme(msg.getSender().getParticipantIdentifierScheme());
		sbdParams.setParticipantIdentifierSenderValue(msg.getSender().getParticipantIdentifierValue());
		sbdParams.setScopeIdentifier(msg.getBsdScope().get(0).getSc_id());
		sbdParams.setScopeType(msg.getBsdScope().get(0).getSc_type());
		
		sbdParams.setScopeIdentifier2(msg.getBsdScope().get(1).getSc_id());
		sbdParams.setScopeType2(msg.getBsdScope().get(1).getSc_type());
		*/
		return bsdMessage;
	}
	
	
	
	public static DocumentsReceived messageParams2DocReceiv(MessageParams params){
		DocumentsReceived docReceived = new DocumentsReceived(); 
		docReceived.setActualDocumentFilepath(params.getFilename());
		docReceived.setAttachedDocumentsReceivedCollection(null);
		docReceived.setDocId(null);
		docReceived.setDocumentAcceptancePeriod(null);
		docReceived.setDocumentAuthorityApplicant(null);
		docReceived.setDocumentComments(params.getNormalizedDocComments());
		docReceived.setDocumentDescription(null);
		docReceived.setDocumentEtiquetteCreationDate(null);
		docReceived.setDocumentIssuingAuthority(params.getOriginatorEmail());
		docReceived.setDocumentIssuingOrganization(params.getOriginatorName());
		docReceived.setDocumentLanguage(null);
		docReceived.setDocumentOrganizationApplicant(null);
		docReceived.setDocumentOrganizationEtiquette("-");
		docReceived.setDocumentPurpose(null);
		docReceived.setDocumentReceivedFromApDate(new Date()); //current 
		docReceived.setDocumentReceiverAuthority(params.getDestinatorEmail());
		docReceived.setDocumentReceiverOrganization(params.getDestinatorName());
		docReceived.setDocumentStatus(new DocumentStatus(2));//TODO
		docReceived.setDocumentSubmitedToApDate(null);
		docReceived.setDocumentTitle(params.getNormalizedDocSubject()==null?"-":params.getNormalizedDocSubject());
		docReceived.setDocumentType("-");//TODO
		docReceived.setDocumentValidPeriod(null);
		//docReceived.setMessageId(params.getMsgId()); TODO 
		//docReceived.setMessageUniqueId(params.getMsgId());
		return docReceived;
	}
	
	public static Evidence eviParams2Evi(EvidenceParams params){
		Evidence evi = new Evidence(); 
		evi.setActual_document_filepath("");
		String code = (params.getEventcode().indexOf("#")>-1)?params.getEventcode().substring(params.getEventcode().indexOf("#")+1, params.getEventcode().length()):params.getEventcode();
		evi.setEventCode(code);
		evi.setEvidence_id(params.getEvidence_identifier());
		evi.setEvidence_name(params.getEvidence_name());
		evi.setEvidence_time(params.getEvidence_time());
		evi.setReference_document(params.getReference_document());
		
		return evi;
	}
	
}
