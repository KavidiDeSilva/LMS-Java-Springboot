/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "operationlog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operationlog.findAll", query = "SELECT o FROM Operationlog o")})
public class Operationlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "operation")
    private Short operation;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "datetime")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime datetime;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "module_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Module moduleId;
    @JoinColumn(name = "operationstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Operationstatus operationstatusId;

    public Operationlog() {
    }

    public Operationlog(Integer id) {
        this.id = id;
    }
    public Operationlog(Integer id,String description) {
        this.id = id;
        this.description=description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getOperation() {
        return operation;
    }

    public void setOperation(Short operation) {
        this.operation = operation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Module getModuleId() {
        return moduleId;
    }

    public void setModuleId(Module moduleId) {
        this.moduleId = moduleId;
    }

    public Operationstatus getOperationstatusId() {
        return operationstatusId;
    }

    public void setOperationstatusId(Operationstatus operationstatusId) {
        this.operationstatusId = operationstatusId;
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
        if (!(object instanceof Operationlog)) {
            return false;
        }
        Operationlog other = (Operationlog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Operationlog[ id=" + id + " ]";
    }
    
}
