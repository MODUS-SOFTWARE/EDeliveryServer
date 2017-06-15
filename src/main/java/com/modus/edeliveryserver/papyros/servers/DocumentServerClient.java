package com.modus.edeliveryserver.papyros.servers;


/*
 * Copyright(c) 2016 Modus SA  All Rights Reserved.
 * This software is the proprietary information of Modus SA. 
 */


import JavaPapyrusR6ServerApi.Constants;
import JavaPapyrusR6ServerApi.DataTypes.DocumentApi;
import JavaPapyrusR6ServerApi.DataTypes.WMTErrRetType;
import gr.modus.edelivery.papyros.servers.exceptions.DSException;
import JavaPapyrusR6ServerApi.DocumentServices;
import JavaPapyrusR6ServerApi.Errors;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.business.EdeliveryBS;
import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.configuration.EdeliverySettings;
import com.google.gson.Gson;




/**
 *
 * @author George Anagnostopoulos
 */
@ApplicationScoped
public class DocumentServerClient {
	private static final Logger LOG = Logger.getLogger(DocumentServerClient.class.getName());
	 
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	
	
    public DocumentServerClient(){
    	
    }

    @Inject
    public DocumentServerClient(EDeliveryServerConfiguration eDeliveryServerConfigurationp){
    	eDeliveryServerConfiguration = eDeliveryServerConfigurationp;
    }

    
    public String getDocument2File(Integer docId, String extension) throws IOException, FileNotFoundException, DSException {
    	 Integer page = 0;
    	 Integer userId  = this.eDeliveryServerConfiguration.getEdeliveryUser();
    	 Integer docTypeId = Constants.DEFAULT_DOCUMENT_DISPLAY_TYPE_NORMAL;
    	 return getDocument2File( docId,  page,  userId,  docTypeId,  extension);
    }
    
    
    public String getDocument2File(Integer docId, Integer page, Integer userId, Integer docTypeId, String extension) throws IOException, FileNotFoundException, DSException {
        List resultTemp;

        int numberOfPagep = page;
        int docTypeIdp = docTypeId;
        DocumentApi docApi;
        WMTErrRetType errorLoc;

        
        String tempFolder = eDeliveryServerConfiguration.getEDeliveryServerProperties().getString("tempFolder");// edeliverySettings.getTempFolder();
        UUID uniqueId = UUID.randomUUID();
        File tempFolderFile = new File(tempFolder);
        if (!tempFolderFile.exists()) {
            tempFolderFile.mkdirs();
        }
        String outputFile = tempFolder + uniqueId;
        if (extension != null) {
            outputFile += "." + extension;
        }
        File fout;
        BufferedOutputStream buffStream = null;
        try {
            fout = new File(outputFile);
            buffStream = new BufferedOutputStream(new FileOutputStream(fout));
            errorLoc = getDocumentR7Call(userId, docId, numberOfPagep, docTypeIdp, buffStream);//getDocumentCall(filename, docId, docTypeId, userId)
            throwDSError(errorLoc, "getDocument,GetDocumentR7");
            docApi = new DocumentApi();
            resultTemp = errorLoc.getData();
            docApi.setFilename(resultTemp.get(0).toString());
            docApi.setExtension(resultTemp.get(1).toString());
            docApi.setTypeId((Integer) resultTemp.get(2)); //Δεν είναι σωστό που το βάζω στο setTypeId γιατί αυτό είναι αν έχει γίνει convert ή όχι. Όχι ο τύπος του εγγράφου.
            docApi.setFileSize((Integer) resultTemp.get(4));
        } catch (FileNotFoundException | DSException ex) {
            if (buffStream != null) {
                buffStream.close();
            }
            if (outputFile != null) {//Στην περίπτωση σφάλματος διαγράφω το αρχείο. 
                File fdelete = new File(outputFile);
                if (fdelete.exists() && !fdelete.isDirectory()) {
                    fdelete.delete();
                }
            }
            throw ex;
        } finally {
            if (buffStream != null) {
                buffStream.close();
            }
        }
        return outputFile;
    }

    public DocumentApi getDocument(int docId, int page, int docTypeId, int userId, BufferedOutputStream out) throws DSException {

        int numberOfPagep = page;
        int docTypeIdp = docTypeId;
        WMTErrRetType errorLoc = getDocumentR7Call(userId, docId, numberOfPagep, docTypeIdp, out);
        throwDSError(errorLoc, "getDocument,GetDocumentR7");
        DocumentApi docApi = new DocumentApi();
        List resultTemp = errorLoc.getData();
        docApi.setFilename(resultTemp.get(0).toString());
        docApi.setExtension(resultTemp.get(1).toString());
        docApi.setTypeId((Integer) resultTemp.get(2)); //Δεν είναι σωστό που το βάζω στο setTypeId γιατί αυτό είναι αν έχει γίνει convert ή όχι. Όχι ο τύπος του εγγράφου.
        docApi.setFileSize((Integer) resultTemp.get(4));

        return docApi;
    }

