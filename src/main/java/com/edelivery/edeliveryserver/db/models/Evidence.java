package com.edelivery.edeliveryserver.db.models;

public class Evidence {
	int id = 0 ; 
	String actual_document_filepath ="";
	int docId  = 0 ; 
	String reference_document=""; //MESSAGE ID 
	String eventCode ="";
	String evidence_id = "";
	String evidence_name = "";
	String evidence_time = "";
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getActual_document_filepath() {
		return actual_document_filepath;
	}
	public void setActual_document_filepath(String actual_document_filepath) {
		this.actual_document_filepath = actual_document_filepath;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public String getReference_document() {
		return reference_document;
	}
	public void setReference_document(String reference_document) {
		this.reference_document = reference_document;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEvidence_id() {
		return evidence_id;
	}
	public void setEvidence_id(String evidence_id) {
		this.evidence_id = evidence_id;
	}
	public String getEvidence_name() {
		return evidence_name;
	}
	public void setEvidence_name(String evidence_name) {
		this.evidence_name = evidence_name;
	}
	public String getEvidence_time() {
		return evidence_time;
	}
	public void setEvidence_time(String evidence_time) {
		this.evidence_time = evidence_time;
	}
	
	
}
