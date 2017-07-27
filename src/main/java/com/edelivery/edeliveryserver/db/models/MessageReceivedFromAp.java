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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ED_MES_REC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageReceivedFromAp.findAll", query = "SELECT m FROM MessageReceivedFromAp m")
    , @NamedQuery(name = "MessageReceivedFromAp.findById", query = "SELECT m FROM MessageReceivedFromAp m WHERE m.id = :id")
    , @NamedQuery(name = "MessageReceivedFromAp.findByMessageUniqueId", query = "SELECT m FROM MessageReceivedFromAp m WHERE m.messageUniqueId = :messageUniqueId")})
public class MessageReceivedFromAp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ap_unique_id")
    private String messageUniqueId;

    public MessageReceivedFromAp() {
    }

    public MessageReceivedFromAp(Integer id) {
        this.id = id;
    }

    public MessageReceivedFromAp(Integer id, String messageUniqueId) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageReceivedFromAp)) {
            return false;
        }
        MessageReceivedFromAp other = (MessageReceivedFromAp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.MessageReceivedFromAp[ id=" + id + " ]";
    }
    
}
