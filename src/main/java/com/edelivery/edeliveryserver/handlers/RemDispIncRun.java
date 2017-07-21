/*
 * Copyright (C) 2017 modussa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.edelivery.edeliveryserver.handlers;

import JavaPapyrusR6ServerApi.DataTypes.DocumentApi;
import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentsReceivedHandler;
import com.edelivery.edeliveryserver.db.entityhandlers.EvidenceHandler;
import com.edelivery.edeliveryserver.db.entityhandlers.MessagesReceivedFromApHandler;
import com.edelivery.edeliveryserver.db.entityhandlers.PostalAddressHandler;
//import com.edelivery.edeliveryserver.db.models.Document;
import com.edelivery.edeliveryserver.db.models.DocumentsReceived;
import com.edelivery.edeliveryserver.db.models.EvidenceReceived;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;
import com.edelivery.edeliveryserver.db.models.PostalAddress;
//import com.edelivery.edeliveryserver.documentserver.DocumentServer;
import com.edelivery.edeliveryserver.documentserver.DocumentServerClient;
import com.edelivery.edeliveryserver.exception.DocumentServerException;
import com.modus.edeliveryclient.EDeliveryClient;
import com.modus.edeliveryclient.exception.EDeliveryException;
import com.modus.edeliveryclient.jaxb.remdispatchhelpers.RemEvidenceHelper;
import com.modus.edeliveryclient.jaxb.remdispatchhelpers.RemMessageHelper;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.BusinessScope;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.DocumentIdentification;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.Partner;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.PartnerIdentification;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.REMDispatch;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.SBDHFactory;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.Scope;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.StandardBusinessDocument;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.StandardBusinessDocumentHeader;
import com.modus.edeliveryclient.models.Authorization;
import com.modus.edeliveryclient.models.MessageId;
import com.modus.edeliveryclient.models.Messages;
import com.modus.edeliveryclient.models.ResponseModel;
import eu.noble.rem.jaxb.despatch.KeywordType;
import eu.noble.rem.jaxb.despatch.REMDispatchType;
import eu.noble.rem.jaxb.despatch.REMMDEvidenceListType;
import eu.noble.rem.jaxb.despatch.REMMDMessageType;
import eu.noble.rem.jaxb.evidence.AttributedElectronicAddressType;
import eu.noble.rem.jaxb.evidence.MessageDetailsType;
import eu.noble.rem.jaxb.evidence.PostalAddressType;
import eu.noble.rem.jaxb.evidence.REMEvidenceType;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Pantelispanka
 */
