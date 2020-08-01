package com.nhdasystem.controller;


import java.math.BigDecimal;


public class ReportEntityLoan {
    String borrower_loan_no;
    BigDecimal principal;
    BigDecimal total_repayment_amount;
    BigDecimal balance;
    Integer no_repayments;


    public ReportEntityLoan(String borrower_loan_no, BigDecimal principal, BigDecimal
            total_repayment_amount,BigDecimal balance ,Integer no_repayments) {
        this.borrower_loan_no = borrower_loan_no;
        this.principal = principal;
        this.total_repayment_amount = total_repayment_amount;
        this.balance = balance;
        this.no_repayments = no_repayments;
    }


    public String getBorrower_loan_no() {
        return borrower_loan_no;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getTotal_repayment_amount() {
        return total_repayment_amount;
    }

    public BigDecimal getbalance() { return balance; }

    public Integer getNo_repayments() {
        return no_repayments;
    }



}
