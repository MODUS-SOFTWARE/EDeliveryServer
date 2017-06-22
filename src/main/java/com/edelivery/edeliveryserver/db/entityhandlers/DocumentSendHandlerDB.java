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
import com.edelivery.edeliveryserver.db.models.DocumentStatuses;
import com.edelivery.edeliveryserver.db.models.DocumentsSend;
import com.edelivery.edeliveryserver.db.models.Tables;


@RequestScoped
public class DocumentSendHandlerDB {
	private static final Logger LOGGER = Logger.getLogger( DocumentSendHandlerDB.class.getName() );
 

	public DocumentSendHandlerDB() {
	}
 
	// TODO make it with entity manager.

	public DocumentsSend selectByMsgId(int mes_id, Connection conn) throws SQLException {
		DocumentsSend docSend = null;

		String query = "SELECT id " + " ,actual_document_filepath " + " ,doc_acceptance_period "
				+ " ,doc_authority_applicant " + " ,doc_comments " + " ,doc_description "
				+ " ,doc_etiquette_creation_date " + " ,doc_issuing_authority "
				+ " ,doc_issuing_organization " + " ,doc_language " + " ,doc_organization_applicant "
				+ " ,doc_organization_etiquette " + " ,doc_purpose " + " ,doc_received_from_ap_date "
				+ " ,doc_receiver_authority " + " ,doc_receiver_organization "
				+ " ,doc_submited_to_ap_date " + " ,doc_title " + " ,doc_type "
				+ " ,doc_valid_period " + " ,mes_id " + " ,mes_unique_id "
				+ " ,ref_document_unique_id " + " ,doc_status " + " ,docId "
				+ " FROM "+Tables.documents_send+" WHERE mes_id = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setInt(1, mes_id);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					docSend = map(resultSet);
				}
			}
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		
		return docSend;
	}

	public DocumentsSend selectByDocId(int mes_id, Connection conn) throws SQLException {
		DocumentsSend docSend = null;

		
		String query = "SELECT id " + " ,actual_document_filepath " + " ,doc_acceptance_period "
				+ " ,doc_authority_applicant " + " ,doc_comments " + " ,doc_description "
				+ " ,doc_etiquette_creation_date " + " ,doc_issuing_authority "
				+ " ,doc_issuing_organization " + " ,doc_language " + " ,doc_organization_applicant "
				+ " ,doc_organization_etiquette " + " ,doc_purpose " + " ,doc_received_from_ap_date "
				+ " ,doc_receiver_authority " + " ,doc_receiver_organization "
				+ " ,doc_submited_to_ap_date " + " ,doc_title " + " ,doc_type "
				+ " ,doc_valid_period " + " ,mes_id " + " ,mes_unique_id "
				+ " ,ref_document_unique_id " + " ,doc_status " + " ,docId "
				+ " FROM "+Tables.documents_send+" WHERE docId = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setInt(1, mes_id);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					docSend = map(resultSet);
				}
			}
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}	
		}
		
		return docSend;
	}
	public DocumentsSend updateStatus(DocumentsSend data, Connection conn) throws SQLException{
		String sql = "update "+Tables.documents_send+" set doc_status = ?  where mes_unique_id = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection = true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
			preparedStatement.setString(1, data.getDocumentStatus().getStatus());
			preparedStatement.setString(2, data.getMessageUniqueId());
			preparedStatement.executeUpdate();
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		return data;
	} 
	
	public DocumentsSend selectNextById(DocumentStatuses status , Connection conn) throws SQLException {
		DocumentsSend docSend = null;

		String query = "SELECT   a.id " + " ,a.actual_document_filepath " + " ,a.doc_acceptance_period "
				+ " ,a.doc_authority_applicant " + " ,a.doc_comments " + " ,a.doc_description "
				+ " ,a.doc_etiquette_creation_date " + " ,a.doc_issuing_authority "
				+ " ,a.doc_issuing_organization " + " ,a.doc_language "
				+ " ,a.doc_organization_applicant " + " ,a.doc_organization_etiquette "
				+ " ,a.doc_purpose " + " ,a.doc_received_from_ap_date " + " ,a.doc_receiver_authority "
				+ " ,a.doc_receiver_organization " + " ,a.doc_submited_to_ap_date " + " ,a.doc_title "
				+ " ,a.doc_type " + " ,a.doc_valid_period " + " ,a.mes_id " + " ,a.mes_unique_id "
				+ " ,a.ref_document_unique_id " + " ,a.doc_status " + " ,a.docId "
				+ " FROM "+Tables.documents_send+"  a  "
		//		+ "inner join "+Tables.message_send_to_ap+"  b on   b.message_unique_id = a.mes_unique_id "
				+ "Where doc_status = ? "
				+ " ORDER BY a.id  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection = true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setInt(1, status.getValue());
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					docSend = map(resultSet);
				}
			}
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		return docSend;
	}

	public DocumentsSend map(ResultSet resultSet) throws SQLException {
		DocumentsSend docSend = new DocumentsSend();
		docSend.setId(resultSet.getInt("id"));
		docSend.setMessageId(resultSet.getInt("mes_id"));
		docSend.setMessageUniqueId(resultSet.getString("mes_unique_id"));
		docSend.setDocumentDescription(resultSet.getString("doc_description"));
		docSend.setDocumentTitle(resultSet.getString("doc_title"));
		docSend.setDocumentType(resultSet.getString("doc_type"));
		docSend.setDocId(resultSet.getInt("docId"));
		docSend.setDocumentIssuingAuthority(resultSet.getString("doc_issuing_authority"));
		docSend.setDocumentIssuingOrganization(resultSet.getString("doc_issuing_organization"));
		docSend.setDocumentReceiverAuthority(resultSet.getString("doc_receiver_authority"));
		docSend.setDocumentReceiverOrganization(resultSet.getString("doc_receiver_organization"));
		docSend.setActualDocumentFilepath(resultSet.getString("actual_document_filepath"));

		return docSend;
	}

	public DocumentsSend insert(DocumentsSend input,Connection conn) throws SQLException {

		String sql = "insert into "+Tables.documents_send+" (actual_document_filepath,docId,doc_acceptance_period,doc_authority_applicant,doc_comments\r\n"
				+ ",doc_description,doc_etiquette_creation_date,doc_issuing_authority,doc_issuing_organization,doc_language\r\n"
				+ ",doc_organization_applicant,doc_organization_etiquette,doc_purpose,doc_received_from_ap_date,doc_receiver_authority\r\n"
				+",doc_receiver_organization,doc_submited_to_ap_date,doc_title,doc_type,doc_valid_period,mes_id\r\n"
				+

				",mes_unique_id,ref_document_unique_id,doc_status\r\n)" + "values (?,?,?,?,?"
				+ ",?,?,?,?,?" + ",?,?,?,?,?" + ",?,?,?,?,?" + ",?,?,?,?" + ")";
		LOGGER.log(Level.INFO,sql);
		boolean closeConnection = false; 
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			preparedStatement.setString(1, input.getActualDocumentFilepath());
			preparedStatement.setInt(2, input.getDocId());
			long acceptedPeriod = input.getDocumentAcceptancePeriod() == null ? 0
					: input.getDocumentAcceptancePeriod().getTime();
			preparedStatement.setDate(3, new java.sql.Date(acceptedPeriod));
			preparedStatement.setString(4, input.getDocumentAuthorityApplicant());
			preparedStatement.setString(5, input.getDocumentComments());
			preparedStatement.setString(6, input.getDocumentDescription());
			long documentEtiquette = input.getDocumentEtiquetteCreationDate() == null ? 0
					: input.getDocumentEtiquetteCreationDate().getTime();
			preparedStatement.setDate(7, new java.sql.Date(documentEtiquette));
			preparedStatement.setString(8, input.getDocumentIssuingAuthority());
			preparedStatement.setString(9, input.getDocumentIssuingOrganization());
			preparedStatement.setString(10, input.getDocumentLanguage());
			preparedStatement.setString(11, input.getDocumentOrganizationApplicant());
			preparedStatement.setString(12, input.getDocumentOrganizationEtiquette());
			preparedStatement.setString(13, input.getDocumentPurpose());
			long documentReceivedFromApDate = input.getDocumentReceivedFromApDate() == null ? 0
					: input.getDocumentReceivedFromApDate().getTime();
			preparedStatement.setDate(14, new java.sql.Date(documentReceivedFromApDate));
			preparedStatement.setString(15, input.getDocumentReceiverAuthority());

			preparedStatement.setString(16, input.getDocumentReceiverOrganization());
			long documentSubmitedToApDate = input.getDocumentSubmitedToApDate() == null ? 0
					: input.getDocumentEtiquetteCreationDate().getTime();
			preparedStatement.setDate(17, new java.sql.Date(documentSubmitedToApDate));
			preparedStatement.setString(18, input.getDocumentTitle());
			preparedStatement.setString(19, input.getDocumentType());
			long documentValidPeriod = input.getDocumentValidPeriod() == null ? 0
					: input.getDocumentValidPeriod().getTime();
			preparedStatement.setLong(20, documentValidPeriod);
			preparedStatement.setInt(21, input.getMessageId());
			preparedStatement.setString(22, input.getMessageUniqueId());
			preparedStatement.setString(23, input.getReferencedDocumentUniqueId());
			preparedStatement.setString(24, input.getDocumentStatus().getStatus());

			preparedStatement.executeUpdate();
			/*try (ResultSet rs = preparedStatement.getGeneratedKeys();) {
				if (rs.next()) {
					input.setId(rs.getInt(1));
				}

			}*/ //Πρόβλημα στην oracle

			
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		return input;
		
	}

}