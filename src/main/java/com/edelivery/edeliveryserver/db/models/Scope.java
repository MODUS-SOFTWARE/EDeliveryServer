package com.edelivery.edeliveryserver.db.models;

public class Scope {
	int id = 0;
	String sc_type;
	String sc_id;
	String sc_instance;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSc_type() {
		return sc_type;
	}
	public void setSc_type(String sc_type) {
		this.sc_type = sc_type;
	}
	public String getSc_id() {
		return sc_id;
	}
	public void setSc_id(String sc_id) {
		this.sc_id = sc_id;
	}
	public String getSc_instance() {
		return sc_instance;
	}
	public void setSc_instance(String sc_instance) {
		this.sc_instance = sc_instance;
	}
	
	
}
