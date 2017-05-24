package com.modus.edeliveryserver.db.factories;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class PapyrosDataSource {
	DataSource papyrosDatasource;

	
	public DataSource getPapyrosDatasource() {
		return papyrosDatasource;
	}


	public void setPapyrosDatasource(DataSource papyrosDatasource) {
		this.papyrosDatasource = papyrosDatasource;
	}


	public Connection getConnection() throws SQLException{
		return papyrosDatasource.getConnection();
	}
}
