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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "client")
@NamedQueries({@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "code")
  //  @Pattern(regexp = "^\\d{4}$", message = "Invalid Code")
    private String code;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Name")
    private String name;
    @Column(name = "fullname")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    private String fullname;
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
    @Column(name = "dobirth")
    //@Temporal(TemporalType.DATE)
    private LocalDate dobirth;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "regdate")
    //@Temporal(TemporalType.DATE)
    private LocalDate regdate;
    @Lob
    @Column(name = "file")
    private byte[] file;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loan> loanList;
    @JoinColumn(name = "cstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cstatus cstatusId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "gndivision_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Gndivision gndivisionId;
    @JoinColumn(name = "workingstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Workingstatus workingstatusId;
    @JoinColumn(name = "civilstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Civilstatus civilstatusId;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Gender genderId;

    public Client() {
    }

    public Client(Integer id) {
        this.id = id;
    }

    public Client(Integer id,String code,String name,String fullname,Gndivision gndivisionId,Cstatus cstatusId) {
        this.id = id;
        this.code = code;
        this.name=name;
        this.fullname=fullname;
        this.gndivisionId=gndivisionId;
        this.cstatusId=cstatusId;
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

    public String getFullname() { return fullname; }

    public void setFullname(String fullname) { this.fullname = fullname; }

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

    public LocalDate getDobirth() {
        return dobirth;
    }

    public void setDobirth(LocalDate dobirth) {
        this.dobirth = dobirth;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDate getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDate regdate) {
        this.regdate = regdate;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Cstatus getCstatusId() {
        return cstatusId;
    }

    public void setCstatusId(Cstatus cstatusId) {
        this.cstatusId = cstatusId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Gndivision getGndivisionId() {
        return gndivisionId;
    }

    public void setGndivisionId(Gndivision gndivisionId) {
        this.gndivisionId = gndivisionId;
    }

    public Workingstatus getWorkingstatusId() { return workingstatusId; }

    public void setWorkingstatusId(Workingstatus workingstatusId) { this.workingstatusId = workingstatusId; }

    public Civilstatus getCivilstatusId() { return civilstatusId; }

    public void setCivilstatusId(Civilstatus civilstatusId) { this.civilstatusId = civilstatusId; }

    public Gender getGenderId() { return genderId; }

    public void setGenderId(Gender genderId) { this.genderId = genderId; }


    @XmlTransient


    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Client[ id=" + id + " ]";
    }

}
