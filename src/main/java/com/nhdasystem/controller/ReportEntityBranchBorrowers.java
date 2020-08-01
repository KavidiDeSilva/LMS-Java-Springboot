package com.nhdasystem.controller;



public class ReportEntityBranchBorrowers {
    String branch;
    Integer loans;


    public ReportEntityBranchBorrowers(String branch, Integer loans) {
        this.branch = branch;
        this.loans = loans;
    }
    public String getBranch() {
        return branch;
    }

    public Integer getLoans() {
        return loans;
    }



}
