/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edelivery.edeliveryserver.db.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pantelispanka
 */
@Entity
@Table(name = "ED_MES_SENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessagesSent.findAll", query = "SELECT d FROM MessagesSent d")
    , @NamedQuery(name = "MessagesSent.findById", query = "SELECT d FROM MessagesSent d WHERE d.id = :id")
    , @NamedQuery(name = "MessagesSent.findByMessageUnId", query = "SELECT d FROM MessagesSent d WHERE d.messageUnId = :messageUnId")
    , @NamedQuery(name = "MessagesSent.fingByDocumentProtocol", query = "SELECT d FROM MessagesSent d WHERE d.documentProtocol = :documentProtocol")
    , @NamedQuery(name = "MessagesSent.fingByApplicantAuthority", query = "SELECT d FROM MessagesSent d WHERE d.applicantAuthority = :applicantAuthority")
    , @NamedQuery(name = "MessagesSent.fingByDocumentReceiverAuthority", query = "SELECT d FROM MessagesSent d WHERE d.documentReceiverAuthority = :documentReceiverAuthority")
    , @NamedQuery(name = "MessagesSent.fingByDocumentReceiverOrganization", query = "SELECT d FROM MessagesSent d WHERE d.documentReceiverOrganization = :documentReceiverOrganization")
    , @NamedQuery(name = "MessagesSent.findByDocStatusId", query = "SELECT d FROM MessagesSent d WHERE d.documentStatus.id = :id")
    , @NamedQuery(name = "MessagesSent.findByDocStatus", query = "SELECT d FROM MessagesSent d WHERE d.documentStatus.status = :status")
})
public class MessagesSent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "mes_un_id")
    @Size(max = 255)
    private String messageUnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "doc_prot")
    private String documentProtocol;
    @Column(name = "doc_type")
    private String documentType;
    @Column(name = "doc_id")
    private int docId;
    @Column(name = "doc_accept_per")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docAcceptance;
    @Column(name = "doc_auth_applic")
    private String applicantAuthority;
    @Column(name = "doc_prot_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date protocolDate;
    @Column(name = "doc_comm")
    @Size(max = 255)
    private String docComments;
    @Column(name = "doc_iss_auth")
    @Size(max = 255)
    private String docIssuingAuthority;
    @Column(name = "doc_iss_organ")
    private String docIssuingOrganization;
    @Column(name = "doc_lang")
    private String docLang;
    @Column(name = "doc_purpose")
    private String docPurpose;
    @Column(name = "doc_receiv_auth")
    private String documentReceiverAuthority;
    @Column(name = "doc_receiv_org")
    private String documentReceiverOrganization;
    @Column(name = "doc_subm_to_ap")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentSubmitedToAP;
    @Column(name = "doc_title")
    private String docTitle;
    @Column(name = "doc_val_per")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docValidPeriod;

    @Column(name = "doc_subj")
    @Size(max = 255)
    private String docSubject;

    @OneToOne
    @JoinColumn(name = "doc_status")
    private MessagesStatus documentStatus;

    public MessagesSent() {
    }

    public MessagesSent(Integer id) {
        this.id = id;
    }

    public MessagesSent(Integer id, String messageId, String messageUniqueId, String documentOrganizationEtiquette, String documentType, String documentTitle) {
        this.id = id;
        this.messageUnId = messageId;
        this.documentProtocol = documentOrganizationEtiquette;
        this.documentType = documentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageUnId() {
        return messageUnId;
    }

    public void setMessageUnId(String messageId) {
        this.messageUnId = messageId;
    }

    public String getDocSubject() {
        return docSubject;
    }

    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    public Date getDocAcceptance() {
        return docAcceptance;
    }

    public Date getProtocolDate() {
        return protocolDate;
    }

    public void setProtocolDate(Date protocolDate) {
        this.protocolDate = protocolDate;
    }

    public Date getDocumentSubmitedToAP() {
        return documentSubmitedToAP;
    }

    public void setDocumentSubmitedToAP(Date documentSubmitedToAP) {
        this.documentSubmitedToAP = documentSubmitedToAP;
    }

    public void setDocAcceptance(Date docAcceptance) {
        this.docAcceptance = docAcceptance;
    }

    public String getApplicantAuthority() {
        return applicantAuthority;
    }

    public void setApplicantAuthority(String applicantAuthority) {
        this.applicantAuthority = applicantAuthority;
    }

    public String getDocComments() {
        return docComments;
    }

    public void setDocComments(String docComments) {
        this.docComments = docComments;
    }

    public String getDocIssuingAuthority() {
        return docIssuingAuthority;
    }

    public void setDocIssuingAuthority(String docIssuingAuthority) {
        this.docIssuingAuthority = docIssuingAuthority;
    }

    public String getDocIssuingOrganization() {
        return docIssuingOrganization;
    }

    public void setDocIssuingOrganization(String docIssuingOrganization) {
        this.docIssuingOrganization = docIssuingOrganization;
    }

    public String getDocLang() {
        return docLang;
    }

    public void setDoc_lang(String doc_lang) {
        this.docLang = doc_lang;
    }

    public String getDocPurpose() {
        return docPurpose;
    }

    public void setDocPurpose(String docPurpose) {
        this.docPurpose = docPurpose;
    }

    public String getDoc_title() {
        return docTitle;
    }

    public void setDoc_title(String doc_title) {
        this.docTitle = doc_title;
    }

    public Date getDocValidPeriod() {
        return docValidPeriod;
    }

    public void setDocValidPeriod(Date docValidPeriod) {
        this.docValidPeriod = docValidPeriod;
    }

    public String getDocumentProtocol() {
        return documentProtocol;
    }

    public void setDocumentProtocol(String documentOrganizationEtiquette) {
        this.documentProtocol = documentOrganizationEtiquette;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentReceiverAuthority() {
        return documentReceiverAuthority;
    }

    public void setDocumentReceiverAuthority(String documentReceiverAuthority) {
        this.documentReceiverAuthority = documentReceiverAuthority;
    }

    public String getDocumentReceiverOrganization() {
        return documentReceiverOrganization;
    }

    public void setDocumentReceiverOrganization(String documentReceiverOrganization) {
        this.documentReceiverOrganization = documentReceiverOrganization;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public MessagesStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(MessagesStatus documentStatus) {
        this.documentStatus = documentStatus;
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
        if (!(object instanceof MessagesSent)) {
            return false;
        }
        MessagesSent other = (MessagesSent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.DocumentsSend[ id=" + id + " ]";
    }

}
