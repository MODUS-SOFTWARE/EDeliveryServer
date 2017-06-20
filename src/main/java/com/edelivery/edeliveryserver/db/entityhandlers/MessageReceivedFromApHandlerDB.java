package com.edelivery.edeliveryserver.db.entityhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentsReceived;
import com.edelivery.edeliveryserver.db.models.MessageReceivedFromAp;

@RequestScoped
public class MessageReceivedFromApHandlerDB {

	public MessageReceivedFromApHandlerDB(){
		
	}
	ConnectionWrapper connWrapper; 
	public MessageReceivedFromApHandlerDB(ConnectionWrapper connWrap){
		this.connWrapper = connWrap;
	}
	
	public List<MessageReceivedFromAp> select(List<MessageReceivedFromAp> all) throws SQLException{
		List<MessageReceivedFromAp> msgList2Get = new ArrayList<MessageReceivedFromAp>();
		List<MessageReceivedFromAp> msgListReceived = null;
		String uniqueIds = "";
		String sql = "SELECT  id,message_unique_id\r\n" + 
				"FROM edeliveryserver.message_received_from_ap\r\n" ;
		try (PreparedStatement preparedStatement = ConstantsDB.getElds().getConnection().prepareStatement(sql);) {
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
					if(msgListReceived.get(j).getMessageUniqueId().equals(mpTemp.getMessageUniqueId())){
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
	
	
	
	public MessageReceivedFromAp map(ResultSet resultSet) throws SQLException {
		MessageReceivedFromAp messageReceivedFromAp = new MessageReceivedFromAp();
		messageReceivedFromAp.setId(resultSet.getInt("id"));
		messageReceivedFromAp.setMessageUniqueId(resultSet.getString("message_unique_id"));
		return messageReceivedFromAp;
	}
	
	
	
}
