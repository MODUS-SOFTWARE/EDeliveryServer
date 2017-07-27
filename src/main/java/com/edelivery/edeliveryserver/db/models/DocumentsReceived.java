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
@Table(name = "ED_ACTUAL_MES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentsReceived.findAll", query = "SELECT d FROM DocumentsReceived d")
    , @NamedQuery(name = "DocumentsReceived.findById", query = "SELECT d FROM DocumentsReceived d WHERE d.id = :id")
    , @NamedQuery(name = "DocumentsReceived.findByMessageId", query = "SELECT d FROM DocumentsReceived d WHERE d.messageId = :messageId")
    , @NamedQuery(name = "DocumentsReceived.findByMessageUniqueId", query = "SELECT d FROM DocumentsReceived d WHERE d.messageUniqueId = :messageUniqueId")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentOrganizationEtiquette", query = "SELECT d FROM DocumentsReceived d WHERE d.documentOrganizationEtiquette = :documentOrganizationEtiquette")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentType", query = "SELECT d FROM DocumentsReceived d WHERE d.documentType = :documentType")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentTitle", query = "SELECT d FROM DocumentsReceived d WHERE d.documentTitle = :documentTitle")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentPurpose", query = "SELECT d FROM DocumentsReceived d WHERE d.documentPurpose = :documentPurpose")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentDescription", query = "SELECT d FROM DocumentsReceived d WHERE d.documentDescription = :documentDescription")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentEtiquetteCreationDate", query = "SELECT d FROM DocumentsReceived d WHERE d.documentEtiquetteCreationDate = :documentEtiquetteCreationDate")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentSubmitedToApDate", query = "SELECT d FROM DocumentsReceived d WHERE d.documentSubmitedToApDate = :documentSubmitedToApDate")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentReceivedFromApDate", query = "SELECT d FROM DocumentsReceived d WHERE d.documentReceivedFromApDate = :documentReceivedFromApDate")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentComments", query = "SELECT d FROM DocumentsReceived d WHERE d.documentComments = :documentComments")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentLanguage", query = "SELECT d FROM DocumentsReceived d WHERE d.documentLanguage = :documentLanguage")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentValidPeriod", query = "SELECT d FROM DocumentsReceived d WHERE d.documentValidPeriod = :documentValidPeriod")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentAcceptancePeriod", query = "SELECT d FROM DocumentsReceived d WHERE d.documentAcceptancePeriod = :documentAcceptancePeriod")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentIssuingAuthority", query = "SELECT d FROM DocumentsReceived d WHERE d.documentIssuingAuthority = :documentIssuingAuthority")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentIssuingOrganization", query = "SELECT d FROM DocumentsReceived d WHERE d.documentIssuingOrganization = :documentIssuingOrganization")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentReceiverAuthority", query = "SELECT d FROM DocumentsReceived d WHERE d.documentReceiverAuthority = :documentReceiverAuthority")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentReceiverOrganization", query = "SELECT d FROM DocumentsReceived d WHERE d.documentReceiverOrganization = :documentReceiverOrganization")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentAuthorityApplicant", query = "SELECT d FROM DocumentsReceived d WHERE d.documentAuthorityApplicant = :documentAuthorityApplicant")
    , @NamedQuery(name = "DocumentsReceived.findByDocumentOrganizationApplicant", query = "SELECT d FROM DocumentsReceived d WHERE d.documentOrganizationApplicant = :documentOrganizationApplicant")})
public class DocumentsReceived implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes_id")
    private String messageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "mes_unique_id")
    private String messageUniqueId;
//    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "doc_organization_etiquette")
    private String documentOrganizationEtiquette;
//    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "doc_type")
    private String documentType;
