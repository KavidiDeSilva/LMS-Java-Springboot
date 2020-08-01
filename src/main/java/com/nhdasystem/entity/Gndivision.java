/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "gndivision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gndivision.findAll", query = "SELECT g FROM Gndivision g")})
public class Gndivision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    @Pattern(regexp = "^\\d{3,}$", message = "Invalid G.N Division Code")
    private String code;
    @Column(name = "number")
    private String number;
    @Column(name = "name")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid G.N Division Name")
    private String name;
    @JoinColumn(name = "dsdivision_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dsdivision dsdivisionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gndivisionId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Client> clientList;

    public Gndivision() {
    }

    public Gndivision(Integer id) {
        this.id = id;
    }


    public Gndivision(Integer id,String name) {
        this.id = id;
        this.name=name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dsdivision getDsdivisionId() {
        return dsdivisionId;
    }

    public void setDsdivisionId(Dsdivision dsdivisionId) {
        this.dsdivisionId = dsdivisionId;
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
        if (!(object instanceof Gndivision)) {
            return false;
        }
        Gndivision other = (Gndivision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Gndivision[ id=" + id + " ]";
    }
    
}
