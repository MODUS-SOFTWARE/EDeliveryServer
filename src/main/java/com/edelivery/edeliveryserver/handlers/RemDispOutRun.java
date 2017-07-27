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
import com.edelivery.edeliveryserver.db.constants.MessageStatus;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageStatusHandler;
import com.edelivery.edeliveryserver.db.entityhandlers.MessagesSentHandler;
import com.edelivery.edeliveryserver.db.entityhandlers.PostalAddressHandler;
import com.edelivery.edeliveryserver.db.models.MessagesSent;
import com.edelivery.edeliveryserver.db.models.MessagesStatus;
import com.edelivery.edeliveryserver.db.models.PostalAddress;
import com.edelivery.edeliveryserver.documentserver.DocumentServerClient;
import com.edelivery.edeliveryserver.exception.DocumentServerException;
import com.modus.edeliveryclient.EDeliveryClient;
import com.modus.edeliveryclient.jaxb.remdispatchhelpers.RemDispatchHelper;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.BusinessScope;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.DocumentIdentification;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.Partner;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.PartnerIdentification;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.SBDHFactory;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.Scope;
import com.modus.edeliveryclient.jaxb.standardbusinessdocument.StandardBusinessDocumentHeader;
import com.modus.edeliveryclient.models.ResponseMessage;
import eu.noble.rem.jaxb.despatch.KeywordType;
import eu.noble.rem.jaxb.despatch.MsgMetaData;
import eu.noble.rem.jaxb.despatch.NormalizedMsg;
import eu.noble.rem.jaxb.despatch.NormalizedMsg.Text;
import eu.noble.rem.jaxb.despatch.OriginalMsgType;
import eu.noble.rem.jaxb.despatch.REMDispatchType;
import eu.noble.rem.jaxb.evidence.PostalAddressType;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author modussa
 */
