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
public class AdministrativeUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    
    private Collection<AdministrativeUsersPasswords> administrativeUsersPasswordsCollection;

    public AdministrativeUsers() {
    }

    public AdministrativeUsers(Integer id) {
        this.id = id;
    }

    public AdministrativeUsers(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Collection<AdministrativeUsersPasswords> getAdministrativeUsersPasswordsCollection() {
        return administrativeUsersPasswordsCollection;
    }

    public void setAdministrativeUsersPasswordsCollection(Collection<AdministrativeUsersPasswords> administrativeUsersPasswordsCollection) {
        this.administrativeUsersPasswordsCollection = administrativeUsersPasswordsCollection;
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
        if (!(object instanceof AdministrativeUsers)) {
            return false;
        }
        AdministrativeUsers other = (AdministrativeUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.edelivery.edeliveryserver.AdministrativeUsers[ id=" + id + " ]";
    }
    
}
