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
	
	public DocumentsSend selectByMsgId(int message_id) throws SQLException{
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
	      " ,docId "+
	      " FROM edeliveryserver.edeliveryserver.documents_send WHERE message_id = ?  ";
		
		try (PreparedStatement preparedStatement = ds.getConnection().prepareStatement(query);) {
			preparedStatement.setInt(1, message_id);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					docSend=map(resultSet);
				}
			}
		}
		return docSend;
	} 
	
	

	public DocumentsSend selectByDocId(int message_id) throws SQLException{
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
	      " ,docId "+
	      " FROM edeliveryserver.edeliveryserver.documents_send WHERE docId = ?  ";
		
		try (PreparedStatement preparedStatement = ds.getConnection().prepareStatement(query);) {
			preparedStatement.setInt(1, message_id);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					docSend=map(resultSet);
				}
			}
		}
		return docSend;
	} 
	
	public DocumentsSend selectNextById() throws SQLException{
		DocumentsSend docSend = null;
		
		String query = "SELECT TOP 1  a.id "+
	      " ,a.actual_document_filepath "+
	      " ,a.document_acceptance_period "+
	      " ,a.document_authority_applicant "+
	      " ,a.document_comments "+
	      " ,a.document_description "+
	      " ,a.document_etiquette_creation_date "+
	      " ,a.document_issuing_authority "+
	      " ,a.document_issuing_organization "+
	      " ,a.document_language "+
	      " ,a.document_organization_applicant " +
	      " ,a.document_organization_etiquette "+ 
	      " ,a.document_purpose "+
	      " ,a.document_received_from_ap_date "+
	      " ,a.document_receiver_authority "+
	      " ,a.document_receiver_organization "+
	      " ,a.document_submited_to_ap_date "+
	      " ,a.document_title "+
	      " ,a.document_type " +
	      " ,a.document_valid_period "+
	      " ,a.message_id "+
	      " ,a.message_unique_id "+
	      " ,a.referenced_document_unique_id "+
	      " ,a.document_status "+
	      " ,a.docId "+
	      " FROM edeliveryserver.edeliveryserver.documents_send  a  "
	      + "inner join edeliveryserver.message_send_to_ap  b on   b.message_unique_id = a.message_unique_id "
	      + " ORDER BY b.id  ";
		
		try (PreparedStatement preparedStatement = ds.getConnection().prepareStatement(query);) {
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					docSend=map(resultSet);
				}
			}
		}
		return docSend;
	} 
	
	public DocumentsSend map(ResultSet resultSet) throws SQLException{
		DocumentsSend docSend = new DocumentsSend();
		docSend.setId(resultSet.getInt("id"));
		docSend.setMessageId(resultSet.getInt("message_id"));
		docSend.setDocumentDescription(resultSet.getString("document_description"));
		docSend.setDocumentTitle(resultSet.getString("document_title"));
		docSend.setDocumentType(resultSet.getString("document_type"));
		docSend.setDocId(resultSet.getInt("docId"));
		docSend.setDocumentIssuingAuthority(resultSet.getString("document_issuing_authority"));
		docSend.setDocumentIssuingOrganization(resultSet.getString("document_issuing_organization"));
		docSend.setDocumentReceiverAuthority(resultSet.getString("document_receiver_authority"));
		docSend.setDocumentReceiverOrganization(resultSet.getString("document_receiver_organization"));
		docSend.setActualDocumentFilepath(resultSet.getString("actual_document_filepath"));
		
		return docSend;
	}
	
}
