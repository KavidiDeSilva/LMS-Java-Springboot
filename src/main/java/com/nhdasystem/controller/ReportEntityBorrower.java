package com.nhdasystem.controller;


import java.math.BigDecimal;
import java.sql.Date;

public class ReportEntityBorrower {
    String borrower_name;
    String nic;
    String loan_name;
    BigDecimal amount;
    Integer duration;
    BigDecimal equated_monthly_value;
    Date granted_date;
    Date maturity_date;

    public ReportEntityBorrower(String borrower_name, String nic, String loan_name, BigDecimal amount, Integer duration, BigDecimal equated_monthly_value, Date granted_date, Date maturity_date) {
        this.borrower_name = borrower_name;
        this.nic = nic;
        this.loan_name = loan_name;
        this.amount = amount;
        this.duration = duration;
        this.equated_monthly_value = equated_monthly_value;
        this.granted_date = granted_date;
        this.maturity_date = maturity_date;
    }

    public String getBorrower_name() {
        return borrower_name;
    }

    public String getNic() {
        return nic;
    }

    public String getLoan_name() {
        return loan_name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getDuration() {
        return duration;
    }

    public BigDecimal getEquated_monthly_value() {
        return equated_monthly_value;
    }

    public Date getGranted_date() {
        return granted_date;
    }

    public Date getMaturity_date() {
        return maturity_date;
    }




}
