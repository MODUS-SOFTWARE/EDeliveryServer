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
@Table(name = "documents_send", catalog = "edeliveryserver", schema = "edeliveryserver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentsSend.findAll", query = "SELECT d FROM DocumentsSend d")
    , @NamedQuery(name = "DocumentsSend.findById", query = "SELECT d FROM DocumentsSend d WHERE d.id = :id")
    , @NamedQuery(name = "DocumentsSend.findByMessageId", query = "SELECT d FROM DocumentsSend d WHERE d.messageId = :messageId")
    , @NamedQuery(name = "DocumentsSend.findByMessageUniqueId", query = "SELECT d FROM DocumentsSend d WHERE d.messageUniqueId = :messageUniqueId")
    , @NamedQuery(name = "DocumentsSend.findByDocumentOrganizationEtiquette", query = "SELECT d FROM DocumentsSend d WHERE d.documentOrganizationEtiquette = :documentOrganizationEtiquette")
    , @NamedQuery(name = "DocumentsSend.findByDocumentType", query = "SELECT d FROM DocumentsSend d WHERE d.documentType = :documentType")
    , @NamedQuery(name = "DocumentsSend.findByDocumentTitle", query = "SELECT d FROM DocumentsSend d WHERE d.documentTitle = :documentTitle")
    , @NamedQuery(name = "DocumentsSend.findByDocumentPurpose", query = "SELECT d FROM DocumentsSend d WHERE d.documentPurpose = :documentPurpose")
    , @NamedQuery(name = "DocumentsSend.findByDocumentDescription", query = "SELECT d FROM DocumentsSend d WHERE d.documentDescription = :documentDescription")
    , @NamedQuery(name = "DocumentsSend.findByDocumentEtiquetteCreationDate", query = "SELECT d FROM DocumentsSend d WHERE d.documentEtiquetteCreationDate = :documentEtiquetteCreationDate")
    , @NamedQuery(name = "DocumentsSend.findByDocumentSubmitedToApDate", query = "SELECT d FROM DocumentsSend d WHERE d.documentSubmitedToApDate = :documentSubmitedToApDate")
    , @NamedQuery(name = "DocumentsSend.findByDocumentReceivedFromApDate", query = "SELECT d FROM DocumentsSend d WHERE d.documentReceivedFromApDate = :documentReceivedFromApDate")
    , @NamedQuery(name = "DocumentsSend.findByDocumentComments", query = "SELECT d FROM DocumentsSend d WHERE d.documentComments = :documentComments")
    , @NamedQuery(name = "DocumentsSend.findByDocumentLanguage", query = "SELECT d FROM DocumentsSend d WHERE d.documentLanguage = :documentLanguage")
    , @NamedQuery(name = "DocumentsSend.findByReferencedDocumentUniqueId", query = "SELECT d FROM DocumentsSend d WHERE d.referencedDocumentUniqueId = :referencedDocumentUniqueId")
    , @NamedQuery(name = "DocumentsSend.findByDocumentValidPeriod", query = "SELECT d FROM DocumentsSend d WHERE d.documentValidPeriod = :documentValidPeriod")
    , @NamedQuery(name = "DocumentsSend.findByDocumentAcceptancePeriod", query = "SELECT d FROM DocumentsSend d WHERE d.documentAcceptancePeriod = :documentAcceptancePeriod")
    , @NamedQuery(name = "DocumentsSend.findByActualDocumentFilepath", query = "SELECT d FROM DocumentsSend d WHERE d.actualDocumentFilepath = :actualDocumentFilepath")
    , @NamedQuery(name = "DocumentsSend.findByDocumentIssuingAuthority", query = "SELECT d FROM DocumentsSend d WHERE d.documentIssuingAuthority = :documentIssuingAuthority")
    , @NamedQuery(name = "DocumentsSend.findByDocumentIssuingOrganization", query = "SELECT d FROM DocumentsSend d WHERE d.documentIssuingOrganization = :documentIssuingOrganization")
    , @NamedQuery(name = "DocumentsSend.findByDocumentReceiverAuthority", query = "SELECT d FROM DocumentsSend d WHERE d.documentReceiverAuthority = :documentReceiverAuthority")
    , @NamedQuery(name = "DocumentsSend.findByDocumentReceiverOrganization", query = "SELECT d FROM DocumentsSend d WHERE d.documentReceiverOrganization = :documentReceiverOrganization")
    , @NamedQuery(name = "DocumentsSend.findByDocumentAuthorityApplicant", query = "SELECT d FROM DocumentsSend d WHERE d.documentAuthorityApplicant = :documentAuthorityApplicant")
    , @NamedQuery(name = "DocumentsSend.findByDocumentOrganizationApplicant", query = "SELECT d FROM DocumentsSend d WHERE d.documentOrganizationApplicant = :documentOrganizationApplicant")})
