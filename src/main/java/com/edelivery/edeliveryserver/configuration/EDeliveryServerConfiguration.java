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
package com.edelivery.edeliveryserver.configuration;

//import com.edelivery.edeliveryserver.handlers.WorkerThreads;
import com.edelivery.edeliveryserver.handlers.EDeliveryHandler;
import com.edelivery.edeliveryserver.handlers.Poller;
import com.edelivery.edeliveryserver.handlers.WorkerThreads;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modus.edeliveryclient.EDeliveryClient;
import com.modus.edeliveryclient.EDeliveryClientImplementation;
import com.modus.edeliveryclient.consumer.SbdConsumer;
import com.modus.edeliveryclient.consumer.SmpParticipantConsumer;
import com.modus.edeliveryclient.models.Authorization;
import com.modus.edeliveryclient.serialize.Serializer;
import com.modus.edeliveryclient.signings.ISignatures;
import com.modus.edeliveryclient.signings.XmlDsig;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

/**
 *
 * @author Pantelispanka
 */
@Singleton
//@ApplicationScoped
@Startup
public class EDeliveryServerConfiguration {

    @EJB
    WorkerThreads wt;

    @EJB
    Poller poll;

    @EJB
    EDeliveryHandler han;

    private static String OS = System.getProperty("os.name").toLowerCase();

    private String workingPath;
    private String defaultUsername;
    private String defautlPassword;
    private String connectorBasePath;
    private ResourceBundle EDeliveryServerProperties;
    private File file;
    private String KeystoreInstance;
    private String KeystorePassword;
    private String pkEntry;
    private String KeystorePath;
    private String DocumentServersHost;
    private int DocumentServersPort;
    private int EDeliveryUser;
    private String EDeliveryAuthority;
    private String EDeliveryAuthorityValue;
//    private ISignatures signatures;

    public String getEDeliveryAuthority() {
        return EDeliveryAuthority;
    }

    public void setEDeliveryAuthority(String EDeliveryAuthority) {
        this.EDeliveryAuthority = EDeliveryAuthority;
    }

    public String getEDeliveryAuthorityValue() {
        return EDeliveryAuthorityValue;
    }

    public void setEDeliveryAuthorityValue(String EDeliveryAuthorityValue) {
        this.EDeliveryAuthorityValue = EDeliveryAuthorityValue;
    }

    public int getEDeliveryUser() {
        return EDeliveryUser;
    }

    public void setEDeliveryUser(int EDeliveryUser) {
        this.EDeliveryUser = EDeliveryUser;
    }

    public int getDocumentServersPort() {
        return DocumentServersPort;
    }

    public void setDocumentServersPort(int PapyrosServersPort) {
        this.DocumentServersPort = PapyrosServersPort;
    }

    public String getDocumentServersHost() {
        return DocumentServersHost;
    }

    public void setDocumentServersHost(String PapyrosServersHost) {
        this.DocumentServersHost = PapyrosServersHost;
    }

    private Authorization auth;

    private EDeliveryClient deliveryClient;

    public EDeliveryServerConfiguration() {
    }

