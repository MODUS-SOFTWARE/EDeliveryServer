package com.edelivery.edeliveryserver.db.entityhandlers;

import javax.enterprise.context.RequestScoped;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * This class wraps a {@link Connection} object to enable
 * shared usage according to scope.
 */
@RequestScoped
public class ConnectionWrapper {

    private static final Logger LOG = Logger.getLogger( ConnectionWrapper.class.getName() );

    @Inject
    private EdeliveryDatasource datasource;

    private Connection connection;

    public ConnectionWrapper(EdeliveryDatasource datasource){
    	this.datasource=datasource;
    }
    
    @PreDestroy
    public void destroy() {
        try {
            if (connection != null) {
            	connection.close();
            }
        } catch (SQLException e) {
            LOG.severe("Unable to close connection.");
        }
    }

    public void beginTransaction() throws SQLException {
        if (connection == null) {
            connection = datasource.getConnection();
        }
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        this.connection.commit();
        this.connection.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        this.connection.rollback();
        this.connection.setAutoCommit(true);
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = datasource.getConnection();
        }
        return connection;
    }
}