//@Named
@Singleton
//@Stateful
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RemDispIncRun {

    private static final Logger LOG = Logger.getLogger(RemDispIncRun.class.getName());

    public EDeliveryClient client;

    @Inject
    public EDeliveryServerConfiguration config;

    @EJB
    MessagesReceivedFromApHandler mesUniq;

    @EJB
    DocumentsReceivedHandler docHandler;

    @EJB
    DocumentServerClient documentServer;

    @EJB
    PostalAddressHandler paHandler;

    @EJB
    EvidenceHandler evidHandler;

    public class findIncoming implements Runnable {

        private int count = 1;

        @Override
        public void run() {

            try {

                getClient();

            } catch (Exception e) {

                LOG.info(e.getMessage());
            }

        }

    }

    public void getClient() {
        client = config.getDeliveryClient();
        m(client);
    }

    public Messages m(EDeliveryClient client) {

        CompletableFuture<Messages> m = new CompletableFuture<>();
        Authorization auth = config.getAuth();

        m = client.getMesaggesPending(auth);

        Messages mes = new Messages();
        try {
            mes = m.get();
        } catch (InternalServerErrorException | InterruptedException | ExecutionException e) {
            LOG.log(Level.INFO, "EDeliveryServerUp. No messagesPending, or other client problem {0}", e.getMessage());
            return null;
        }
        try {
            MessageId mesId = mes.getMessages();
            String[] messages = mesId.getMessageId();
            for (String id : messages) {
                List<MessageReceivedFromAp> checked = checkMes(id);
                if (checked.isEmpty()) {
                    try {
                        CompletableFuture<Object> message = client.getMessageDefault(id, auth);

                        ResponseModel ames = new ResponseModel();
                        ames = (ResponseModel) message.get();
                        StandardBusinessDocument sbd = new StandardBusinessDocument();
                        ArrayList sbdlist = (ArrayList) ames.getData();
                        sbd = (StandardBusinessDocument) sbdlist.get(0);

                        JAXBElement element = (JAXBElement) sbd.getAny();
                        Object ob = element.getValue();
                        String name = ob.getClass().getName();
                        switch (name) {
                            case "eu.noble.rem.jaxb.despatch.REMDispatchType":
                                String instanceId = saveMessage(sbd, id);
                                savedMessage(id);
                                sendAcceptanceRejection(instanceId, sbd.getStandardBusinessDocumentHeader().getReceiver().get(0));
                                deleteMessage(id);
                                break;
                            case "eu.noble.rem.jaxb.despatch.REMMDMessageType":
                                saveEvidence(sbd, id);
                                deleteMessage(id);
                                break;
                        }

                    } catch (JAXBException e) {
                        throw new InternalServerErrorException("Error trying to get messages", e);
                    }

                } else {
                    break;
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new InternalServerErrorException("Error trying to get messages", e);
        }
        return mes;

    }

    public void deleteMessage(String id) {

        client.deleteMessage(id, config.getAuth());

    }

    public void saveEvidence(StandardBusinessDocument sbd, String apUniqueId) {

        EvidenceReceived evid = new EvidenceReceived();

        StandardBusinessDocumentHeader sbdh = sbd.getStandardBusinessDocumentHeader();
        JAXBElement<REMMDMessageType> remMesJ = (JAXBElement<REMMDMessageType>) sbd.getAny();
        REMMDMessageType remMes = remMesJ.getValue();
        JAXBElement<REMEvidenceType> remEvidJ = remMes
                .getREMMDEvidenceList()
                .getSubmissionAcceptanceRejectionAndRelayREMMDAcceptanceRejectionAndRelayREMMDFailure()
                .get(0);
        REMEvidenceType remEvid = remEvidJ.getValue();

        evid.setEvidenceCode(remEvid.getEventCode());
        evid.setEvidenceIdentification(remEvid.getEvidenceIdentifier());
//        evid.setRecipient(remEvid.get);

        XMLGregorianCalendar eventTime = remEvid.getEventTime();
        if (eventTime == null) {
            Date date = new Date();
            date.setTime(0);
            evid.setEvidenceTime(date);
            LOG.info("Received message " + remEvid.getEvidenceIdentifier() + "Without event time");
//            evid.setEvidenceTime();
        } else {
            evid.setEvidenceTime(remEvid.getEventTime().toGregorianCalendar().getTime());
        }

        List<DocumentsReceived> docRec = docHandler.gatMessageByUniqueId(remEvid.getReplyTo());

        if (docRec.isEmpty()) {
            LOG.info("Received Evidence without Document");
        } else if (docRec.size() > 1) {
            LOG.info("Received Evidence with multiple referenced Documents");
        } else {
            evid.setDocRec(docRec.get(0));
        }

        evidHandler.saveEvidence(evid);

    }

    public String saveMessage(StandardBusinessDocument sbd, String apUniqueId) {

        StandardBusinessDocumentHeader sbdh = sbd.getStandardBusinessDocumentHeader();
        JAXBElement<REMDispatchType> remDispJ = (JAXBElement<REMDispatchType>) sbd.getAny();
        REMDispatchType remDisp = remDispJ.getValue();
        DocumentsReceived docRec = new DocumentsReceived();

        InputStream is = new ByteArrayInputStream(remDisp.getOriginalMsg().getValue());

        DocumentApi doc;
        try {
            doc = storeDocument(remDisp.getNormalizedMsg().getInformational().getSubject(),
                    remDisp.getNormalizedMsg().getInformational().getComments(), is);
        } catch (DocumentServerException e) {
            throw new InternalServerErrorException("Document server error", e);
        }
        docRec.setDocId(doc.getId());

        docRec.setMessageUniqueId(remDisp.getId());
        docRec.setDocumentComments(remDisp.getNormalizedMsg().getInformational().getComments());
        docRec.setDocumentAcceptancePeriod(remDisp.getMsgMetaData().getDeliveryConstraints().getObsoleteAfter().toGregorianCalendar().getTime());
        docRec.setDocumentPurpose(remDisp.getNormalizedMsg().getInformational().getSubject());
        docRec.setMessageId(remDisp.getMsgMetaData().getMsgIdentification().getMessageID());

        docRec.setDocumentType(remDisp.getOriginalMsg().getContentType());

        List<KeywordType> keys = remDisp.getNormalizedMsg().getInformational().getKeywords();
        for (KeywordType key : keys) {
            docRec.setDocumentTitle(key.getValue());
        }

        docRec.setDocumentTitle(remDisp.getMsgMetaData().getMsgIdentification().getMessageID());

        docRec.setUniqueIdFromAp(apUniqueId);
        List<Partner> partner = sbdh.getSender();
        docRec.setDocumentIssuingAuthority(partner.get(0).getIdentifier().getValue());

        docHandler.saveMessage(docRec);

        String instanceId = sbdh.getDocumentIdentification().getInstanceIdentifier();

        return instanceId;

    }

    public void sendAcceptanceRejection(String replyTo, Partner receiver) {

        StandardBusinessDocumentHeader sbdh = new StandardBusinessDocumentHeader();
        SBDHFactory sbdhf = new SBDHFactory();

        XMLGregorianCalendar grec = null;
        GregorianCalendar gc = new GregorianCalendar();
        try {

            DatatypeFactory dtf = DatatypeFactory.newInstance();
            gc.setTimeZone(TimeZone.getTimeZone("GMT"));
            dtf.newXMLGregorianCalendar(gc);
            grec = dtf.newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sbdh.setHeaderVersion("1.0");
        DocumentIdentification docid = sbdhf.createDocumentIdentification();

        String eventCode = "http//:uri.etsi.org/REM/Event#Acceptance";
        String evidenceIdentifier = UUID.randomUUID().toString();

        docid.setTypeVersion("2");
        docid.setType("REMMDMessage");
        docid.setStandard("http://uri.etsi.org/02640/soapbinding/v2#");
        docid.setCreationDateAndTime(grec);
        docid.setInstanceIdentifier(evidenceIdentifier);

        BusinessScope businessScope = sbdhf.createBusinessScope();
        Scope e1 = sbdhf.createScope();
        e1.setInstanceIdentifier("http://uri.etsi.org/02640/soapbinding/v2#::REMDispatch:2");
        e1.setType("DOCUMENTID");

        Scope e2 = sbdhf.createScope();
        e2.setInstanceIdentifier("http://uri.etsi.org/02640/soapbinding/v2#::REMMDMessage:2");
        e2.setType("PROCESSID");

        businessScope.getScope().add(e1);
        businessScope.getScope().add(e2);

        Partner pSender = sbdhf.createPartner();
        PartnerIdentification pis = sbdhf.createPartnerIdentification();
        pis.setAuthority(config.getEDeliveryAuthority());
        pis.setValue(config.getEDeliveryAuthorityValue());
        pSender.setIdentifier(pis);

        sbdh.getSender().add(pSender);
        sbdh.getReceiver().add(receiver);
        sbdh.setBusinessScope(businessScope);
        sbdh.setDocumentIdentification(docid);

        RemMessageHelper remMesHelp = new RemMessageHelper();
        RemEvidenceHelper remEvHelp = new RemEvidenceHelper();

        PostalAddressType pat = new PostalAddressType();

        PostalAddress pa = new PostalAddress();

        try {
            List<PostalAddress> paList = paHandler.getPostalAddress();
            if (paList.isEmpty()) {
                pat.setCountryName("Country name unspecified");
                pat.setLang("Lang name unspecified");
                pat.setLocality("Locality unspecified");
                pat.setPostalCode("postal code unspecified");
                pat.setStateOrProvince("State or province unspecified");
            } else if (paList.size() > 1) {
                throw new InternalServerErrorException("Postall Address List has more than two instances, Please configure");
            } else {
                pa = paList.get(0);
                pat.setCountryName(pa.getCountryName());
                pat.setLang(pa.getLang());
                pat.setLocality(pa.getLocality());
                pat.setPostalCode(pa.getPostalCode());
                pat.setStateOrProvince(pa.getStateOrProvince());
            }
        } catch (Exception e) {
            throw new InternalServerErrorException("Postal Address List problems", e);
        }

        AttributedElectronicAddressType aeat = remEvHelp.createAttributedElAddrType("AEATDisplay name", "AEAT Scheme", "AEAT value");

        REMMDEvidenceListType remList = new REMMDEvidenceListType();
        MessageDetailsType mdtSender = remEvHelp.createMessageDetails(null, null, Boolean.TRUE, "messageIdentSender", "messageSubjectSender", "UAMessageIdSender");
        MessageDetailsType notifDet = remEvHelp.createMessageDetails(null, null, Boolean.TRUE, "NotifDetails", "NotifDetails", "NotifDetails");

        String forwardedToExternalSystem = "forwardedToExternalSystem";

        REMEvidenceType remType = remEvHelp.createRemEvidenceType(evidenceIdentifier,
                eventCode, evidenceIdentifier, replyTo, pat, grec,
                aeat, forwardedToExternalSystem, mdtSender,
                notifDet, BigInteger.ZERO);

        REMMDMessageType remMesJ = remMesHelp.createMessage("MessageId", remType);

        client.createEvidenceDefault(sbdh, remMesJ, config.getAuth());

    }

    public void savedMessage(String unId) {
        MessageReceivedFromAp mes = new MessageReceivedFromAp();
        mes.setMessageUniqueId(unId);
        mesUniq.createMessageUniqueId(mes);
    }

    public DocumentApi storeDocument(String filename, String description, InputStream stream) throws DocumentServerException {

        DocumentApi doc = documentServer.insertCall(config.getEDeliveryUser(), filename, description, stream);
        return doc;

    }

    public List<MessageReceivedFromAp> checkMes(String id) {
        List<MessageReceivedFromAp> res = new ArrayList();
        try {
            res = mesUniq.getMessageIdFirtsResult(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;

    }

}
