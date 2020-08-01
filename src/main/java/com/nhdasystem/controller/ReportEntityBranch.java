package com.nhdasystem.controller;


import java.math.BigDecimal;
import java.sql.Date;

public class ReportEntityBranch {
    String district_office;
    String address;
    Integer mobileno;
    Integer landno;
    Integer fax;
    String email;
    String district;


    public ReportEntityBranch( String district_office, String address, Integer mobileno, Integer landno, Integer fax, String email, String district) {
        this.district_office = district_office;
        this.address = address;
        this.mobileno = mobileno;
        this.landno = landno;
        this.fax = fax;
        this.email = email;
        this.district = district;
    }


    public String getDistrict_office() {
        return district_office;
    }

    public String getAddress() {
        return address;
    }

    public Integer getMobileno() {
        return mobileno;
    }

    public Integer getLandno() {
        return landno;
    }

    public Integer getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getDistrict() {
        return district;
    }




}
