package com.nhdasystem.controller;


import java.sql.Date;

public class ReportEntityBorrowerDetails {
    String code;
    String fullname;
    String nic;
    String address;
    Integer mobileno;
    Integer landno;
    Date regdate;


    public ReportEntityBorrowerDetails(String code, String fullname, String nic, String address, Integer mobileno, Integer landno, Date regdate) {
        this.code = code;
        this.fullname = fullname;
        this.nic = nic;
        this.address = address;
        this.mobileno = mobileno;
        this.landno = landno;
        this.regdate = regdate;
    }



    public String getCode() {
        return code;
    }

    public String getFullname() {
        return fullname;
    }

    public String getNic() {
        return nic;
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

    public Date getRegdate() {
        return regdate;
    }

}
