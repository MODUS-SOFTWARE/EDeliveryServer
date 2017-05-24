package com.edelivery.edeliveryserver.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EdeliverySettings {

	//public static String configFilePath="c:/modus/edelivery/PapyrosEdelivery.properties";
	
	String papyrosServersHost;
	int documentServerPort;
	String tempFolder;
	
	
	public String getTempFolder() {
		if(!this.tempFolder.endsWith("\\") || this.tempFolder.endsWith("/")){
			this.tempFolder+="/";
		}
		return tempFolder;
	}
	public void setTempFolder(String tempFolder) {
		this.tempFolder = tempFolder;
	}
	public String getPapyrosServersHost() {
		return papyrosServersHost;
	}
	public void setPapyrosServersHost(String papyrosServersHost) {
		this.papyrosServersHost = papyrosServersHost;
	}
	public int getDocumentServerPort() {
		return documentServerPort;
	}
	public void setDocumentServerPort(int documentServerPort) {
		this.documentServerPort = documentServerPort;
	}
	
	
	public EdeliverySettings() {
	
	}
	
	public EdeliverySettings(String filepath) {
		try {
			load(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(String filepath) throws IOException{
		Properties config = new Properties();
		InputStream stream = null;
		try{
				stream = new FileInputStream(new File(filepath));
				config.load(stream);
		}
		finally{
			if(stream!=null){
				stream.close();
			}
		}
		try{
	    	this.papyrosServersHost =config.getProperty("PapyrosServersHost");
	    	this.documentServerPort = Integer.parseInt(config.getProperty("DocumentServerPort"));
	    	this.tempFolder = config.getProperty("TempFolder");
	    	
		}
		catch(NumberFormatException ex){
			ex.printStackTrace();
		}
	}
	
	
}
