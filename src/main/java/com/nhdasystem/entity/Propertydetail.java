/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nhdasystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Pattern;

/**
 *
 * @author Kavidi
 */
@Entity
@Table(name = "propertydetail")
@NamedQueries({
        @NamedQuery(name = "Propertydetail.findAll", query = "SELECT p FROM Propertydetail p")})
public class Propertydetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "number")
    private String number;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "housesize")
    private BigDecimal housesize;
    @Column(name = "landsize")
    private BigDecimal landsize;
    @Lob
    @Column(name = "landlocation")
    private String landlocation;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Basic(optional = false)
    @Column(name = "ownership")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Ownership Name")
    private String ownership;
    @Lob
    @Column(name = "ownaddress")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Owner Address")
    private String ownaddress;
    @Lob
    @Column(name = "landaddress")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Land Address")
    private String landaddress;
    @Column(name = "partnername")
    private String partnername;
    @Column(name = "partnerrelation")
    private String partnerrelation;
    @Basic(optional = false)
    @Column(name = "deedno")
    private String deedno;
    @Lob
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propertydetailId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Loan> loanList;
    @JoinColumn(name = "propertystatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Propertystatus propertystatusId;

    public Propertydetail() { }

    public Propertydetail(Integer id) { this.id = id; }

    public Propertydetail(Integer id, String number, String ownership) {
        this.id = id;
        this.number = number;
        this.ownership = ownership;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public BigDecimal getHousesize() { return housesize; }

    public void setHousesize(BigDecimal housesize) { this.housesize = housesize; }

    public BigDecimal getLandsize() { return landsize; }

    public void setLandsize(BigDecimal landsize) { this.landsize = landsize; }

    public String getLandlocation() { return landlocation; }

    public void setLandlocation(String landlocation) { this.landlocation = landlocation; }

    public byte[] getPhoto() { return photo; }

    public void setPhoto(byte[] photo) { this.photo = photo; }

    public String getOwnership() { return ownership; }

    public void setOwnership(String ownership) { this.ownership = ownership; }

    public String getOwnaddress() { return ownaddress; }

    public void setOwnaddress(String ownaddress) { this.ownaddress = ownaddress; }

    public String getLandaddress() { return landaddress; }

    public void setLandaddress(String landaddress) { this.landaddress = landaddress; }

    public String getPartnername() { return partnername; }

    public void setPartnername(String partnername) { this.partnername = partnername; }

    public String getPartnerrelation() { return partnerrelation; }

    public void setPartnerrelation(String partnerrelation) { this.partnerrelation = partnerrelation; }

    public String getDeedno() { return deedno; }

    public void setDeedno(String deedno) { this.deedno = deedno; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<Loan> getLoanList() { return loanList; }

    public void setLoanList(List<Loan> loanList) { this.loanList = loanList; }

    public Propertystatus getPropertystatusId() { return propertystatusId; }

    public void setPropertystatusId(Propertystatus propertystatusId) { this.propertystatusId = propertystatusId; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propertydetail)) {
            return false;
        }
        Propertydetail other = (Propertydetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhdasystem.entity.Propertydetail[ id=" + id + " ]";
    }

}
