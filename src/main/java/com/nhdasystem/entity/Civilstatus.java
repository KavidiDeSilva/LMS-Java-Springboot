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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "civilstatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Civilstatus.findAll", query = "SELECT c FROM Civilstatus c")})
public class Civilstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Civil Status")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "civilstatusId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employee> employeeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "civilstatusId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gurantordetail> gurantordetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "civilstatusId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Client> clientList;

    public Civilstatus() {
    }

    public Civilstatus(Integer id) {
        this.id = id;
    }


    public Civilstatus(Integer id,String name) {
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

    @XmlTransient
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @XmlTransient
    public List<Gurantordetail> getGurantordetailList() {
        return gurantordetailList;
    }

    public void setGurantordetailList(List<Gurantordetail> gurantordetailList) {
        this.gurantordetailList = gurantordetailList;
    }

    @XmlTransient
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
        if (!(object instanceof Civilstatus)) {
            return false;
        }
        Civilstatus other = (Civilstatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Civilstatus[ id=" + id + " ]";
    }
    
}
