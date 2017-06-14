package com.edelivery.edeliveryserver.db.entityhandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.db.models.BSDMessage;
import com.edelivery.edeliveryserver.db.models.Participants;
import com.edelivery.edeliveryserver.db.models.Scope;

@RequestScoped
public class BSDHandlerDB {
	private static final Logger LOGGER = Logger.getLogger(DocumentSendHandlerDB.class.getName());
	ConnectionWrapper connWrapper;

	public BSDHandlerDB() {
	}

	@Inject
	public BSDHandlerDB(ConnectionWrapper connWrapper) {
		this.connWrapper = connWrapper;
	}
	/*
	 * private static int headerVersion = 1;

    private final static String participantIdentifierSenderScheme = "iso6523-actorid-upis";
    private final static String participantIdentifierSenderValue = "9933:test1";
    private final static String participantIdentifierReceiverScheme = "iso6523-actorid-upis";
    private final static String participantIdentifierReceiverValue = "9933:test1";

    private final static String documentIdStandard = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
    private final static int docTypeVersion = 1;
    private final static String documentInstanceIdentifier = "627a2e9a-a146-461f-b62f-f22f5799fd55";
    private final static String documentType = "Invoice";

    private final static String scopeType = "DOCUMENTID";
    private final static String scopeIdentifier = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2:: Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn: www.peppol.eu:bis:peppol4a:ver2.0::2.1";
    private final static String scopeType2 = "PROCESSID";
    private final static String scopeIdentifier2 = "urn:www.cenbii.eu:profile:bii04:ver2.0";

    private final static String manifestDescr = "manifestDescr";
    private final static String manifestLanguage = " manifestLanguage";
    private final static String maniTypeQualCode = "maniTypeQualCode";
    private final static String uniformResourceIdentifier = "uniformResourceIdentifier";
	 * */
	public BSDMessage select(String message_id){
		BSDMessage sbd ;
		//epistrfei karfoto 
		sbd = new BSDMessage();
		sbd.setMessage_unique_id(message_id);
		sbd.setId(1);
		Participants sender  = new Participants();sender.setId(1); sender.setParticipantIdentifierScheme("iso6523-actorid-upis");
		sender.setParticipantIdentifierValue("9933:test1");
		
		Participants receiver = new Participants();
		receiver.setId(1);
		receiver.setParticipantIdentifierScheme("iso6523-actorid-upis");
		receiver.setParticipantIdentifierValue("9933:test1");
		sbd.setSender(sender);
		sbd.setReceiver(receiver);
		sbd.setDi_standard("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2");
		sbd.setDi_type("1");
		sbd.setDi_standard("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2");
		sbd.setDi_type("Invoice");
		sbd.setDi_id(message_id);
		sbd.setMan_descr("manifestDescr");
		sbd.setMan_lang("manifestLanguage");
		sbd.setMan_uni(message_id); //TODO unique
		sbd.setMan_type("maniTypeQualCode");
		sbd.setMan_uni("uniformResourceIdentifier");
		Scope s1 = new Scope(); s1.setId(1);
		s1.setSc_id("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2:: Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn: www.peppol.eu:bis:peppol4a:ver2.0::2.1");
		s1.setSc_instance("Instance");
		s1.setSc_type("DOCUMENTID");
		
		
		Scope s2 = new Scope(); s2.setId(2);
		s2.setSc_id("urn:www.cenbii.eu:profile:bii04:ver2.0");
		s2.setSc_instance("Instance");
		s2.setSc_type("PROCESSID");
		List<Scope> scoped = new ArrayList<Scope>();
		scoped.add(s1);scoped.add(s2);
		sbd.setBsdScope(scoped);
		
		return sbd;
	}
}
