package com.edelivery.edeliveryserver.business;

import java.sql.Connection;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

 
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;

@ApplicationScoped
public class SendMessageBS {
	
	@Inject
	DocumentSendHandlerDB   docSendHandler;
	
	
	@Inject
	MessageSendHandlerDB messageHandler;
	
	 
	
	public SendMessageBS(){
		
	}
	@Inject
	public SendMessageBS(DocumentSendHandlerDB   docSendHandler, MessageSendHandlerDB messageHandler
			 
			){
		 
		this.docSendHandler = docSendHandler;
		this.messageHandler = messageHandler;
	}
	
	public void insertMessage2Send(DocumentsSend docSend, Connection conn) throws SQLException{
		boolean closeConnection =false;
		if(conn==null){ //TODO change 
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try{
			//connWrapper.beginTransaction();
			docSendHandler.insert(docSend,conn);
			MessageSendToAp input = new MessageSendToAp();
			input.setMessageUniqueId(docSend.getMessageUniqueId());
			messageHandler.insert(input);
			//connWrapper.commitTransaction();
		}
		catch(Exception ex){
			conn.rollback();
			ex.printStackTrace();
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
	}
	
	public DocumentsSend selectNextById(DocumentStatuses status, Connection conn) throws SQLException{
		return this.docSendHandler.selectNextById(status, conn);
	}
	
}
