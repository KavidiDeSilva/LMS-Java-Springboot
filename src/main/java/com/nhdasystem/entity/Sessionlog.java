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
@Table(name = "sessionlog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sessionlog.findAll", query = "SELECT s FROM Sessionlog s")})
public class Sessionlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "logintime")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logintime;
    @Column(name = "logouttime")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logouttime;
    @Lob
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "sessionstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Sessionstatus sessionstatusId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User userId;

    public Sessionlog() {
    }

    public Sessionlog(Integer id) {
        this.id = id;
    }
    public Sessionlog(Integer id,String token) {
        this.id = id;
        this.token=token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getLogintime() {
        return logintime;
    }

    public void setLogintime(LocalDateTime logintime) {
        this.logintime = logintime;
    }

    public LocalDateTime getLogouttime() {
        return logouttime;
    }

    public void setLogouttime(LocalDateTime logouttime) {
        this.logouttime = logouttime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sessionstatus getSessionstatusId() {
        return sessionstatusId;
    }

    public void setSessionstatusId(Sessionstatus sessionstatusId) {
        this.sessionstatusId = sessionstatusId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Sessionlog)) {
            return false;
        }
        Sessionlog other = (Sessionlog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Sessionlog[ id=" + id + " ]";
    }
    
}
