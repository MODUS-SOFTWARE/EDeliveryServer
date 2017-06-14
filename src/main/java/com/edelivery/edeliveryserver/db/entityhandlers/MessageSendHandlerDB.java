package com.edelivery.edeliveryserver.db.entityhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;

public class MessageSendHandlerDB {
	
	private static final Logger LOGGER = Logger.getLogger( MessageSendHandlerDB.class.getName() );
	ConnectionWrapper connWrapper;

	public MessageSendHandlerDB() {
	}

	@Inject
	public MessageSendHandlerDB(ConnectionWrapper connWrapper) {
		this.connWrapper = connWrapper;
	}
	
	public MessageSendToAp insert(MessageSendToAp input) throws SQLException {

		String sql = "insert into edeliveryserver.message_send_to_ap (message_unique_id) values(?)";
				
		LOGGER.log(Level.INFO,sql);
		try (PreparedStatement preparedStatement = this.connWrapper.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			preparedStatement.setString(1, input.getMessageUniqueId());
			preparedStatement.executeUpdate();
			try (ResultSet rs = preparedStatement.getGeneratedKeys();) {
				if (rs.next()) {
					input.setId(rs.getInt(1));
				}

			}
			return input;
		}
	}
}
