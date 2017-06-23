package com.edelivery.edeliveryserver.db.entityhandlers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;
import com.edelivery.edeliveryserver.db.models.Tables;

@ApplicationScoped
public class MessageSendHandlerDB {
	
	private static final Logger LOGGER = Logger.getLogger( MessageSendHandlerDB.class.getName() );
	 
	public MessageSendHandlerDB() {
	}
	 
	public MessageSendToAp insert(MessageSendToAp input) throws SQLException {
		String sql = "insert into "+Tables.message_send_to_ap+" (message_unique_id) values(?)";
		LOGGER.log(Level.INFO,sql);
		try (Connection conn = ConstantsDB.getElds().getConnection() ; PreparedStatement preparedStatement =conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
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
