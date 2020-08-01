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
@Table(name = "branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b")})
public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dcode")
    private String dcode;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Branch")
    private String name;
    @Column(name = "number")
    @Pattern(regexp = "^\\d{4}$", message = "Invalid Number")
    private String number;
    @Lob
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Column(name = "districtmanager")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Dstrict Manager")
    private String districtmanager;
    @Column(name = "mobileno")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Mobilephone Number")
    private String mobileno;
    @Column(name = "landno")
    @Pattern(regexp = "^(((0\\d{9})){0,1})$", message = "Invalid Landphone Number")
    private String landno;
    @Column(name = "fax")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Fax Number")
    private String fax;
    @Column(name = "email")
    private String email;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employee> employeeList;
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private District districtId;

    public Branch() {
    }

    public Branch(Integer id) {
        this.id = id;
    }

    public Branch(Integer id,String name,String dcode) {
        this.id = id;
        this.name=name;
        this.dcode=dcode;
    }
    public Branch(Integer id,String name,String number,String address,String districtmanager,String mobileno,String
            landno,String fax,String email,District districtId) {
        this.id = id;
        this.name=name;
        this.number=number;
        this.address=address;
        this.districtmanager=districtmanager;
        this.mobileno=mobileno;
        this.landno=landno;
        this.fax=fax;
        this.email=email;
        this.districtId=districtId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDcode() { return dcode; }

    public void setDcode(String dcode) { this.dcode = dcode; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrictmanager() {
        return districtmanager;
    }

    public void setDistrictmanager(String districtmanager) {
        this.districtmanager = districtmanager;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getLandno() {
        return landno;
    }

    public void setLandno(String landno) {
        this.landno = landno;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @XmlTransient
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
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
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Branch[ id=" + id + " ]";
    }
}
