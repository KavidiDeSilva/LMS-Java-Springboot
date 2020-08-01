/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import com.nhdasystem.util.RegexPattern;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "loanpayment")
@NamedQueries({
    @NamedQuery(name = "Loanpayment.findAll", query = "SELECT l FROM Loanpayment l")})
public class Loanpayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
   // @Pattern(regexp = "^([A-Z0-9a-z]{3,})$", message = "Invalid Receipt No")
    private String name;
    @Column(name = "principal")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Principal Amount")
    private String principal;
    @Column(name = "interestamount")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Interest Amount")
    private String interestamount;
    @Column(name = "repaymentamount")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Repayment Amount")
    private String repaymentamount;
    @Column(name = "balance")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Balance")
    private String balance;
    @Column(name = "dopay")
   // @Temporal(TemporalType.DATE)
    private LocalDate dopay;
    @Column(name = "due")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Due Amount")
    private BigDecimal due;
    @Column(name = "paid")
    @RegexPattern(regexp = "^([0-9]{0,7}[.][0-9]{2})$", message = "Invalid Paid Amount")
    private BigDecimal paid;
    @Column(name = "lastpayment")
    //@Temporal(TemporalType.DATE)
    private LocalDate lastpayment;
    @Column(name = "paidthrough")
    private String paidthrough;
    @Column(name = "assignemployee")
    private String assignemployee;
    @Lob
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "loanpaymentstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Loanpaymentstatus loanpaymentstatusId;
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Loan loanId;

    public Loanpayment() {
    }

    public Loanpayment(Integer id) {
        this.id = id;
    }

    public Loanpayment(Integer id,String name,String repaymentamount,LocalDate lastpayment,BigDecimal paid, Loan loanId) {
        this.id = id;
        this.name=name;
        this.repaymentamount=repaymentamount;
        this.lastpayment=lastpayment;
        this.paid=paid;
        this.loanId=loanId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getInterestamount() {
        return interestamount;
    }

    public void setInterestamount(String interestamount) {
        this.interestamount = interestamount;
    }

    public String getRepaymentamount() {
        return repaymentamount;
    }

    public void setRepaymentamount(String repaymentamount) {
        this.repaymentamount = repaymentamount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
    public LocalDate getDopay() { return dopay; }

    public void setDopay(LocalDate dopay) { this.dopay = dopay; }

    public Loan getLoanId() { return loanId; }

    public void setLoanId(Loan loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getDue() { return due; }

    public void setDue(BigDecimal due) { this.due = due; }

    public BigDecimal getPaid() { return paid; }

    public void setPaid(BigDecimal paid) { this.paid = paid; }

    public LocalDate getLastpayment() { return lastpayment; }

    public void setLastpayment(LocalDate lastpayment) { this.lastpayment = lastpayment; }

    public String getPaidthrough() { return paidthrough; }

    public void setPaidthrough(String paidthrough) { this.paidthrough = paidthrough; }

    public String getAssignemployee() { return assignemployee; }

    public void setAssignemployee(String assignemployee) { this.assignemployee = assignemployee; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Loanpaymentstatus getLoanpaymentstatusId() { return loanpaymentstatusId; }

    public void setLoanpaymentstatusId(Loanpaymentstatus loanpaymentstatusId) { this.loanpaymentstatusId = loanpaymentstatusId; }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loanpayment)) {
            return false;
        }
        Loanpayment other = (Loanpayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Loanpayment[ id=" + id + " ]";
    }
    
}