public class DocumentsSend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "message_id")
    private int messageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "message_unique_id")
    private String messageUniqueId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "document_organization_etiquette")
    private String documentOrganizationEtiquette;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "document_type")
    private String documentType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "document_title")
    private String documentTitle;
    @Size(max = 100)
    @Column(name = "document_purpose")
    private String documentPurpose;
    @Size(max = 400)
    @Column(name = "document_description")
    private String documentDescription;
    @Column(name = "document_etiquette_creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentEtiquetteCreationDate;
    @Column(name = "document_submited_to_ap_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentSubmitedToApDate;
    @Column(name = "document_received_from_ap_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentReceivedFromApDate;
    @Size(max = 400)
    @Column(name = "document_comments")
    private String documentComments;
    @Size(max = 10)
    @Column(name = "document_language")
    private String documentLanguage;
    @Size(max = 80)
    @Column(name = "referenced_document_unique_id")
    private String referencedDocumentUniqueId;
    @Column(name = "document_valid_period")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentValidPeriod;
    @Column(name = "document_acceptance_period")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentAcceptancePeriod;
    @Size(max = 200)
    @Column(name = "actual_document_filepath")
    private String actualDocumentFilepath;
    @Size(max = 100)
    @Column(name = "document_issuing_authority")
    private String documentIssuingAuthority;
    @Size(max = 100)
    @Column(name = "document_issuing_organization")
    private String documentIssuingOrganization;
    @Size(max = 100)
    @Column(name = "document_receiver_authority")
    private String documentReceiverAuthority;
    @Size(max = 100)
    @Column(name = "document_receiver_organization")
    private String documentReceiverOrganization;
    @Size(max = 100)
    @Column(name = "document_authority_applicant")
    private String documentAuthorityApplicant;
    @Size(max = 100)
    @Column(name = "document_organization_applicant")
    private String documentOrganizationApplicant;
    @Column(name = "docId")
    private Integer docId;
    @OneToMany(mappedBy = "referenceDocument")
    private Collection<AttachedDocumentsSend> attachedDocumentsSendCollection;
    @JoinColumn(name = "document_status", referencedColumnName = "id")
    @ManyToOne
    private DocumentStatus documentStatus;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private MessageSendToAp messageSendToAp;

    public DocumentsSend() {
    }

    public DocumentsSend(Integer id) {
        this.id = id;
    }

    public DocumentsSend(Integer id, int messageId, String messageUniqueId, String documentOrganizationEtiquette, String documentType, String documentTitle) {
        this.id = id;
        this.messageId = messageId;
        this.messageUniqueId = messageUniqueId;
        this.documentOrganizationEtiquette = documentOrganizationEtiquette;
        this.documentType = documentType;
        this.documentTitle = documentTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageUniqueId() {
        return messageUniqueId;
    }

    public void setMessageUniqueId(String messageUniqueId) {
        this.messageUniqueId = messageUniqueId;
    }

    public String getDocumentOrganizationEtiquette() {
        return documentOrganizationEtiquette;
    }

    public void setDocumentOrganizationEtiquette(String documentOrganizationEtiquette) {
        this.documentOrganizationEtiquette = documentOrganizationEtiquette;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentPurpose() {
        return documentPurpose;
    }

    public void setDocumentPurpose(String documentPurpose) {
        this.documentPurpose = documentPurpose;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public Date getDocumentEtiquetteCreationDate() {
        return documentEtiquetteCreationDate;
    }

    public void setDocumentEtiquetteCreationDate(Date documentEtiquetteCreationDate) {
        this.documentEtiquetteCreationDate = documentEtiquetteCreationDate;
    }

    public Date getDocumentSubmitedToApDate() {
        return documentSubmitedToApDate;
    }

    public void setDocumentSubmitedToApDate(Date documentSubmitedToApDate) {
        this.documentSubmitedToApDate = documentSubmitedToApDate;
    }

    public Date getDocumentReceivedFromApDate() {
        return documentReceivedFromApDate;
    }

    public void setDocumentReceivedFromApDate(Date documentReceivedFromApDate) {
        this.documentReceivedFromApDate = documentReceivedFromApDate;
    }

    public String getDocumentComments() {
        return documentComments;
    }

    public void setDocumentComments(String documentComments) {
        this.documentComments = documentComments;
    }

    public String getDocumentLanguage() {
        return documentLanguage;
    }

    public void setDocumentLanguage(String documentLanguage) {
        this.documentLanguage = documentLanguage;
    }

    public String getReferencedDocumentUniqueId() {
        return referencedDocumentUniqueId;
    }

    public void setReferencedDocumentUniqueId(String referencedDocumentUniqueId) {
        this.referencedDocumentUniqueId = referencedDocumentUniqueId;
    }

    public Date getDocumentValidPeriod() {
        return documentValidPeriod;
    }

    public void setDocumentValidPeriod(Date documentValidPeriod) {
        this.documentValidPeriod = documentValidPeriod;
    }

    public Date getDocumentAcceptancePeriod() {
        return documentAcceptancePeriod;
    }

    public void setDocumentAcceptancePeriod(Date documentAcceptancePeriod) {
        this.documentAcceptancePeriod = documentAcceptancePeriod;
    }

    public String getActualDocumentFilepath() {
        return actualDocumentFilepath;
    }

    public void setActualDocumentFilepath(String actualDocumentFilepath) {
        this.actualDocumentFilepath = actualDocumentFilepath;
    }

    public String getDocumentIssuingAuthority() {
        return documentIssuingAuthority;
    }

    public void setDocumentIssuingAuthority(String documentIssuingAuthority) {
        this.documentIssuingAuthority = documentIssuingAuthority;
    }

    public String getDocumentIssuingOrganization() {
        return documentIssuingOrganization;
    }

    public void setDocumentIssuingOrganization(String documentIssuingOrganization) {
        this.documentIssuingOrganization = documentIssuingOrganization;
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

    public String getDocumentAuthorityApplicant() {
        return documentAuthorityApplicant;
    }

    public void setDocumentAuthorityApplicant(String documentAuthorityApplicant) {
        this.documentAuthorityApplicant = documentAuthorityApplicant;
    }

    public String getDocumentOrganizationApplicant() {
        return documentOrganizationApplicant;
    }

    public void setDocumentOrganizationApplicant(String documentOrganizationApplicant) {
        this.documentOrganizationApplicant = documentOrganizationApplicant;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }
    
    @XmlTransient
    public Collection<AttachedDocumentsSend> getAttachedDocumentsSendCollection() {
        return attachedDocumentsSendCollection;
    }

    public void setAttachedDocumentsSendCollection(Collection<AttachedDocumentsSend> attachedDocumentsSendCollection) {
        this.attachedDocumentsSendCollection = attachedDocumentsSendCollection;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public MessageSendToAp getMessageSendToAp() {
        return messageSendToAp;
    }

    public void setMessageSendToAp(MessageSendToAp messageSendToAp) {
        this.messageSendToAp = messageSendToAp;
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
        if (!(object instanceof DocumentsSend)) {
            return false;
        }
        DocumentsSend other = (DocumentsSend) object;
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