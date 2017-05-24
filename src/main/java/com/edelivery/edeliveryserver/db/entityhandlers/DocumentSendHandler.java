package com.edelivery.edeliveryserver.db.entityhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;

@ApplicationScoped
public class DocumentSendHandler  
{

	EdeliveryDatasource ds ;
	
	public DocumentSendHandler(){
	}
	
	@Inject
	public DocumentSendHandler(EdeliveryDatasource ds){
		this.ds = ds;
	}
	//TODO make it with entity manager.
	
	public DocumentsSend getDocumentSendByMsgId(int message_id) throws SQLException{
		DocumentsSend docSend = null;
		
		String query = "SELECT id "+
	      " ,actual_document_filepath "+
	      " ,document_acceptance_period "+
	      " ,document_authority_applicant "+
	      " ,document_comments "+
	      " ,document_description "+
	      " ,document_etiquette_creation_date "+
	      " ,document_issuing_authority "+
	      " ,document_issuing_organization "+
	      " ,document_language "+
	      " ,document_organization_applicant " +
	      " ,document_organization_etiquette "+ 
	      " ,document_purpose "+
	      " ,document_received_from_ap_date "+
	      " ,document_receiver_authority "+
	      " ,document_receiver_organization "+
	      " ,document_submited_to_ap_date "+
	      " ,document_title "+
	      " ,document_type " +
	      " ,document_valid_period "+
	      " ,message_id "+
	      " ,message_unique_id "+
	      " ,referenced_document_unique_id "+
	      " ,document_status "+
	      " FROM edeliveryserver.edeliveryserver.documents_send WHERE message_id = ?  ";
		
		try (PreparedStatement preparedStatement = ds.getConnection().prepareStatement(query);) {
			preparedStatement.setInt(1, message_id);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				while (resultSet.next()) {
					docSend = new DocumentsSend();
					docSend.setId(resultSet.getInt("id"));
					docSend.setMessageId(resultSet.getInt("message_id"));
					docSend.setDocumentDescription(resultSet.getString("document_description"));
					docSend.setDocumentTitle(resultSet.getString("document_title"));
					docSend.setDocumentType(resultSet.getString("document_type"));
				}
			}
		}
		return docSend;
	} 

}
