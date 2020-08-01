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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")})
public class Employee implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
   // @Pattern(regexp = "^\\d{4}$", message = "Invalid Number")
    private String number;
    @Column(name = "fullname")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    private String fullname;
    @Column(name = "callingname")
    @Pattern(regexp = "^([A-Z][a-z]+)$", message = "Invalid Calligname")
    private String callingname;
    @Column(name = "dobirth")
   // @Temporal(TemporalType.DATE)
    private LocalDate dobirth;
    @Column(name = "nic")
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    private String nic;
    @Lob
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Column(name = "mobile")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Mobilephone Number")
    private String mobile;
    @Column(name = "land")
    @Pattern(regexp = "^(((0\\d{9})){0,1})$", message = "Invalid Landphone Number")
    private String land;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @Column(name = "doassignment")
    //@Temporal(TemporalType.DATE)
    private LocalDate doassignment;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Branch branchId;
    @JoinColumn(name = "civilstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Civilstatus civilstatusId;
    @JoinColumn(name = "designation_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Designation designationId;
    @JoinColumn(name = "employeestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employeestatus employeestatusId;
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Gender genderId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Client> clientList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Operationlog> operationlogList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeCreatedId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> userList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Gurantordetail> gurantordetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loan> loanList;

    public Employee() {
    }

    public Employee(Integer id) {
        this.id = id;
    }


    public Employee(Integer id,String callingname,Designation designationId,Branch branchId) {
        this.id = id;
        this.callingname=callingname;
        this.designationId=designationId;
        this.branchId=branchId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCallingname() {
        return callingname;
    }

    public void setCallingname(String callingname) {
        this.callingname = callingname;
    }


    public LocalDate getDobirth() {
        return dobirth;
    }

    public void setDobirth(LocalDate dobirth) {
        this.dobirth = dobirth;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDoassignment() {
        return doassignment;
    }

    public void setDoassignment(LocalDate doassignment) {
        this.doassignment = doassignment;
    }

    public Branch getBranchId() {
        return branchId;
    }

    public void setBranchId(Branch branchId) {
        this.branchId = branchId;
    }

    public Civilstatus getCivilstatusId() {
        return civilstatusId;
    }

    public void setCivilstatusId(Civilstatus civilstatusId) {
        this.civilstatusId = civilstatusId;
    }

    public Designation getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Designation designationId) {
        this.designationId = designationId;
    }

    public Employeestatus getEmployeestatusId() {
        return employeestatusId;
    }

    public void setEmployeestatusId(Employeestatus employeestatusId) {
        this.employeestatusId = employeestatusId;
    }

    public Gender getGenderId() {
        return genderId;
    }

    public void setGenderId(Gender genderId) {
        this.genderId = genderId;
    }

    @XmlTransient
    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }


    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<User> getUserList1() {
        return userList1;
    }

    public void setUserList1(List<User> userList1) {
        this.userList1 = userList1;
    }


    @XmlTransient
    public List<Operationlog> getOperationlogList() {
        return operationlogList;
    }

    public void setOperationlogList(List<Operationlog> operationlogList) {
        this.operationlogList = operationlogList;
    }

    @XmlTransient
    public List<Gurantordetail> getGurantordetailList() {
        return gurantordetailList;
    }

    public void setGurantordetailList(List<Gurantordetail> gurantordetailList) { this.gurantordetailList = gurantordetailList; }

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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Employee[ id=" + id + " ]";
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }



}