    public WMTErrRetType getDocumentR7Call(
            int userId,
            int docId,
            int page,
            int typeId,
            BufferedOutputStream pOut) {

        WMTErrRetType errorLoc = new WMTErrRetType(Errors.WM_SUCCESS, Errors.WMS_SUCCESS);
        DocumentServices ds = new DocumentServices(this.eDeliveryServerConfiguration.getPapyrosServersHost() , this.eDeliveryServerConfiguration.getDocumentServerPort());
        try {
            ds.initSetSockets();
            int numberOfPagep = page;
            int docTypeIdp = typeId;
            errorLoc = ds.GetDocumentR7(
                    userId,
                    docId,
                    numberOfPagep,
                    docTypeIdp,
                    Constants.BYTES_PACKET_SIZE,
                    pOut);
        } finally {
            ds.finalMySockets();
        }
        return errorLoc;
    }

    public void throwDSError(WMTErrRetType error, String messageInput) throws DSException {
        if (error != null && !error.hasNoErrors()) {
            throw new DSException(error);
        }
    }
    
    /**
     *Επιστρέφει το nextid του συγκεκριμένου τύπου δεδομένων . 
     * @param objectType Ο τύπος δεδομένων. 
     * @return Ακέραιος με το nextId.
     * @throws DSException
     */

    public  int getPNextId (int objectType) throws DSException{
          
        int nextId = 0;
        DocumentServices ds = new DocumentServices(this.eDeliveryServerConfiguration.getPapyrosServersHost() , this.eDeliveryServerConfiguration.getDocumentServerPort());
        WMTErrRetType errorLoc = new WMTErrRetType(Errors.WM_SUCCESS, Errors.WMS_SUCCESS);
        try{
        	ds.initSetSockets();
            errorLoc = ds.GetNextId(objectType);
            throwDSError(errorLoc, "getPNextId");
            if (errorLoc.hasNoErrors()){
                nextId = ((Integer)errorLoc.getData().get(0)).intValue();
            }
        }
        catch(Exception e){
            throw new DSException(e.getMessage());
        }
        finally{
        	ds.finalMySockets();
        }              
        return nextId;
      
    }
    
    
    /**
     * Εισάγει ένα έγγραφο στον document server. Δεν πραγματοποιεί εγγραφές στην βάση.
     *
     * @param userId    Ο χρήστης που πραγματοποιεί την ενέργεια.
     * @param docId     Το id του εγγράφου.
     * @param docPageNo Η σελίδα του εγγράφου.
     * @param typeId    Ο τύπος του εγγράφου
     * @param deviceId  το id του device
     * @param inStream  Το input Stream.
     * @return Επιστρέφονται τα αποτελέσματα της κλήσης.
     * @author Αναγνωστόπουλος Γεώργιος
     */
    public  WMTErrRetType putDocumentCall(int userId, int docId, int docPageNo, int typeId, int deviceId, InputStream inStream) {
        WMTErrRetType errorLoc = new WMTErrRetType(Errors.WM_SUCCESS, Errors.WMS_SUCCESS);
        DocumentServices ds = new DocumentServices(this.eDeliveryServerConfiguration.getPapyrosServersHost() , this.eDeliveryServerConfiguration.getDocumentServerPort());

        try {
        	ds.initSetSockets();
            errorLoc = ds.PutDocument(userId, docId, docPageNo, typeId, deviceId, inStream);

        } finally {
        	ds.finalMySockets();
        }
        return errorLoc;
    }
    
    
    
    
    public  DocumentApi insertCall(int userId, String filename, String fileDescription, File file)
			throws FileNotFoundException, DSException {

    	DocumentApi docApi = new DocumentApi();
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			docApi = insertCall(userId, filename, fileDescription, fin);
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return docApi;
	}
    
    
    public  DocumentApi insertCall(int userId, String filename, String fileDescription, InputStream inStream) throws DSException {

    	DocumentServices df = new DocumentServices(this.eDeliveryServerConfiguration.getPapyrosServersHost() , this.eDeliveryServerConfiguration.getDocumentServerPort());		WMTErrRetType errorLoc = new WMTErrRetType(Errors.WM_SUCCESS, Errors.WMS_SUCCESS);
		errorLoc = df.InsertDocument(userId, filename, fileDescription, inStream, 0);// Κάνει
																						// init
																						// εσωτερικά

		DocumentApi docApi = new DocumentApi();
		throwDSError(errorLoc,"insertCall");
		docApi.setId(df.getDocId());
		LOG.log(Level.INFO,new Gson().toJson(docApi));
		errorLoc.setDoc(docApi);
		return docApi;
	}
    

}
