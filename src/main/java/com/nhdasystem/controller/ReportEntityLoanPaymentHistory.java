package com.nhdasystem.controller;


import java.math.BigDecimal;
import java.sql.Date;


public class ReportEntityLoanPaymentHistory {
    String borrowerloanno;
    String receipt;
    BigDecimal principal;
    BigDecimal interestamount;
    BigDecimal repaymentamount;
    Date dopay;
    BigDecimal balance;
    BigDecimal paid;
    String paidthrough;
    String assignemployee;


    public ReportEntityLoanPaymentHistory(String borrowerloanno,String receipt, BigDecimal principal, BigDecimal interestamount, BigDecimal repaymentamount, Date dopay, BigDecimal balance, BigDecimal paid, String paidthrough, String assignemployee) {
        this.borrowerloanno = borrowerloanno;
        this.receipt = receipt;
        this.principal = principal;
        this.interestamount = interestamount;
        this.repaymentamount = repaymentamount;
        this.dopay = dopay;
        this.balance = balance;
        this.paid = paid;
        this.paidthrough = paidthrough;
        this.assignemployee = assignemployee;
    }


    public String getBorrowerloanno() {
        return borrowerloanno;
    }

    public String getReceipt() { return receipt; }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getInterestamount() {
        return interestamount;
    }

    public BigDecimal getRepaymentamount() {
        return repaymentamount;
    }

    public Date getDopay() {
        return dopay;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public String getPaidthrough() {
        return paidthrough;
    }

    public String getAssignemployee() {
        return assignemployee;
    }

}
