/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edelivery.edeliveryserver.db.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pantelispanka
 */
@Entity
@Table(name = "message_send_to_ap", catalog = "edeliveryserver", schema = "edeliveryserver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageSendToAp.findAll", query = "SELECT m FROM MessageSendToAp m")
    , @NamedQuery(name = "MessageSendToAp.findById", query = "SELECT m FROM MessageSendToAp m WHERE m.id = :id")
    , @NamedQuery(name = "MessageSendToAp.findByMessageUniqueId", query = "SELECT m FROM MessageSendToAp m WHERE m.messageUniqueId = :messageUniqueId")})
public class MessageSendToAp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "message_unique_id")
    private String messageUniqueId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "messageSendToAp")
    private DocumentsReceived documentsReceived;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "messageSendToAp")
    private DocumentsSend documentsSend;

    public MessageSendToAp() {
    }

    public MessageSendToAp(Integer id) {
        this.id = id;
    }

    public MessageSendToAp(Integer id, String messageUniqueId) {
        this.id = id;
        this.messageUniqueId = messageUniqueId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageUniqueId() {
        return messageUniqueId;
    }

    public void setMessageUniqueId(String messageUniqueId) {
        this.messageUniqueId = messageUniqueId;
    }

    public DocumentsReceived getDocumentsReceived() {
        return documentsReceived;
    }

    public void setDocumentsReceived(DocumentsReceived documentsReceived) {
        this.documentsReceived = documentsReceived;
    }

    public DocumentsSend getDocumentsSend() {
        return documentsSend;
    }

    public void setDocumentsSend(DocumentsSend documentsSend) {
        this.documentsSend = documentsSend;
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
        if (!(object instanceof MessageSendToAp)) {
            return false;
        }
        MessageSendToAp other = (MessageSendToAp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.MessageSendToAp[ id=" + id + " ]";
    }
    
}
