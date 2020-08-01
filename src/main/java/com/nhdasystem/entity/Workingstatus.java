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
@Table(name = "workingstatus")
@NamedQueries({
    @NamedQuery(name = "Workingstatus.findAll", query = "SELECT w FROM Workingstatus w"),
    @NamedQuery(name = "Workingstatus.findById", query = "SELECT w FROM Workingstatus w WHERE w.id = :id"),
    @NamedQuery(name = "Workingstatus.findByName", query = "SELECT w FROM Workingstatus w WHERE w.name = :name")})
public class Workingstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Working Status")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workingstatusId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Client> clientList;

    public Workingstatus() {
    }

    public Workingstatus(Integer id) {
        this.id = id;
    }
    public Workingstatus(Integer id,String name) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workingstatus)) {
            return false;
        }
        Workingstatus other = (Workingstatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Workingstatus[ id=" + id + " ]";
    }
    
}
