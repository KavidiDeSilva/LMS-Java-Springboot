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

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "criteria")
@NamedQueries({
    @NamedQuery(name = "Criteria.findAll", query = "SELECT c FROM Criteria c")})
public class Criteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Criteria")
    private String name;
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "constraint1_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Constraint1 constraintId;

    @JoinColumn(name = "loantype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Loantype loantypeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criteriaId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loancriteria> loancriteriaList;


    public Criteria() {
    }

    public Criteria(Integer id) {
        this.id = id;
    }

    public Criteria(Integer id, String name,String value, Constraint1 constraintId) {
        this.id = id;
        this.name=name;
        this.value=value;
        this.constraintId=constraintId;
    }
//    public Criteria(Integer id, String name) {
//        this.id = id;
//        this.name = name;
//    }


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


    public Loantype getLoantypeId() { return loantypeId; }

    public void setLoantypeId(Loantype loantypeId) { this.loantypeId = loantypeId; }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Constraint1 getConstraintId() {
        return constraintId;
    }

    public void setConstraintId(Constraint1 constraintId) {
        this.constraintId = constraintId;
    }

    public List<Loancriteria> getLoancriteriaList() { return loancriteriaList; }

    public void setLoancriteriaList(List<Loancriteria> loancriteriaList) { this.loancriteriaList = loancriteriaList; }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criteria)) {
            return false;
        }
        Criteria other = (Criteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Criteria[ id=" + id + " ]";
    }
    
}
