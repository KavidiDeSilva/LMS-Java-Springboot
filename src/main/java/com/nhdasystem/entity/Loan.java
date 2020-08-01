/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhdasystem.util.RegexPattern;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
//import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "loan")
@NamedQueries({
        @NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l")})
public class Loan implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    //@Pattern(regexp = "^.*$", message = "Invalid Loan No")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Principal Amount")
    private BigDecimal amount;
    @Column(name = "duration")
    @RegexPattern(regexp = "^([0-9]{1,4})$", message = "Invalid Duration")
    private String duration;
    @Column(name = "reqamount")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Requested Amount")
    private BigDecimal reqamount;
    @Column(name = "reqdate")
    private LocalDate reqdate;
    @Column(name = "dogranted")
    private LocalDate dogranted;
    @Column(name = "equatedmvalue")
    @RegexPattern(regexp = "^([0-9]{0,6}[.][0-9]{2})$", message = "Invalid Equated Monthly Value")
    private BigDecimal equatedmvalue;
    @Column(name = "domaturity")
    //@Temporal(TemporalType.DATE)
    private LocalDate domaturity;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Client clientId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "loanstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Loanstatus loanstatusId;
    @JoinColumn(name = "loantype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Loantype loantypeId;
    @JoinColumn(name = "purposeofloan_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Purposeofloan purposeofloanId;
    @JoinColumn(name = "gurantordetail_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Gurantordetail gurantordetailId;
    @JoinColumn(name = "propertydetail_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Propertydetail propertydetailId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loanpayment> loanpaymentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loanId", fetch = FetchType.LAZY, orphanRemoval = true)
    //@JsonIgnore
    private List<Loancriteria> loancriteriaList;


    public Loan() {
    }
    public Loan(Integer id,String name,BigDecimal amount,String duration,BigDecimal equatedmvalue,Loantype
            loantypeId, Client clientId,Gurantordetail gurantordetailId) {
        this.id = id;
        this.name=name;
        this.amount=amount;
        this.duration=duration;
        this.equatedmvalue=equatedmvalue;
        this.loantypeId=loantypeId;
        this.clientId=clientId;
        this.gurantordetailId=gurantordetailId;
    }


    public Loan(Integer id) {
        this.id = id;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigDecimal getReqamount() {
        return reqamount;
    }

    public void setReqamount(BigDecimal reqamount) {
        this.reqamount = reqamount;
    }

    public LocalDate getReqdate() {
        return reqdate;
    }

    public void setReqdate(LocalDate reqdate) {
        this.reqdate = reqdate;
    }

    public LocalDate getDogranted() {
        return dogranted;
    }

    public void setDogranted(LocalDate dogranted) {
        this.dogranted = dogranted;
    }

    public BigDecimal getEquatedmvalue() {
        return equatedmvalue;
    }

    public void setEquatedmvalue(BigDecimal equatedmvalue) {
        this.equatedmvalue = equatedmvalue;
    }

    public LocalDate getDomaturity() { return domaturity; }

    public void setDomaturity(LocalDate domaturity) { this.domaturity = domaturity; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Loanstatus getLoanstatusId() {
        return loanstatusId;
    }

    public void setLoanstatusId(Loanstatus loanstatusId) {
        this.loanstatusId = loanstatusId;
    }

    public Loantype getLoantypeId() {
        return loantypeId;
    }

    public void setLoantypeId(Loantype loantypeId) {
        this.loantypeId = loantypeId;
    }

    public Purposeofloan getPurposeofloanId() { return purposeofloanId; }

    public void setPurposeofloanId(Purposeofloan purposeofloanId) { this.purposeofloanId = purposeofloanId;}

    public Gurantordetail getGurantordetailId() { return gurantordetailId; }

    public void setGurantordetailId(Gurantordetail gurantordetailId) { this.gurantordetailId = gurantordetailId; }

    public Propertydetail getPropertydetailId() { return propertydetailId; }

    public void setPropertydetailId(Propertydetail propertydetailId) { this.propertydetailId = propertydetailId; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loan)) {
            return false;
        }
        Loan other = (Loan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Loan[ id=" + id + " ]";
    }

    public List<Loanpayment> getLoanpaymentList() {
        return loanpaymentList;
    }

    public void setLoanpaymentList(List<Loanpayment> loanpaymentList) {
        this.loanpaymentList = loanpaymentList;
    }

    public List<Loancriteria> getLoancriteriaList() {
        return loancriteriaList;
    }

    public void setLoancriteriaList(List<Loancriteria> loancriteriaList) {
        this.loancriteriaList = loancriteriaList;
    }


}
