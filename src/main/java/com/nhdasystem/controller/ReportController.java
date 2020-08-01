package com.nhdasystem.controller;


import com.nhdasystem.util.ModuleList;


import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReportController {



    @RequestMapping(value = "/reports/systemaccessanalysis", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            LocalDateTime startdate = LocalDateTime.now().minusDays(35);
            LocalDateTime enddate = LocalDateTime.now().plusDays(1);
            return ReportProvider.getSystemAccessAnalysisReport(startdate, enddate);
        }
        else return  null;


    }

    @RequestMapping(value = "/reports/systemaccessanalysis", params = {"startdate", "enddate"}, method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis2(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime stdate=LocalDateTime.parse(startdate+" 00:00",formatter );
            LocalDateTime endate=LocalDateTime.parse(enddate +" 00:00",formatter);
            return ReportProvider.getSystemAccessAnalysisReport(stdate,endate);
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/clientdetails", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityBorrower> clientsdetails(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.CLIENT,AuthProvider.SELECT)) {
            return ReportProvider.getBorrowerReport();
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/borrowersdetails", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityBorrowerDetails> borrowerdetails(@CookieValue(value="username") String username, @CookieValue
            (value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.CLIENT,AuthProvider.SELECT)) {
            return ReportProvider.getBorrowerDetailsReport();
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/branchdetails", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityBranch> branchdetails(@CookieValue(value="username") String username, @CookieValue
            (value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.BRANCH,AuthProvider.SELECT)) {
            return ReportProvider.getBranchReport();
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/loandetails", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityLoan> loandetails(@CookieValue(value="username") String username, @CookieValue
            (value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOAN,AuthProvider.SELECT)) {
            return ReportProvider.getLoanReport();
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/loansdetails", params = {"name"}, method = RequestMethod.GET, produces =
            "application/json")
    public List<ReportEntityLoan> loans(@CookieValue(value="username") String username, @CookieValue
            (value="password") String password, @RequestParam("name") String name) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOAN,AuthProvider.SELECT)) {
            return ReportProvider.getLoansReport(name);
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/loanpaymentdetails", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityBorrowerRepayments> loanpaymentdetails(@CookieValue(value="username") String username, @CookieValue
            (value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT,AuthProvider.SELECT)) {
            return ReportProvider.getLoanPaymentDeialsReport();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/borrowerrepayments", params = {"name"}, method = RequestMethod.GET, produces =
            "application/json")
    public List<ReportEntityBorrowerRepayments> borrowerrepayments(@CookieValue(value="username") String username,
                                                                   @CookieValue(value="password") String password,
                                                                   @RequestParam("name") String name) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT,AuthProvider.SELECT)) {

            return ReportProvider.getBorrowerRepaymentsReport(name);
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/branchborrowers", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityBranchBorrowers> branchborrowers(@CookieValue(value="username") String username, @CookieValue(value="password")
            String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.BRANCH,AuthProvider.SELECT)) {
            return ReportProvider.getBranchBorrowersReport();
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/loanpaymenthistory", params = {"name"}, method = RequestMethod.GET, produces =
            "application/json")
    public List<ReportEntityLoanPaymentHistory> loanpaymenthistory(@CookieValue(value="username") String username,
                                                                   @CookieValue(value="password") String password,
                                                                   @RequestParam("name") String name) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT,AuthProvider.SELECT)) {
            return ReportProvider.getLoanPaymentHistory(name);
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/loanpaymenthistories", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityLoanPaymentHistory> loanpaymenthistories(@CookieValue(value="username") String username,
                                                               @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT,AuthProvider.SELECT)) {
            return ReportProvider.getLoanPaymentHistories();
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/loanhistory", params = {"fullname"}, method = RequestMethod.GET, produces =
            "application/json")
    public List<ReportEntityLoanHistory> loanhistory(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("fullname") String fullname) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOAN,AuthProvider.SELECT)) {
            return ReportProvider.getLoanHistory(fullname);
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/loanhistories", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityLoanHistory> loanhistories(@CookieValue(value="username") String username,
                                                                     @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOAN,AuthProvider.SELECT)) {
            return ReportProvider.getLoanHistories();
        }
        else return  null;
    }
    @RequestMapping(value = "/reports/loantypeloans", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityLoantypeLoan> loantypesloans(@CookieValue(value="username") String username, @CookieValue
            (value="password")
            String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.LOANTYPE,AuthProvider.SELECT)) {
            return ReportProvider.getLoantypeLoansReport();
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/branchemployees", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityBranchEmployees> branchemployees(@CookieValue(value="username") String username, @CookieValue(value="password")
            String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.BRANCH,AuthProvider.SELECT)) {
            return ReportProvider.getBranchEmployeesReport();
        }
        else return  null;
    }

}
