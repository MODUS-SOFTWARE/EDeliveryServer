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
import com.modus.edeliveryserver.db.factories.EdeliveryDatasource;

@RequestScoped
public class DocumentSendHandlerDB {
	private static final Logger LOGGER = Logger.getLogger( DocumentSendHandlerDB.class.getName() );
	ConnectionWrapper connWrapper;

	public DocumentSendHandlerDB() {
	}

	@Inject
	public DocumentSendHandlerDB(ConnectionWrapper connWrapper) {
		this.connWrapper = connWrapper;
	}
	// TODO make it with entity manager.

	public DocumentsSend selectByMsgId(int message_id, Connection conn) throws SQLException {
		DocumentsSend docSend = null;

		String query = "SELECT id " + " ,actual_document_filepath " + " ,document_acceptance_period "
				+ " ,document_authority_applicant " + " ,document_comments " + " ,document_description "
				+ " ,document_etiquette_creation_date " + " ,document_issuing_authority "
				+ " ,document_issuing_organization " + " ,document_language " + " ,document_organization_applicant "
				+ " ,document_organization_etiquette " + " ,document_purpose " + " ,document_received_from_ap_date "
				+ " ,document_receiver_authority " + " ,document_receiver_organization "
				+ " ,document_submited_to_ap_date " + " ,document_title " + " ,document_type "
				+ " ,document_valid_period " + " ,message_id " + " ,message_unique_id "
				+ " ,referenced_document_unique_id " + " ,document_status " + " ,docId "
				+ " FROM "+Tables.documents_send+" WHERE message_id = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setInt(1, message_id);
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

	public DocumentsSend selectByDocId(int message_id, Connection conn) throws SQLException {
		DocumentsSend docSend = null;

		
		String query = "SELECT id " + " ,actual_document_filepath " + " ,document_acceptance_period "
				+ " ,document_authority_applicant " + " ,document_comments " + " ,document_description "
				+ " ,document_etiquette_creation_date " + " ,document_issuing_authority "
				+ " ,document_issuing_organization " + " ,document_language " + " ,document_organization_applicant "
				+ " ,document_organization_etiquette " + " ,document_purpose " + " ,document_received_from_ap_date "
				+ " ,document_receiver_authority " + " ,document_receiver_organization "
				+ " ,document_submited_to_ap_date " + " ,document_title " + " ,document_type "
				+ " ,document_valid_period " + " ,message_id " + " ,message_unique_id "
				+ " ,referenced_document_unique_id " + " ,document_status " + " ,docId "
				+ " FROM "+Tables.documents_send+" WHERE docId = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setInt(1, message_id);
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
		String sql = "update "+Tables.documents_send+" set document_status = ?  where id = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			conn.setAutoCommit(false);
			closeConnection = true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
			preparedStatement.setInt(1, data.getDocumentStatus().getId());
			preparedStatement.setInt(2, data.getId());
			preparedStatement.executeUpdate();
			conn.commit();
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

		String query = "SELECT TOP 1  a.id " + " ,a.actual_document_filepath " + " ,a.document_acceptance_period "
				+ " ,a.document_authority_applicant " + " ,a.document_comments " + " ,a.document_description "
				+ " ,a.document_etiquette_creation_date " + " ,a.document_issuing_authority "
				+ " ,a.document_issuing_organization " + " ,a.document_language "
				+ " ,a.document_organization_applicant " + " ,a.document_organization_etiquette "
				+ " ,a.document_purpose " + " ,a.document_received_from_ap_date " + " ,a.document_receiver_authority "
				+ " ,a.document_receiver_organization " + " ,a.document_submited_to_ap_date " + " ,a.document_title "
				+ " ,a.document_type " + " ,a.document_valid_period " + " ,a.message_id " + " ,a.message_unique_id "
				+ " ,a.referenced_document_unique_id " + " ,a.document_status " + " ,a.docId "
				+ " FROM "+Tables.documents_send+"  a  "
				+ "inner join "+Tables.message_send_to_ap+"  b on   b.message_unique_id = a.message_unique_id "
				+ "Where document_status = ? "
				+ " ORDER BY b.id  ";
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
		docSend.setMessageId(resultSet.getInt("message_id"));
		docSend.setMessageUniqueId(resultSet.getString("message_unique_id"));
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

	public DocumentsSend insert(DocumentsSend input,Connection conn) throws SQLException {

		String sql = "insert into "+Tables.documents_send+" (actual_document_filepath,docId,document_acceptance_period,document_authority_applicant,document_comments\r\n"
				+ ",document_description,document_etiquette_creation_date,document_issuing_authority,document_issuing_organization,document_language\r\n"
				+ ",document_organization_applicant,document_organization_etiquette,document_purpose,document_received_from_ap_date,document_receiver_authority\r\n"
				+",document_receiver_organization,document_submited_to_ap_date,document_title,document_type,document_valid_period,message_id\r\n"
				+

				",message_unique_id,referenced_document_unique_id,document_status\r\n)" + "values (?,?,?,?,?"
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
			try (ResultSet rs = preparedStatement.getGeneratedKeys();) {
				if (rs.next()) {
					input.setId(rs.getInt(1));
				}

			}

			return input;
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		
	}

}