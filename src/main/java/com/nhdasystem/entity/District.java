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
@Table(name = "district")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d")})
public class District implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    @Pattern(regexp = "^\\d{2}$", message = "Invalid District Code")
    private String code;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid District Name")
    private String name;
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Province provinceId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Branch> branchList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Dsdivision> dsdivisionList;

    public District() {
    }

    public District(Integer id) {
        this.id = id;
    }


    public District(Integer id,String name) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Province getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Province provinceId) {
        this.provinceId = provinceId;
    }

    @XmlTransient
    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }

    @XmlTransient
    public List<Dsdivision> getDsdivisionList() {
        return dsdivisionList;
    }

    public void setDsdivisionList(List<Dsdivision> dsdivisionList) {
        this.dsdivisionList = dsdivisionList;
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
        if (!(object instanceof District)) {
            return false;
        }
        District other = (District) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.District[ id=" + id + " ]";
    }


    
}
