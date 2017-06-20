package com.edelivery.edeliveryserver.db.entityhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.DocumentsReceived;

@RequestScoped
public class DocumentReceivedHandlerDB {
	private static final Logger LOGGER = Logger.getLogger(DocumentReceivedHandlerDB.class.getName());

	ConnectionWrapper connWrapper;

	public DocumentReceivedHandlerDB() {
	}

	@Inject
	public DocumentReceivedHandlerDB(ConnectionWrapper connWrapper) {
		this.connWrapper = connWrapper;
	}

	public DocumentsReceived insert(DocumentsReceived input) throws SQLException {

		String sql = " \r\n"
				+ "insert into edeliveryserver.documents_received (actual_document_filepath      ,docId      ,document_acceptance_period      ,document_authority_applicant\r\n"
				+ "      ,document_comments      ,document_description      ,document_etiquette_creation_date      ,document_issuing_authority      ,document_issuing_organization\r\n"
				+ "      ,document_language      ,document_organization_applicant      ,document_organization_etiquette      ,document_purpose      ,document_received_from_ap_date\r\n"
				+ "      ,document_receiver_authority      ,document_receiver_organization      ,document_submited_to_ap_date      ,document_title      ,document_type\r\n"
				+ "      ,document_valid_period      ,message_id      ,message_unique_id      ,referenced_document_unique_id      ,document_status)\r\n"
				+ "	  values( ? ,? ,? ,?," + "			  ? ,? ,? ,? ,?," + "			  ? ,? ,? ,? ,?,"
				+ "			  ? ,? ,? ,? ,?," + "			  ? ,? ,? ,? ,?" + "	  )" + "  ";
		LOGGER.log(Level.INFO, sql);
		try (Connection conn = ConstantsDB.getElds().getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);) {
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
	}
}
