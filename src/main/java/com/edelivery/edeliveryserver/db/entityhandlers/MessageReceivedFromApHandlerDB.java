package com.edelivery.edeliveryserver.db.entityhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.configuration.EDeliveryServerConfiguration;
import com.edelivery.edeliveryserver.configuration.EdeliverySettings;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentsReceived;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;
import com.edelivery.edeliveryserver.db.models.Tables;

@ApplicationScoped
public class MessageReceivedFromApHandlerDB {
	private static final Logger LOGGER = Logger.getLogger(MessageReceivedFromApHandlerDB.class.getName());

	@Inject 
	EDeliveryServerConfiguration eDeliveryServerConfiguration;
	
	public MessageReceivedFromApHandlerDB(){
		
	}
	 
	public List<MessageReceivedFromAp> select(List<MessageReceivedFromAp> all) throws SQLException{
		List<MessageReceivedFromAp> msgList2Get = new ArrayList<MessageReceivedFromAp>();
		List<MessageReceivedFromAp> msgListReceived = new ArrayList<MessageReceivedFromAp>();
		String uniqueIds = "";
		String sql = "SELECT  id,mes_unique_id,mes_ap_unique_id\r\n" + 
				"FROM "+Tables.documents_received+ "\r\n"
				+ "where mes_ap_unique_id is not null " ;
		try (Connection conn = ConstantsDB.getElds().getConnection() ;PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					MessageReceivedFromAp mp = map(resultSet);
					msgListReceived.add(mp);
				}
			}
		}		
		for(int i = 0; i < all.size();i++){
			MessageReceivedFromAp mpTemp = all.get(i);
			boolean found = false;
			if(msgListReceived!=null){
				for(int j=0;j<msgListReceived.size();j++){
					if(msgListReceived.get(j).getMessageApUniqueId().equals(mpTemp.getMessageApUniqueId())){
						found=true;
						break;
					}
				}
			}
			if(!found){
				msgList2Get.add(mpTemp);
			}
		}
		
		return msgList2Get;
	}
	
	
	
	
	public DocumentsReceived insert(DocumentsReceived input , Connection conn) throws SQLException{
		String sql = "insert into "+Tables.message_received_from_ap +" (message_unique_id)" + "values (?"
				+ ")";
		LOGGER.log(Level.INFO,sql);
		boolean closeConnection = false; 
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			preparedStatement.setString(1, input.getMessageUniqueApId());
			preparedStatement.executeUpdate();
			/*try (ResultSet rs = preparedStatement.getGeneratedKeys();) {
				if (rs.next()) {
					input.setId(rs.getInt(1));
				}

			}*/

			return input;
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
	}
	
	
	/*INSERT INTO PFLDDOC (FOLDER_ID, FLDDOC_ID, FLDDOC_DESCRIPTION, FLDDOC_INSDATE, FLDDOC_USER_ID)
   VALUES ((select folder_id from pfolders where folder_name like 'E Delivery (inbox)'), 1222, 'Description', GETDATE(), 100000)*/

	
	public void insertFolder(int docId , Connection conn) throws SQLException{
		
		String sqlOralce  = "INSERT INTO PFLDDOC (FOLDER_ID, FLDDOC_ID, FLDDOC_DESCRIPTION, FLDDOC_INSDATE, FLDDOC_USER_ID) " + 
				"   VALUES ((select folder_id from pfolders where folder_name like 'E Delivery (inbox)'), ?, 'Description', SYSDATE, 100000)";
		
		
		String sqlMSSQL = "INSERT INTO PFLDDOC (FOLDER_ID, FLDDOC_ID, FLDDOC_DESCRIPTION, FLDDOC_INSDATE, FLDDOC_USER_ID) " + 
				"   VALUES ((select folder_id from pfolders where folder_name like 'E Delivery (inbox)'), ?, 'Description', GETDATE(), 100000)";
		String sql = sqlOralce;
		if(this.eDeliveryServerConfiguration.getDB().equalsIgnoreCase("SQL")){
			sql = sqlMSSQL;
		}
		
		LOGGER.log(Level.INFO,sql);
		boolean closeConnection = false; 
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql );) {
			preparedStatement.setInt(1, docId);
			preparedStatement.executeUpdate();
			/*try (ResultSet rs = preparedStatement.getGeneratedKeys();) {
				if (rs.next()) {
					input.setId(rs.getInt(1));
				}

			}*/

			
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
	}

	
	
	public MessageReceivedFromAp map(ResultSet resultSet) throws SQLException {
		MessageReceivedFromAp messageReceivedFromAp = new MessageReceivedFromAp();
		messageReceivedFromAp.setId(resultSet.getInt("id"));
		messageReceivedFromAp.setMessageApUniqueId(resultSet.getString("mes_ap_unique_id"));
		messageReceivedFromAp.setMessageUniqueId(resultSet.getString("mes_unique_id"));
		return messageReceivedFromAp;
	}
	
	
	
}
