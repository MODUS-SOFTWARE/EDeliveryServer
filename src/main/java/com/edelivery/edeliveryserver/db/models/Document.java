package com.edelivery.edeliveryserver.db.models;

import java.util.Date;

public class Document {
	private Integer documentId;
    private String description;
    private String description_exact;
    

	private String versionDescription;
    private String versionDescription_exact;
    private String fileName;
    private String fileName_exact;
    

	private Date dateInserted;

    private int creator;

    private int type;
    private String extension;
    private Integer deviceId;
    private Integer param1;
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription_exact() {
		return description_exact;
	}
	public void setDescription_exact(String description_exact) {
		this.description_exact = description_exact;
	}
	public String getVersionDescription() {
		return versionDescription;
	}
	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}
	public String getVersionDescription_exact() {
		return versionDescription_exact;
	}
	public void setVersionDescription_exact(String versionDescription_exact) {
		this.versionDescription_exact = versionDescription_exact;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName_exact() {
		return fileName_exact;
	}
	public void setFileName_exact(String fileName_exact) {
		this.fileName_exact = fileName_exact;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getParam1() {
		return param1;
	}
	public void setParam1(Integer param1) {
		this.param1 = param1;
	}
}
