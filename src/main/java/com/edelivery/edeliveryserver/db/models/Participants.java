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
@Table(catalog = "edeliveryserver", schema = "edeliveryserver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participants.findAll", query = "SELECT p FROM Participants p")
    , @NamedQuery(name = "Participants.findById", query = "SELECT p FROM Participants p WHERE p.id = :id")
    , @NamedQuery(name = "Participants.findByParticipantIdentifierValue", query = "SELECT p FROM Participants p WHERE p.participantIdentifierValue = :participantIdentifierValue")
    , @NamedQuery(name = "Participants.findByParticipantIdentifierScheme", query = "SELECT p FROM Participants p WHERE p.participantIdentifierScheme = :participantIdentifierScheme")})
public class Participants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "participant_identifier_value")
    private String participantIdentifierValue;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "participant_identifier_scheme")
    private String participantIdentifierScheme;

    public Participants() {
    }

    public Participants(Integer id) {
        this.id = id;
    }

    public Participants(Integer id, String participantIdentifierValue, String participantIdentifierScheme) {
        this.id = id;
        this.participantIdentifierValue = participantIdentifierValue;
        this.participantIdentifierScheme = participantIdentifierScheme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParticipantIdentifierValue() {
        return participantIdentifierValue;
    }

    public void setParticipantIdentifierValue(String participantIdentifierValue) {
        this.participantIdentifierValue = participantIdentifierValue;
    }

    public String getParticipantIdentifierScheme() {
        return participantIdentifierScheme;
    }

    public void setParticipantIdentifierScheme(String participantIdentifierScheme) {
        this.participantIdentifierScheme = participantIdentifierScheme;
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
        if (!(object instanceof Participants)) {
            return false;
        }
        Participants other = (Participants) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.Participants[ id=" + id + " ]";
    }
    
}
