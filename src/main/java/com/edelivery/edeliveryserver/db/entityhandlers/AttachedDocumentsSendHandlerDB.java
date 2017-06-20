package com.edelivery.edeliveryserver.db.entityhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.edelivery.edeliveryserver.db.models.AttachedDocumentsSend;
import com.edelivery.edeliveryserver.db.models.ConstantsDB;
import com.edelivery.edeliveryserver.db.models.Tables;

public class AttachedDocumentsSendHandlerDB {
	private static final Logger LOGGER = Logger.getLogger( AttachedDocumentsSendHandlerDB.class.getName() );
	ConnectionWrapper connWrapper;
	
	@Inject
	public AttachedDocumentsSendHandlerDB(ConnectionWrapper connWrapper) {
		this.connWrapper = connWrapper;
	}
	// TODO make it with entity manager.

	public AttachedDocumentsSend selectByMsgId(String reference_document, Connection conn) throws SQLException {
		AttachedDocumentsSend attachedDocSend = null;

		String query = "SELECT id   ,actual_document_filepath  ,docId "
				+ " ,reference_document " 
				+ " FROM "+Tables.attached_documents_send+" WHERE reference_document = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setString(1, reference_document);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					attachedDocSend = map(resultSet);
				}
			}
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		
		return attachedDocSend;
	}
	
	public AttachedDocumentsSend selectById(int id, Connection conn) throws SQLException {
		AttachedDocumentsSend attachedDocSend = null;

		String query = "SELECT id   ,actual_document_filepath  ,docId "
				+ " ,reference_document " 
				+ " FROM "+Tables.attached_documents_send+" WHERE id = ?  ";
		boolean closeConnection = false;
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				if (resultSet.next()) {
					attachedDocSend = map(resultSet);
				}
			}
		}
		finally{
			if(conn !=null && closeConnection){
				conn.close();
			}
		}
		
		return attachedDocSend;
	}
	
	
	public AttachedDocumentsSend map(ResultSet resultSet) throws SQLException {
		AttachedDocumentsSend attachedDocSend = new AttachedDocumentsSend();
		attachedDocSend.setId(resultSet.getInt("id"));
		attachedDocSend.setActualDocumentFilepath(resultSet.getString("actual_document_filepath"));
		attachedDocSend.setReferenceDocument(resultSet.getString("reference_document"));
		attachedDocSend.setDocId(resultSet.getInt("docId"));
		return attachedDocSend;
	}

	public AttachedDocumentsSend insert(AttachedDocumentsSend input,Connection conn) throws SQLException {

		String sql = "insert into "+Tables.attached_documents_send +" (actual_document_filepath,docId,reference_document)" 
		+ "values (?,?,?)";
		LOGGER.log(Level.INFO,sql);
		boolean closeConnection = false; 
		if(conn==null){
			conn = ConstantsDB.getElds().getConnection();
			closeConnection=true;
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
			preparedStatement.setString(1, input.getActualDocumentFilepath());
			preparedStatement.setInt(2, input.getDocId());
			preparedStatement.setString(3, input.getReferenceDocument());

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
	
	
	public AttachedDocumentsSend update(AttachedDocumentsSend input,Connection conn) throws SQLException {

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
		
	}
}
