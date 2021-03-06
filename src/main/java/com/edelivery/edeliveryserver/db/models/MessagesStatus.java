/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edelivery.edeliveryserver.db.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pantelispanka
 */
@Entity
@Table(name = "ED_MES_STATUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessagesStatus.findAll", query = "SELECT d FROM MessagesStatus d")
    , @NamedQuery(name = "MessagesStatus.findById", query = "SELECT d FROM MessagesStatus d WHERE d.id = :id")
    , @NamedQuery(name = "MessagesStatus.findByStatus", query = "SELECT d FROM MessagesStatus d WHERE d.status = :status")})
public class MessagesStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;
    @Size(max = 20)
    private String status;
//    @OneToMany(mappedBy = "documentStatus")
//    private Collection<DocumentsReceived> documentsReceivedCollection;
//    @OneToMany(mappedBy = "documentStatus")
//    private Collection<DocumentsSend> documentsSendCollection;

    public MessagesStatus() {
    }

    public MessagesStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    @XmlTransient
//    public Collection<DocumentsReceived> getDocumentsReceivedCollection() {
//        return documentsReceivedCollection;
//    }
//
//    public void setDocumentsReceivedCollection(Collection<DocumentsReceived> documentsReceivedCollection) {
//        this.documentsReceivedCollection = documentsReceivedCollection;
//    }
//
//    @XmlTransient
//    public Collection<DocumentsSend> getDocumentsSendCollection() {
//        return documentsSendCollection;
//    }
//
//    public void setDocumentsSendCollection(Collection<DocumentsSend> documentsSendCollection) {
//        this.documentsSendCollection = documentsSendCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessagesStatus)) {
            return false;
        }
        MessagesStatus other = (MessagesStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.DocumentStatus[ id=" + id + " ]";
    }
    
}
