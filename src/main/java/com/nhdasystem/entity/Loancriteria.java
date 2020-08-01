/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "loancriteria")
@NamedQueries({
    @NamedQuery(name = "Loancriteria.findAll", query = "SELECT l FROM Loancriteria l")})
public class Loancriteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "criteria_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Criteria criteriaId;
    @JoinColumn(name = "loan_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Loan loanId;

    public Loancriteria() {
    }

    public Loancriteria(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Criteria getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Criteria criteriaId) {
        this.criteriaId = criteriaId;
    }

    public Loan getLoanId() {
        return loanId;
    }

    public void setLoanId(Loan loanId) {
        this.loanId = loanId;
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
        if (!(object instanceof Loancriteria)) {
            return false;
        }
        Loancriteria other = (Loancriteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Loancriteria[ id=" + id + " ]";
    }
    
}
