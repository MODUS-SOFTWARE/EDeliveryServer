package com.edelivery.edeliveryserver.business;

import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.db.entityhandlers.ConnectionWrapper;
import com.edelivery.edeliveryserver.db.entityhandlers.DocumentSendHandlerDB;
import com.edelivery.edeliveryserver.db.entityhandlers.MessageSendHandlerDB;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.MessageSendToAp;

@ApplicationScoped
public class SendMessageBS {
	
	@Inject
	DocumentSendHandlerDB   docSendHandler;
	
	@Inject 
	ConnectionWrapper connWrapper;
	@Inject
	MessageSendHandlerDB messageHandler;
	
	public SendMessageBS(){
		
	}
	
	public SendMessageBS(ConnectionWrapper connWrapper,DocumentSendHandlerDB   docSendHandler, MessageSendHandlerDB messageHandler){
		this.connWrapper  = connWrapper;
		this.docSendHandler = docSendHandler;
		this.messageHandler = messageHandler;
	}
	
	public void insertMessage2Send(DocumentsSend docSend) throws SQLException{
		try{
			connWrapper.beginTransaction();
			docSendHandler.insert(docSend);
			MessageSendToAp input = new MessageSendToAp();
			input.setMessageUniqueId(docSend.getMessageUniqueId());
			messageHandler.insert(input);
			connWrapper.commitTransaction();
		}
		catch(Exception ex){
			connWrapper.rollbackTransaction();
			ex.printStackTrace();
		}
	}
	
	public DocumentsSend selectNextById() throws SQLException{
		return this.docSendHandler.selectNextById();
	}
	
}
