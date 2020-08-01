package com.nhdasystem.controller;



public class ReportEntityBranchEmployees {
    String branch;
    Integer employees;


    public ReportEntityBranchEmployees(String branch, Integer employees) {
        this.branch = branch;
        this.employees = employees;
    }
    public String getBranch() {
        return branch;
    }

    public Integer getEmployees() {
        return employees;
    }



}
