/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "cstatus")
@NamedQueries({
    @NamedQuery(name = "Cstatus.findAll", query = "SELECT c FROM Cstatus c"),
    @NamedQuery(name = "Cstatus.findById", query = "SELECT c FROM Cstatus c WHERE c.id = :id"),
    @NamedQuery(name = "Cstatus.findByName", query = "SELECT c FROM Cstatus c WHERE c.name = :name")})
public class Cstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Borrower Status")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cstatusId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Client> clientList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cstatusId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gurantordetail> gurantordetailList;

    public Cstatus() {
    }

    public Cstatus(Integer id) {
        this.id = id;
    }

    public Cstatus(Integer id,String name) {
        this.id = id;
        this.name=name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Gurantordetail> getGurantordetailList() { return gurantordetailList; }

    public void setGurantordetailList(List<Gurantordetail> gurantordetailList) { this.gurantordetailList = gurantordetailList; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cstatus)) {
            return false;
        }
        Cstatus other = (Cstatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Cstatus[ id=" + id + " ]";
    }


    
}
