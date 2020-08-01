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
@Table(name = "dsdivision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dsdivision.findAll", query = "SELECT d FROM Dsdivision d")})
public class Dsdivision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
    @Pattern(regexp = "^\\d{2,}$", message = "Invalid D.S Division Code")
    private String code;
    @Column(name = "name")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid D.S Division Name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dsdivisionId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gndivision> gndivisionList;
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private District districtId;

    public Dsdivision() {
    }

    public Dsdivision(Integer id) {
        this.id = id;
    }


    public Dsdivision(Integer id,String name) {
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

    @XmlTransient
    public List<Gndivision> getGndivisionList() {
        return gndivisionList;
    }

    public void setGndivisionList(List<Gndivision> gndivisionList) {
        this.gndivisionList = gndivisionList;
    }

    public District getDistrictId() {
        return districtId;
    }

    public void setDistrictId(District districtId) {
        this.districtId = districtId;
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
        if (!(object instanceof Dsdivision)) {
            return false;
        }
        Dsdivision other = (Dsdivision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Dsdivision[ id=" + id + " ]";
    }
    
}
