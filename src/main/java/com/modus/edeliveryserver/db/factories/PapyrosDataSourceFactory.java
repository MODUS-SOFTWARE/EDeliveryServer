package com.modus.edeliveryserver.db.factories;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;

@ApplicationScoped
public class PapyrosDataSourceFactory {
	EDeliveryServerConfiguration config;
	PapyrosDataSource pdatasource;
	public PapyrosDataSourceFactory() {

	}

	@Inject
	public PapyrosDataSourceFactory(EDeliveryServerConfiguration configp) {
		config = configp;
	}

	@Produces
	public PapyrosDataSource getDatasource() {
		String dataSourceString = "";

		try {
			dataSourceString = this.config.getEDeliveryServerProperties().getString("DBPapyros");
			if (pdatasource == null) {
				InitialContext context = new InitialContext();
				DataSource ds = (DataSource) context.lookup(dataSourceString);
				pdatasource = new PapyrosDataSource();
				pdatasource.setPapyrosDatasource(ds);
				
			}
		} catch (NamingException nameex) {
			// LOG.log(Level.SEVERE,"DataSource NamingException exception
			// Datasource :"+dataSourceString+" "+nameex.getMessage());
			nameex.printStackTrace();
		}  catch (Exception ex) {
			// LOG.log(Level.SEVERE,"DataSource Exception "+new
			// Gson().toJson(this.xmlSettings)+" "+ex.getMessage());
		}

		return pdatasource;
	}


}
