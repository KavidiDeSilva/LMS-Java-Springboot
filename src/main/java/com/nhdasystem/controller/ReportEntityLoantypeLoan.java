package com.nhdasystem.controller;



public class ReportEntityLoantypeLoan {
    String loantype;
    Integer loans;


    public ReportEntityLoantypeLoan(String loantype, Integer loans) {
        this.loantype = loantype;
        this.loans = loans;
    }
    public String getLoantype() {
        return loantype;
    }

    public Integer getLoans() {
        return loans;
    }



}
