package com.modus.edeliveryserver.db.factories;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
@ApplicationScoped
public class EdeliveryDatasourceFactory {
	EDeliveryServerConfiguration config;
	EdeliveryDatasource edatasource;

	public EdeliveryDatasourceFactory() {

	}

	@Inject
	public EdeliveryDatasourceFactory(EDeliveryServerConfiguration config) {
		config = config;
	}

	@Produces
	public EdeliveryDatasource getDatasource() {
		String dataSourceString = "";

		try {
			dataSourceString = this.config.getEDeliveryServerProperties().getString("DBEdelivery");
			if (edatasource == null) {
				InitialContext context = new InitialContext();
				DataSource ds = (DataSource) context.lookup(dataSourceString);
				edatasource = new EdeliveryDatasource();
				edatasource.setEdeliveryDatasource(ds);
				
			}
		} catch (NamingException nameex) {
			// LOG.log(Level.SEVERE,"DataSource NamingException exception
			// Datasource :"+dataSourceString+" "+nameex.getMessage());
			nameex.printStackTrace();
		}  catch (Exception ex) {
			// LOG.log(Level.SEVERE,"DataSource Exception "+new
			// Gson().toJson(this.xmlSettings)+" "+ex.getMessage());
		}

		return edatasource;
	}

}
