package com.edelivery.edeliveryserver.db.models;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;

public class ConstantsDB {

	
	private  static DataSource elds;
	public static void setElds(DataSource elds) {
		ConstantsDB.elds = elds;
	}

	public static void setPapds(DataSource papds) {
		ConstantsDB.papds = papds;
	}

	private  static DataSource papds;
	
	
	public static DataSource getElds(){
		if(elds==null){
			initEDDatasource();
		}
		return  elds;
	}
	
	public static  DataSource getPapds(){
		if(papds==null){
			initPapDatasource();
		}
		return papds;
		
	}
	
	public static void initPapDatasource() {
		String dataSourceString = "";
		
		try {
			EDeliveryServerConfiguration configApp = new EDeliveryServerConfiguration(); 	
			configApp.load();
			dataSourceString = configApp.getEDeliveryServerProperties().getString("DBPapyros");
			if (ConstantsDB.papds == null) {
				InitialContext context = new InitialContext();
				DataSource ds = (DataSource) context.lookup(dataSourceString);
				ConstantsDB.papds = ds;
			}
		} catch (NamingException nameex) {
			// LOG.log(Level.SEVERE,"DataSource NamingException exception
			// Datasource :"+dataSourceString+" "+nameex.getMessage());
			nameex.printStackTrace();
		}  catch (Exception ex) {
			// LOG.log(Level.SEVERE,"DataSource Exception "+new
			// Gson().toJson(this.xmlSettings)+" "+ex.getMessage());
		}
	}
    
    public static void initEDDatasource() {
		String dataSourceString = "";

		try {
			EDeliveryServerConfiguration configApp = new EDeliveryServerConfiguration(); 	
			configApp.load();
			dataSourceString = configApp.getEDeliveryServerProperties().getString("DBEdelivery");
			if (ConstantsDB.elds == null) {
				InitialContext context = new InitialContext();
				DataSource ds = (DataSource) context.lookup(dataSourceString);
				ConstantsDB.elds = ds;
			}
		} catch (NamingException nameex) {
			// LOG.log(Level.SEVERE,"DataSource NamingException exception
			// Datasource :"+dataSourceString+" "+nameex.getMessage());
			nameex.printStackTrace();
		}  catch (Exception ex) {
			// LOG.log(Level.SEVERE,"DataSource Exception "+new
			// Gson().toJson(this.xmlSettings)+" "+ex.getMessage());
		}
	}
}