    @PostConstruct
    public void Properties() {

        EDeliveryServerProperties = ResourceBundle.getBundle("EDeliveryServer", Locale.ENGLISH);

        this.defaultUsername = EDeliveryServerProperties.getString("defaultuser");
        this.defautlPassword = EDeliveryServerProperties.getString("defaultpassword");
        this.auth = new Authorization(EDeliveryServerProperties.getString("defaultuser"), EDeliveryServerProperties.getString("defaultpassword"));

        this.DocumentServersHost = EDeliveryServerProperties.getString("DocumentServerHost");
        this.DocumentServersPort = Integer.parseInt(EDeliveryServerProperties.getString("DocumentServerPort"));
        this.EDeliveryUser = Integer.parseInt(EDeliveryServerProperties.getString("EDeliveryUser"));
        
        this.EDeliveryAuthority = EDeliveryServerProperties.getString("EDeliveryAuthority");
        this.EDeliveryAuthorityValue = EDeliveryServerProperties.getString("EDeliveryAuthorityValue");
        
        this.connectorBasePath = EDeliveryServerProperties.getString("ConnectorBasepath");

        if (OS.contains("win")) {
            this.workingPath = EDeliveryServerProperties.getString("windowsfilepath");
            this.file = new File(workingPath);
            if (file.canWrite() && file.canRead()) {
                System.out.println("Rights are rigth");
            } else {
                throw new InternalServerErrorException("Server Doesn't have right to save files received, Please give permitions to " + workingPath);
            }
        } else {
            this.workingPath = EDeliveryServerProperties.getString("unixfilepath");
            this.file = new File(workingPath);
            if (file.canWrite() && file.canRead()) {
                System.out.println("Rights are rigth");
            } else {
                throw new InternalServerErrorException("Server Doesn't have right to save files received, Please give permitions to " + workingPath);
            }
        }

        this.KeystoreInstance = EDeliveryServerProperties.getString("KeystoreInstance");
        this.KeystorePassword = EDeliveryServerProperties.getString("KeystorePassword");
        this.KeystorePath = EDeliveryServerProperties.getString("KeystorePath");
        this.pkEntry = EDeliveryServerProperties.getString("pkEntry");

//        han.startClient();
        ISignatures signatures = new XmlDsig(KeystorePath, KeystorePassword, pkEntry, KeystoreInstance);

        Serializer serializer = new JacksonSerializer(new ObjectMapper()) {
        };
        AsyncHttpClient httpClient = new DefaultAsyncHttpClient();
        this.deliveryClient = new EDeliveryClientImplementation(httpClient, serializer,
                new SmpParticipantConsumer(httpClient, serializer, connectorBasePath, signatures),
                new SbdConsumer(httpClient, serializer, connectorBasePath, signatures), signatures);

        wt.Start();

    }

    public Authorization getAuth() {
        return auth;
    }

    public void setAuth(Authorization auth) {
        this.auth = auth;
    }

    public EDeliveryClient getDeliveryClient() {
        return deliveryClient;
    }

    public void setDeliveryClient(EDeliveryClient deliveryClient) {
        this.deliveryClient = deliveryClient;
    }

    public String getKeystoreInstance() {
        return KeystoreInstance;
    }

    public void setKeystoreInstance(String KeystoreInstance) {
        this.KeystoreInstance = KeystoreInstance;
    }

    public String getKeystorePassword() {
        return KeystorePassword;
    }

    public void setKeystorePassword(String KeystorePassword) {
        this.KeystorePassword = KeystorePassword;
    }

    public String getPkEntry() {
        return pkEntry;
    }

    public void setPkEntry(String pkEntry) {
        this.pkEntry = pkEntry;
    }

    public String getKeystorePath() {
        return KeystorePath;
    }

    public void setKeystorePath(String KeystorePath) {
        this.KeystorePath = KeystorePath;
    }

    public String getWorkingPath() {
        return workingPath;
    }

    public void setWorkingPath(String workingPath) {
        this.workingPath = workingPath;
    }

    public static String getOS() {
        return OS;
    }

    public static void setOS(String OS) {
        EDeliveryServerConfiguration.OS = OS;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public void setDefaultUsername(String defaultUsername) {
        this.defaultUsername = defaultUsername;
    }

    public String getDefautlPassword() {
        return defautlPassword;
    }

    public void setDefautlPassword(String defautlPassword) {
        this.defautlPassword = defautlPassword;
    }

    public String getConnectorBasePath() {
        return connectorBasePath;
    }

    public void setConnectorBasePath(String connectorBasePath) {
        this.connectorBasePath = connectorBasePath;
    }

    public ResourceBundle getEDeliveryServerProperties() {
        return EDeliveryServerProperties;
    }

    public void setEDeliveryServerProperties(ResourceBundle EDeliveryServerProperties) {
        this.EDeliveryServerProperties = EDeliveryServerProperties;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
