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

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.configuration.JacksonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modus.edeliveryclient.EDeliveryClient;
import com.modus.edeliveryclient.EDeliveryClientImplementation;
import com.modus.edeliveryclient.consumer.SbdConsumer;
import com.modus.edeliveryclient.consumer.SmpParticipantConsumer;
import com.modus.edeliveryclient.models.Authorization;
import com.modus.edeliveryclient.models.Messages;
import com.modus.edeliveryclient.serialize.Serializer;
import com.modus.edeliveryclient.signings.XmlDsig;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

/**
 *
 * @author Pantelispanka
 */
@Singleton
@Startup
public class EDeliveryHandler {

    private static final Logger LOG = Logger.getLogger(EDeliveryHandler.class.getName());
    
    
    @EJB
    EDeliveryServerConfiguration conf;

    private EDeliveryClient deliveryClient;
    private String keystorePath;
    private String keystorePassword;
    private String pkEntry;
    private String keystoreInstance;
    private String ConnectorBasePath;

    private Authorization auth;
    

    @PostConstruct
    private void init()  {
        this.keystoreInstance = conf.getKeystoreInstance();
        this.keystorePassword = conf.getKeystorePassword();
        this.keystorePath = conf.getKeystorePath();
        this.pkEntry = conf.getPkEntry();
        this.ConnectorBasePath = conf.getConnectorBasePath();
        
        String un = conf.getDefaultUsername();
        String pw = conf.getDefautlPassword();
        this.auth = new Authorization(un, pw);

        XmlDsig signature = new XmlDsig(keystorePath, keystorePassword, pkEntry, keystoreInstance);

        Serializer serializer = new JacksonSerializer(new ObjectMapper()) {
        };
        AsyncHttpClient httpClient = new DefaultAsyncHttpClient();
        LOG.info("Creating client singleton");
        this.deliveryClient = new EDeliveryClientImplementation(httpClient, serializer,
                new SmpParticipantConsumer(httpClient, serializer, ConnectorBasePath, signature),
                new SbdConsumer(httpClient, serializer, ConnectorBasePath, signature), signature);

//        LOG.info(deliveryClient.toString());
//        LOG.info(this.auth.getUsername());
//        
//        CompletableFuture<Messages> m = deliveryClient.getMesaggesPending(auth);
//        try{
//            LOG.info(m.get().getNumberOfMessages());
//        }catch(InterruptedException | ExecutionException e){
//            e.printStackTrace();
//        }
        
    }

    
    
    public EDeliveryClient getEDeliveryClient() {

        Serializer serializer = new JacksonSerializer(new ObjectMapper()) {
        };
        AsyncHttpClient httpClient = new DefaultAsyncHttpClient();
        XmlDsig signature = new XmlDsig(keystorePath, keystorePassword, pkEntry, keystoreInstance);

        deliveryClient = new EDeliveryClientImplementation(httpClient, serializer,
                new SmpParticipantConsumer(httpClient, serializer, ConnectorBasePath, signature),
                new SbdConsumer(httpClient, serializer, ConnectorBasePath, signature), signature);

        return deliveryClient;

    }
    
    
    public EDeliveryClient getClient(){
        return deliveryClient;
    }
    
    public Authorization getAuth(){
        return auth;
    }

}
