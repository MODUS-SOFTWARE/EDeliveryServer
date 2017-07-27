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
@Entity
@Table(name = "attached_documents_received", catalog = "edeliveryserver", schema = "edeliveryserver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttachedDocumentsReceived.findAll", query = "SELECT a FROM AttachedDocumentsReceived a")
    , @NamedQuery(name = "AttachedDocumentsReceived.findById", query = "SELECT a FROM AttachedDocumentsReceived a WHERE a.id = :id")
    , @NamedQuery(name = "AttachedDocumentsReceived.findByActualDocumentFilepath", query = "SELECT a FROM AttachedDocumentsReceived a WHERE a.actualDocumentFilepath = :actualDocumentFilepath")})
public class AttachedDocumentsReceived implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Size(max = 280)
    @Column(name = "actual_document_filepath")
    private String actualDocumentFilepath;
    @Column(name = "docId")
    private Integer docId;
    @JoinColumn(name = "reference_document", referencedColumnName = "id")
    @ManyToOne
    private DocumentsReceived referenceDocument;

    

    public AttachedDocumentsReceived() {
    }

    public AttachedDocumentsReceived(Integer id) {
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
    
    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public DocumentsReceived getReferenceDocument() {
        return referenceDocument;
    }

    public void setReferenceDocument(DocumentsReceived referenceDocument) {
        this.referenceDocument = referenceDocument;
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
        if (!(object instanceof AttachedDocumentsReceived)) {
            return false;
        }
        AttachedDocumentsReceived other = (AttachedDocumentsReceived) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.AttachedDocumentsReceived[ id=" + id + " ]";
    }
    
}
