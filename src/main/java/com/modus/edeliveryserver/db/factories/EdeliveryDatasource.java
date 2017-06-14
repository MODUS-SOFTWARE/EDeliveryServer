package com.modus.edeliveryserver.db.factories;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;


public class EdeliveryDatasource {
	DataSource edeliveryDatasource;

	public DataSource getEdeliveryDatasource() {
		return edeliveryDatasource;
	}

	public void setEdeliveryDatasource(DataSource edeliveryDatasource) {
		this.edeliveryDatasource = edeliveryDatasource;
	}
	
	public Connection getConnection() throws SQLException{
		return edeliveryDatasource.getConnection();
	}
	
	public void closeConnection() throws SQLException{
		edeliveryDatasource.getConnection().close();
	}
}
