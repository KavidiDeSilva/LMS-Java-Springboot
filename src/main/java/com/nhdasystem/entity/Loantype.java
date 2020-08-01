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

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "loantype")
@NamedQueries({
    @NamedQuery(name = "Loantype.findAll", query = "SELECT l FROM Loantype l")})
public class Loantype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "number")
    private String number;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Name")
    private String name;
    @Column(name = "code")
    private String code;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "interestrate")
    private BigDecimal interestrate;
    @Column(name = "dointroduced")
    private LocalDate dointroduced;
    @Column(name = "minduration")
    @RegexPattern(regexp = "^([0-9]{1,4})$", message = "Invalid Min Duration")
    private String minduration;
    @Column(name = "maxduration")
    @RegexPattern(regexp = "^([0-9]{1,4})$", message = "Invalid Max Duration")
    private String maxduration;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loantypeId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loan> loanList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loantypeId", fetch = FetchType.LAZY,orphanRemoval = true)
//    @JsonIgnore
    private List<Criteria> criteriaList;
    @JoinColumn(name = "loantypestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Loantypestatus loantypestatusId;
    @JoinColumn(name = "loancategory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Loancategory loancategoryId;

    public Loantype() {
    }

    public Loantype(Integer id) { this.id = id; }


    public Loantype(Integer id,String number,String name,BigDecimal interestrate) {
        this.id = id;
        this.number = number;
        this.name=name;
        this.interestrate=interestrate;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public BigDecimal getInterestrate() { return interestrate; }

    public void setInterestrate(BigDecimal interestrate) { this.interestrate = interestrate; }

    public LocalDate getDointroduced() {
        return dointroduced;
    }

    public void setDointroduced(LocalDate dointroduced) {
        this.dointroduced = dointroduced;
    }

    public String getMinduration() {
        return minduration;
    }

    public void setMinduration(String minduration) {
        this.minduration = minduration;
    }

    public String getMaxduration() {
        return maxduration;
    }

    public void setMaxduration(String maxduration) {
        this.maxduration = maxduration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    public List<Criteria> getCriteriaList() { return criteriaList; }

    public void setCriteriaList(List<Criteria> criteriaList) { this.criteriaList = criteriaList; }

    public Loantypestatus getLoantypestatusId() {
        return loantypestatusId;
    }

    public void setLoantypestatusId(Loantypestatus loantypestatusId) {
        this.loantypestatusId = loantypestatusId;
    }

    public Loancategory getLoancategoryId() { return loancategoryId; }

    public void setLoancategoryId(Loancategory loancategoryId) { this.loancategoryId = loancategoryId; }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loantype)) {
            return false;
        }
        Loantype other = (Loantype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Loantype[ id=" + id + " ]";
    }
    
}
