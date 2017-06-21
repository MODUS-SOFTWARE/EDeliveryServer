package com.edelivery.edeliveryserver.db.entityhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;

import com.edelivery.edeliveryserver.db.models.AttachedDocumentsSend;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.Evidence;
import com.edelivery.edeliveryserver.db.models.Tables;
@RequestScoped
public class EvidenceHandlerDB {
	private static final Logger LOGGER = Logger.getLogger( EvidenceHandlerDB.class.getName() );
	public EvidenceHandlerDB(){}
	
	public Evidence selectByMsgId(String reference_document, Connection conn) throws SQLException {
		Evidence evidence = null;

		String query = "SELECT id   ,actual_document_filepath  ,docId "
				+ " ,reference_document, eventcode , evidence_identifier ,evidence_name ,evidence_time  " 
				+ " FROM "+Tables.evidence_documents_send+" WHERE reference_document = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setString(1, reference_document);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					evidence = map(resultSet);
				}
			}
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		
		return evidence;
	}
	
	
	
	/*String query = "SELECT id   ,actual_document_filepath  ,docId "
				+ " ,reference_document, eventcode , evidence_identifier ,evidence_name ,evidence_time  " 
				+ " FROM "+Tables.evidence_documents_send+" WHERE reference_document = ?  ";*/
	public Evidence map(ResultSet resultSet) throws SQLException {
		Evidence evidence = new Evidence();
		evidence.setId(resultSet.getInt("id"));
		evidence.setActual_document_filepath(resultSet.getString("actual_document_filepath"));
		evidence.setDocId(resultSet.getInt("docId"));
		evidence.setEventCode(resultSet.getString("eventcode"));
		evidence.setEvidence_id(resultSet.getString("evidence_identifier"));
		evidence.setEvidence_name(resultSet.getString("evidence_name"));
		evidence.setEvidence_time(resultSet.getString("evidence_time"));
		return evidence;
	}

	public Evidence insert(Evidence input,Connection conn) throws SQLException {

		String sql = "insert into "+Tables.evidence_documents_send +" (actual_document_filepath  ,docId "
				+ ",reference_document, eventcode , evidence_identifier ,evidence_name ,evidence_time)" 
				+ "values (?,? ,   ? ,? ,? ,? ,?)";
		LOGGER.log(Level.INFO,sql);
		boolean closeConnection = false; 
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			preparedStatement.setString(1, input.getActual_document_filepath());
			preparedStatement.setInt(2, input.getDocId());
			preparedStatement.setString(3, input.getReference_document());
			preparedStatement.setString(4, input.getEventCode());
			preparedStatement.setString(5, input.getEvidence_id());
			preparedStatement.setString(6, input.getEvidence_name());
			preparedStatement.setString(7, input.getEvidence_time());
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
	
	
	/*public AttachedDocumentsSend update(AttachedDocumentsSend input,Connection conn) throws SQLException {

		String sql = "update "+Tables.attached_documents_send +"  set reference_document = ? "
				+ " , docId = ?  "
				+ " , actual_document_filepath = ?  "
				+ " Where id = ? "
				; 
		
		LOGGER.log(Level.INFO,sql);
		boolean closeConnection = false; 
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
			preparedStatement.setString(1, input.getReferenceDocument());
			preparedStatement.setInt(2, input.getDocId());
			preparedStatement.setString(3, input.getActualDocumentFilepath());
			preparedStatement.setInt(4, input.getId());
			preparedStatement.executeUpdate();
			return input;
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		
	}*/
}
