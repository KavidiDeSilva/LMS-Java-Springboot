package com.nhdasystem.controller;


import java.math.BigDecimal;
import java.sql.Date;

public class ReportEntityBorrowerRepayments {
        String borrower_loan_no;
        BigDecimal amount;
        BigDecimal principal;
        BigDecimal interestamount;
        BigDecimal equatedmvalue;
        BigDecimal total_repayment_amount;
        BigDecimal balance;
        Integer no_repayments;
        Integer remain;
        Integer duration;

    public ReportEntityBorrowerRepayments(String borrower_loan_no, BigDecimal amount, BigDecimal principal,
                                          BigDecimal interestamount, BigDecimal equatedmvalue, BigDecimal total_repayment_amount, BigDecimal balance, Integer no_repayments,Integer remain, Integer duration) {
        this.borrower_loan_no = borrower_loan_no;
        this.amount = amount;
        this.principal = principal;
        this.interestamount = interestamount;
        this.equatedmvalue = equatedmvalue;
        this.total_repayment_amount = total_repayment_amount;
        this.balance = balance;
        this.no_repayments = no_repayments;
        this.remain = remain;
        this.duration = duration;
    }
    public String getBorrower_loan_no() {
        return borrower_loan_no;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getInterestamount() {
        return interestamount;
    }

    public BigDecimal getEquatedmvalue() {
        return equatedmvalue;
    }

    public BigDecimal getTotal_repayment_amount() {
        return total_repayment_amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Integer getNo_repayments() {
        return no_repayments;
    }

    public Integer getRemain() { return remain; }

    public Integer getDuration() {
        return duration;
    }

}
