/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "gurantordetail")
@NamedQueries({
    @NamedQuery(name = "Gurantordetail.findAll", query = "SELECT g FROM Gurantordetail g")})
public class Gurantordetail implements Serializable {




    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
  //  @Pattern(regexp = "^\\d{4}$", message = "Invalid Number")
    private String code;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    private String name;
    @Column(name = "callingname")
     @Pattern(regexp = "^([A-Z][a-z]+)$", message = "Invalid Calligname")
    private String callingname;
    @Column(name = "nic")
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    private String nic;
    @Lob
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Column(name = "mobileno")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Mobilephone Number")
    private String mobileno;
    @Column(name = "landno")
    @Pattern(regexp = "^(((0\\d{9})){0,1})$", message = "Invalid Landphone Number")
    private String landno;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "dobirth")
    //@Temporal(TemporalType.DATE)
    private LocalDate dobirth;
    @Column(name = "regdate")
    // @Temporal(TemporalType.DATE)
    private LocalDate regdate;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gurantordetailId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loan> loanList;

    @JoinColumn(name = "cstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cstatus cstatusId;
    @JoinColumn(name = "civilstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Civilstatus civilstatusId;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Gender genderId;

    public Gurantordetail() {
    }

    public Gurantordetail(Integer id) {
        this.id = id;
    }

    public Gurantordetail(Integer id,String name) {
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

    public String getCallingname() {
        return callingname;
    }

    public void setCallingname(String callingname) {
        this.callingname = callingname;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getPhoto() { return photo; }

    public void setPhoto(byte[] photo) { this.photo = photo; }

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

    public LocalDate getDobirth() { return dobirth; }

    public void setDobirth(LocalDate dobirth) { this.dobirth = dobirth; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Civilstatus getCivilstatusId() {
        return civilstatusId;
    }

    public void setCivilstatusId(Civilstatus civilstatusId) {
        this.civilstatusId = civilstatusId;
    }



    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Gender getGenderId() {
        return genderId;
    }

    public void setGenderId(Gender genderId) {
        this.genderId = genderId;
    }

    public Cstatus getCstatusId() {
        return cstatusId;
    }

    public void setCstatusId(Cstatus cstatusId) {
        this.cstatusId = cstatusId;
    }

    public LocalDate getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDate regdate) {
        this.regdate = regdate;
    }

    public List<Loan> getLoanList() { return loanList; }

    public void setLoanList(List<Loan> loanList) { this.loanList = loanList; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gurantordetail)) {
            return false;
        }
        Gurantordetail other = (Gurantordetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Gurantordetail[ id=" + id + " ]";
    }




}
