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
@Table(name = "loancategory")
@NamedQueries({
        @NamedQuery(name = "Loancategory.findAll", query = "SELECT l FROM Loancategory l")})
public class Loancategory implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Loan Category Name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loancategoryId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loantype> loantypeList;

    public Loancategory() {
    }

    public Loancategory(Integer id) {
        this.id = id;
    }

    public Loancategory(Integer id,String code,String name) {
        this.id = id;
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Loantype> getLoantypeList() {
        return loantypeList;
    }

    public void setLoantypeList(List<Loantype> loantypeList) {
        this.loantypeList = loantypeList;
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
        if (!(object instanceof Loancategory)) {
            return false;
        }
        Loancategory other = (Loancategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Loancategory[ id=" + id + " ]";
    }



}