@Singleton
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RemDispOutRun {

    private static final Logger LOG = Logger.getLogger(RemDispOutRun.class.getName());

    @Inject
    EDeliveryServerConfiguration conf;

    @EJB
    MessagesSentHandler mesHandler;

    @EJB
    DocumentServerClient documentServer;

    @EJB
    PostalAddressHandler paHandler;

    @EJB
    MessageStatusHandler statusHandler;

//    @EJB
//    DocumentServerClient documentServer;
    public class sendOutgoing implements Runnable {

        @Override
        public void run() {
            startSendingMessages();
        }

    }

    public void startSendingMessages() {

        EDeliveryClient client = conf.getDeliveryClient();
        sentMessage(client);

    }

    public void sentMessage(EDeliveryClient client) {

        List<MessagesSent> mesList = mesHandler.getPendingMessages();

        if (mesList.isEmpty()) {
            LOG.info("There are no messages pending to be sent");
        } else {
            for (MessagesSent ms : mesList) {
                MessagesSent msWithId = setMessageUniqueId(ms);
                REMDispatchType remType = new REMDispatchType();
                StandardBusinessDocumentHeader sbdh = new StandardBusinessDocumentHeader();
                try {
                    remType = createRemDispatch(msWithId);
                    sbdh = createSBDH(msWithId);
                } catch (Exception e) {
                    LOG.info(e.getLocalizedMessage());
                }
//                REMDispatchType remType = createRemDispatch(msWithId);
//                StandardBusinessDocumentHeader sbdh = createSBDH(msWithId);
                CompletableFuture<ResponseMessage> resp = client.createOutgoingDefaultImpl(sbdh, remType, conf.getAuth());
                ResponseMessage msg = new ResponseMessage();
                try {
                    msg = resp.get();
                    if (msg.getStatus() < 399) {
                        LOG.log(Level.INFO, "Message {0} accepted by AP", msWithId.getMessageUnId());
                        MessagesStatus status = new MessagesStatus();
                        status = statusHandler.findByStatus(MessageStatus.SENT.toString());
                        msWithId.setDocumentStatus(status);
                        mesHandler.updateMessage(msWithId);
                    }
                    else{
                        LOG.info(msg.getMessage());
                    }

                } catch (InterruptedException | ExecutionException e) {
                    throw new InternalServerErrorException("Client interrupted", e);
                }

            }

        }

    }

    public REMDispatchType createRemDispatch(MessagesSent ms) {

//        MessagesSent ms = setMessageUniqueId(_ms);
        RemDispatchHelper remHelper = new RemDispatchHelper();
        REMDispatchType remType = new REMDispatchType();

        DocumentApi docApi = new DocumentApi();

        String filename = new String();
        try {
//            documentServer.
            filename = documentServer.getDocument2File(ms.getDocId(), null, docApi);
        } catch (DocumentServerException | IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }

        KeywordType kw = new KeywordType();
        kw.setMeaning("PROTOCOL");
        kw.setValue(ms.getDocumentProtocol());
        List<KeywordType> kwl = new ArrayList();
        kwl.add(kw);

//        remHelper.
        String contentType;
        File file = new File(filename);
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new InternalServerErrorException("filepath problem", e);
        }

        BigInteger fileSize = BigInteger.valueOf(docApi.getFileSize());
        Path path = Paths.get(file.getAbsolutePath());
        byte[] actualFile;
        try {
            actualFile = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new InternalServerErrorException("Cannot convert/read file", e);
        }

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

        GregorianCalendar c = new GregorianCalendar();
        GregorianCalendar c2 = new GregorianCalendar();
        Date dateOrigin = new Date();

        c.setTime(dateOrigin);
        if(ms.getDocValidPeriod() != null){
            c2.setTime(ms.getDocValidPeriod());
        }
        XMLGregorianCalendar originDate;
        XMLGregorianCalendar obsoleteAfter;
        XMLGregorianCalendar initialSend;
        
        try {
            originDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            obsoleteAfter = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
            initialSend = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            throw new InternalServerErrorException("Could not create XMLGregorian times", e);
        }

        OriginalMsgType orMes = remHelper.createOriginalMessage(contentType, fileSize, actualFile);
        NormalizedMsg nMsg = remHelper.createNormalizedMessage(ms.getDocComments(), ms.getDocSubject(), null, null, kwl);
        MsgMetaData msgMeta = remHelper.createRemDispatchMetaData(initialSend, obsoleteAfter, originDate, pat.getCountryName(),
                pat.getLang(), pat.getLocality(), pat.getPostalCode(), pat.getStateOrProvince(), filename);

        remType = remHelper.createRemDispatch(ms.getMessageUnId(), msgMeta, nMsg, orMes, null, null);

//        JAXBElement<REMDispatchType> remTypeJ = remHelper.generateRexmXml(remType);
        return remType;
    }

    public StandardBusinessDocumentHeader createSBDH(MessagesSent ms) {

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
        String evidenceIdentifier = ms.getMessageUnId();

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
        pis.setAuthority(conf.getEDeliveryAuthority());
        pis.setValue(conf.getEDeliveryAuthorityValue());
        pSender.setIdentifier(pis);

        sbdh.getSender().add(pSender);

        Partner receiver = sbdhf.createPartner();
        PartnerIdentification pis2 = sbdhf.createPartnerIdentification();
        pis2.setAuthority(ms.getDocumentReceiverAuthority());
        pis2.setValue(ms.getDocumentReceiverOrganization());

        
        receiver.setIdentifier(pis2);
        sbdh.getReceiver().add(receiver);
        sbdh.setBusinessScope(businessScope);
        sbdh.setDocumentIdentification(docid);

        return sbdh;

    }

    public MessagesSent setMessageUniqueId(MessagesSent ms) {

        String messageUniqueId = UUID.randomUUID().toString();
        ms.setMessageUnId(messageUniqueId);
        return ms;

    }

}