//    @Basic(optional = false)
    @Size(min = 1, max = 400)
    @Column(name = "doc_title")
    private String documentTitle;
    @Size(max = 100)
    @Column(name = "doc_purpose")
    private String documentPurpose;
    @Size(max = 400)
    @Column(name = "doc_description")
    private String documentDescription;
    @Column(name = "doc_etiquette_creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentEtiquetteCreationDate;
    @Column(name = "doc_submited_to_ap_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentSubmitedToApDate;
    @Column(name = "doc_received_from_ap_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentReceivedFromApDate;
    @Size(max = 400)
    @Column(name = "doc_comments")
    private String documentComments;
    @Size(max = 10)
    @Column(name = "doc_language")
    private String documentLanguage;
    @Column(name = "doc_valid_period")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentValidPeriod;
    @Column(name = "doc_acceptance_period")
    @Temporal(TemporalType.TIMESTAMP)
    private Date documentAcceptancePeriod;
    @Size(max = 100)
    @Column(name = "doc_issuing_authority")
    private String documentIssuingAuthority;
    @Size(max = 100)
    @Column(name = "doc_issuing_organization")
    private String documentIssuingOrganization;
    @Size(max = 100)
    @Column(name = "doc_receiver_authority")
    private String documentReceiverAuthority;
    @Size(max = 100)
    @Column(name = "doc_receiver_organization")
    private String documentReceiverOrganization;
    @Size(max = 100)
    @Column(name = "doc_authority_applicant")
    private String documentAuthorityApplicant;
    @Size(max = 100)
    @Column(name = "doc_organization_applicant")
    private String documentOrganizationApplicant;
    @Column(name = "docId")
    @NotNull
    private Integer docId;
    @Column(name = "mes_ap_unique_id")
    @Size(max = 255)
    private String uniqueIdFromAp;
//    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
//    @OneToOne
//    private MessageSendToAp messageSendToAp;
//    @OneToMany(mappedBy = "referenceDocument")
//    
//    private Collection<AttachedDocumentsReceived> attachedDocumentsReceivedCollection;

    public DocumentsReceived() {
    }

    public DocumentsReceived(Integer id) {
        this.id = id;
    }

    public DocumentsReceived(Integer id, String messageId, String messageUniqueId, String documentOrganizationEtiquette, String documentType, String documentTitle) {
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
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

    public String getUniqueIdFromAp() {
        return uniqueIdFromAp;
    }

    public void setUniqueIdFromAp(String uniqueIdFromAp) {
        this.uniqueIdFromAp = uniqueIdFromAp;
    }

//    public String getReferencedDocumentUniqueId() {
//        return referencedDocumentUniqueId;
//    }
//
//    public void setReferencedDocumentUniqueId(String referencedDocumentUniqueId) {
//        this.referencedDocumentUniqueId = referencedDocumentUniqueId;
//    }
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

//    public String getActualDocumentFilepath() {
//        return actualDocumentFilepath;
//    }
//
//    public void setActualDocumentFilepath(String actualDocumentFilepath) {
//        this.actualDocumentFilepath = actualDocumentFilepath;
//    }
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

//    public DocumentStatus getDocumentStatus() {
//        return documentStatus;
//    }
//
//    public void setDocumentStatus(DocumentStatus documentStatus) {
//        this.documentStatus = documentStatus;
//    }
//
//    public MessageSendToAp getMessageSendToAp() {
//        return messageSendToAp;
//    }
//
//    public void setMessageSendToAp(MessageSendToAp messageSendToAp) {
//        this.messageSendToAp = messageSendToAp;
//    }
    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

//    @XmlTransient
//    public Collection<AttachedDocumentsReceived> getAttachedDocumentsReceivedCollection() {
//        return attachedDocumentsReceivedCollection;
//    }
//
//    public void setAttachedDocumentsReceivedCollection(Collection<AttachedDocumentsReceived> attachedDocumentsReceivedCollection) {
//        this.attachedDocumentsReceivedCollection = attachedDocumentsReceivedCollection;
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
        if (!(object instanceof DocumentsReceived)) {
            return false;
        }
        DocumentsReceived other = (DocumentsReceived) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.DocumentsReceived[ id=" + id + " ]";
    }

}
