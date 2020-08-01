package com.nhdasystem.controller;



public class ReportEntitySystemAccessAnalysis {
    String designation;
    Integer attempt;

    public ReportEntitySystemAccessAnalysis(String designation, Integer attempt ){
        this.designation=designation; this.attempt=attempt;

    }

    public String getDesignation(){
        return this.designation;

    }

    public Integer getAttempt(){
        return this.attempt;

    }



}
