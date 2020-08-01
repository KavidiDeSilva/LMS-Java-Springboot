package com.nhdasystem.controller;


import java.math.BigDecimal;
import java.sql.Date;

public class ReportEntityLoanHistory {
    String ltype;
    String fullname;
    BigDecimal amount;
    BigDecimal equatedmvalue;

    Date dogranted;
    Integer duration;
    Date domaturity;
    String purposeofloan;
    String status;

    public ReportEntityLoanHistory(String ltype, String fullname, BigDecimal amount, BigDecimal equatedmvalue,  Date dogranted, Integer duration, Date domaturity, String purposeofloan, String status) {
        this.ltype = ltype;
        this.fullname = fullname;
        this.amount = amount;
        this.equatedmvalue = equatedmvalue;

        this.dogranted = dogranted;
        this.duration = duration;
        this.domaturity = domaturity;
        this.purposeofloan = purposeofloan;
        this.status = status;
    }
    public String getLtype() {
        return ltype;
    }

    public String getFullname() {
        return fullname;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getEquatedmvalue() {
        return equatedmvalue;
    }



    public Date getDogranted() {
        return dogranted;
    }

    public Integer getDuration() {
        return duration;
    }

    public Date getDomaturity() {
        return domaturity;
    }

    public String getPurposeofloan() {
        return purposeofloan;
    }

    public String getStatus() {
        return status;
    }

}
