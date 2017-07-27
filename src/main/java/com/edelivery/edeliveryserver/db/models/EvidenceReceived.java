/*
 * Copyright (C) 2017 modussa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.edelivery.edeliveryserver.db.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author modussa
 */
@Entity
@Table(name = "ED_EVID_REC")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvidenceReceived.findAll", query = "SELECT e FROM EvidenceReceived e")
    , @NamedQuery(name = "EvidenceReceived.findById", query = "SELECT e FROM EvidenceReceived e WHERE e.id = :id")
    , @NamedQuery(name = "EvidenceReceived.findByEvidenceCode", query = "SELECT e FROM EvidenceReceived e WHERE e.evidenceIdentification = :evidenceIdentification")
    , @NamedQuery(name = "EvidenceReceived.findByDocId", query = "SELECT e FROM EvidenceReceived e WHERE e.docId = :docId")
})
public class EvidenceReceived implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 220)
    @Column(name = "e_code")
    private String evidenceCode;

    @Size(max = 220)
    @Column(name = "e_id")
    private String evidenceIdentification;

    @Size(max = 220)
    @Column(name = "to_recip")
    private String recipient;

    @Size(max = 220)
    @Column(name = "e_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date evidenceTime;

    @Column(name = "doc_id")
    private Integer docId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refer_to")
    private DocumentsReceived docRec;

    public DocumentsReceived getDocRec() {
        return docRec;
    }

    public void setDocRec(DocumentsReceived docRec) {
        this.docRec = docRec;
    }

    public String getEvidenceCode() {
        return evidenceCode;
    }

    public void setEvidenceCode(String evidenceCode) {
        this.evidenceCode = evidenceCode;
    }

    public String getEvidenceIdentification() {
        return evidenceIdentification;
    }

    public void setEvidenceIdentification(String evidenceIdentification) {
        this.evidenceIdentification = evidenceIdentification;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getEvidenceTime() {
        return evidenceTime;
    }

    public void setEvidenceTime(Date evidenceTime) {
        this.evidenceTime = evidenceTime;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof EvidenceReceived)) {
            return false;
        }
        EvidenceReceived other = (EvidenceReceived) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.db.models.EvidenceReceived[ id=" + id + " ]";
    }

}
