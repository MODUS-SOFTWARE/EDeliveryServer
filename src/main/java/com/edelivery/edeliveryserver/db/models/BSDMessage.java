package com.edelivery.edeliveryserver.db.models;

import java.util.Date;
import java.util.List;

public class BSDMessage {
	int id;
	String message_unique_id;
	Participants sender;
	Participants receiver;
	String di_standard;
	String di_type_version;
	String di_id;
	String di_type;
	Date di_creatiom_time;
	String man_type="";
	String man_uni="";
	String man_descr="";
	String man_lang;
	List<Scope> bsdScope ;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage_unique_id() {
		return message_unique_id;
	}
	public void setMessage_unique_id(String message_unique_id) {
		this.message_unique_id = message_unique_id;
	}
	public Participants getSender() {
		return sender;
	}
	public void setSender(Participants sender) {
		this.sender = sender;
	}
	public Participants getReceiver() {
		return receiver;
	}
	public void setReceiver(Participants receiver) {
		this.receiver = receiver;
	}
	public String getDi_standard() {
		return di_standard;
	}
	public void setDi_standard(String di_standard) {
		this.di_standard = di_standard;
	}
	public String getDi_type_version() {
		return di_type_version;
	}
	public void setDi_type_version(String di_type_version) {
		this.di_type_version = di_type_version;
	}
	public String getDi_id() {
		return di_id;
	}
	public void setDi_id(String di_id) {
		this.di_id = di_id;
	}
	public String getDi_type() {
		return di_type;
	}
	public void setDi_type(String di_type) {
		this.di_type = di_type;
	}
	public Date getDi_creatiom_time() {
		return di_creatiom_time;
	}
	public void setDi_creatiom_time(Date di_creatiom_time) {
		this.di_creatiom_time = di_creatiom_time;
	}
	public String getMan_type() {
		return man_type;
	}
	public void setMan_type(String man_type) {
		this.man_type = man_type;
	}
	public String getMan_uni() {
		return man_uni;
	}
	public void setMan_uni(String man_uni) {
		this.man_uni = man_uni;
	}
	public String getMan_descr() {
		return man_descr;
	}
	public void setMan_descr(String man_descr) {
		this.man_descr = man_descr;
	}
	public String getMan_lang() {
		return man_lang;
	}
	public void setMan_lang(String man_lang) {
		this.man_lang = man_lang;
	}
	public List<Scope> getBsdScope() {
		return bsdScope;
	}
	public void setBsdScope(List<Scope> bsdScope) {
		this.bsdScope = bsdScope;
	}
	
	
}
