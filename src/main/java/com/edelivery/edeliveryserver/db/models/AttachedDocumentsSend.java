/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edelivery.edeliveryserver.db.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pantelispanka
 */

public class AttachedDocumentsSend implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String actualDocumentFilepath;
    private String referenceDocument;
    private Integer docId;
    

    public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public String getReferenceDocument() {
		return referenceDocument;
	}

	public void setReferenceDocument(String referenceDocument) {
		this.referenceDocument = referenceDocument;
	}

	public AttachedDocumentsSend() {
    }

    public AttachedDocumentsSend(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActualDocumentFilepath() {
        return actualDocumentFilepath;
    }

    public void setActualDocumentFilepath(String actualDocumentFilepath) {
        this.actualDocumentFilepath = actualDocumentFilepath;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttachedDocumentsSend)) {
            return false;
        }
        AttachedDocumentsSend other = (AttachedDocumentsSend) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.AttachedDocumentsSend[ id=" + id + " ]";
    }
    
}
