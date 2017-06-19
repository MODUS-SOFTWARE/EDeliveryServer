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


public class DocumentsSend implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private int messageId;
    
    private int docId;
    
    private String messageUniqueId;
    private String documentOrganizationEtiquette;
    private String documentType;
    private String documentTitle;
    private String documentPurpose;
    private String documentDescription;
    private Date documentEtiquetteCreationDate;
    private Date documentSubmitedToApDate;
    private Date documentReceivedFromApDate;
    private String documentComments;
    private String documentLanguage;
    private String referencedDocumentUniqueId;
    private Date documentValidPeriod;
    private Date documentAcceptancePeriod;
    private String actualDocumentFilepath;
    private String documentIssuingAuthority;
    private String documentIssuingOrganization;
    private String documentReceiverAuthority;
    private String documentReceiverOrganization;
    private String documentAuthorityApplicant;
    private String documentOrganizationApplicant;
    private Collection<AttachedDocumentsSend> attachedDocumentsSendCollection;
    private DocumentStatus documentStatus;
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

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}
    
}