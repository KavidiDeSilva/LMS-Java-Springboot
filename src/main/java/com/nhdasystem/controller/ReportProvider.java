package com.nhdasystem.controller;



import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReportProvider {

    public static List<ReportEntitySystemAccessAnalysis> getSystemAccessAnalysisReport(LocalDateTime startdate, LocalDateTime enddate) {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT d.name as name, count(*) as attempt FROM nhda.sessionlog as s, nhda.user as u, nhda.employee as e, nhda.designation as d where s.user_id=u.id and u.employee_id=e.id and e.designation_id=d.id and s.logintime between '" + startdate + "' and '" + enddate + "' group by d.id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntitySystemAccessAnalysis> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntitySystemAccessAnalysis report = new ReportEntitySystemAccessAnalysis(rs.getString("name"), rs.getInt("attempt"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityBorrower> getBorrowerReport() {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT c.name as borrower_name,c.nic,ln.name as loan_name,ln.amount,ln.duration,ln.equatedmvalue as equated_monthly_value ,ln.dogranted as granted_date,ln.domaturity as maturity_date FROM nhda.client as c, nhda.loan as ln where c.id=ln.client_id group by c.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityBorrower> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityBorrower report = new ReportEntityBorrower(
                        rs.getString("borrower_name"),
                        rs.getString("nic"),
                        rs.getString("loan_name"),
                        rs.getBigDecimal("amount"),
                        rs.getInt("duration"),
                        rs.getBigDecimal("equated_monthly_value"),
                        rs.getDate("granted_date"),
                        rs.getDate("maturity_date"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityBorrowerDetails> getBorrowerDetailsReport() {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT c.code,c.fullname, c.nic,c.address, c.mobileno, c.landno , c.regdate  FROM nhda.client as c;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityBorrowerDetails> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityBorrowerDetails report = new ReportEntityBorrowerDetails(
                        rs.getString("code"),
                        rs.getString("fullname"),
                        rs.getString("nic"),
                        rs.getString("address"),
                        rs.getInt("mobileno"),
                        rs.getInt("landno"),
                        rs.getDate("regdate"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityBranch> getBranchReport() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT  b.name as district_office, b.address, b.mobileno,b.landno, b.fax, b.email,d.name as district FROM nhda.branch as b, nhda.district as d where b.district_id=d.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityBranch> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityBranch report = new ReportEntityBranch(
                        rs.getString("district_office"),
                        rs.getString("address"),
                        rs.getInt("mobileno"),
                        rs.getInt("landno"),
                        rs.getInt("fax"),
                        rs.getString("email"),
                        rs.getString("district"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }


    public static List<ReportEntityLoan> getLoanReport() {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT  l.name as borrower_loan_no,lp.principal, sum(lp.repaymentamount) as total_repayment_amount, lp.principal- sum(lp.repaymentamount) as balance ," +
                    "count(lp.name) as no_repayments FROM nhda.loan as l,nhda.loanpayment as lp where l.id=lp.loan_id group by loan_id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityLoan> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityLoan report = new ReportEntityLoan(
                        rs.getString("borrower_loan_no"),
                        rs.getBigDecimal("principal"),
                        rs.getBigDecimal("total_repayment_amount"),
                        rs.getBigDecimal("balance"),
                        rs.getInt("no_repayments"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }
    public static List<ReportEntityLoan> getLoansReport(String name) {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT  l.name as borrower_loan_no,lp.principal, sum(lp.repaymentamount) as total_repayment_amount, lp.principal- sum(lp.repaymentamount) as balance ," +
                    "count(lp.name) as no_repayments FROM nhda.loan as l,nhda.loanpayment as lp where l.id=lp.loan_id" +
                    " and l.name='" + name + "'  group by loan_id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityLoan> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityLoan report = new ReportEntityLoan(
                        rs.getString("borrower_loan_no"),
                        rs.getBigDecimal("principal"),
                        rs.getBigDecimal("total_repayment_amount"),
                        rs.getBigDecimal("balance"),
                        rs.getInt("no_repayments"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }


    public static List<ReportEntityBorrowerRepayments> getLoanPaymentDeialsReport() {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT l.name as borrower_loan_no ,l.amount, lp.principal, lp.interestamount,l" +
                    ".equatedmvalue, sum(lp.repaymentamount) as total_repayment_amount,lp.principal - sum(lp" +
                    ".repaymentamount) as balance,l.duration*12-(count(lp.name)) as remain,count(lp.name) as no_repayments,l.duration * 12 as duration FROM " +
                    "nhda.loan as l,nhda.loanpayment as lp where l.id=lp.loan_id group by loan_id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityBorrowerRepayments> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityBorrowerRepayments report = new ReportEntityBorrowerRepayments(
                        rs.getString("borrower_loan_no"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("principal"),
                        rs.getBigDecimal("interestamount"),
                        rs.getBigDecimal("equatedmvalue"),
                        rs.getBigDecimal("total_repayment_amount"),
                        rs.getBigDecimal("balance"),
                        rs.getInt("no_repayments"),
                        rs.getInt("remain"),
                        rs.getInt("duration"));

                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityBorrowerRepayments> getBorrowerRepaymentsReport(String name) {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT l.name as borrower_loan_no ,l.amount, lp.principal, lp.interestamount,l" +
                    ".equatedmvalue, sum(lp.repaymentamount) as total_repayment_amount,lp.principal- sum(lp" +
                    ".repaymentamount) as balance,count(lp.name) as no_repayments,l.duration*12-(count(lp.name)) as remain,l.duration * 12 as duration FROM " +
                    "nhda.loan as l,nhda.loanpayment as lp where l.id=lp.loan_id and l.name='" + name + "' group by " +
                    "loan_id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityBorrowerRepayments> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityBorrowerRepayments report = new ReportEntityBorrowerRepayments(
                        rs.getString("borrower_loan_no"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("principal"),
                        rs.getBigDecimal("interestamount"),
                        rs.getBigDecimal("equatedmvalue"),
                        rs.getBigDecimal("total_repayment_amount"),
                        rs.getBigDecimal("balance"),
                        rs.getInt("no_repayments"),
                        rs.getInt("remain"),
                        rs.getInt("duration"));

                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityBranchBorrowers> getBranchBorrowersReport() {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT b.name as branch, count(l.employee_id) as loans from  nhda.branch as b, nhda" +
                    ".employee as e, nhda" +
                    ".loan as l where e.id=l.employee_id and e.branch_id=b.id group by b.id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityBranchBorrowers> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityBranchBorrowers report = new ReportEntityBranchBorrowers(
                        rs.getString("branch"),
                        rs.getInt("loans"));

                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityLoanPaymentHistory> getLoanPaymentHistory(String name) {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT l.name as borrowerloanno, lp.name as receipt,lp.principal,lp.interestamount,lp" +
                    ".repaymentamount,lp.dopay,lp.balance,paid,lp.paidthrough,lp.assignemployee FROM nhda.loanpayment" +
                    " as lp, nhda.loan as l where l.id=lp.loan_id and l.name='" + name + "';";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityLoanPaymentHistory> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityLoanPaymentHistory report = new ReportEntityLoanPaymentHistory(
                        rs.getString("borrowerloanno"),
                        rs.getString("receipt"),
                        rs.getBigDecimal("principal"),
                        rs.getBigDecimal("interestamount"),
                        rs.getBigDecimal("repaymentamount"),
                        rs.getDate("dopay"),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("paid"),
                        rs.getString("paidthrough"),
                        rs.getString("assignemployee"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityLoanPaymentHistory> getLoanPaymentHistories() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT l.name, lp.name as receipt,lp.principal,lp.interestamount,lp.repaymentamount,lp.dopay,lp.balance,paid,lp.paidthrough,lp.assignemployee FROM nhda.loanpayment as lp, nhda.loan as l where l.id=lp.loan_id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityLoanPaymentHistory> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityLoanPaymentHistory report = new ReportEntityLoanPaymentHistory(
                        rs.getString("name"),
                        rs.getString("receipt"),
                        rs.getBigDecimal("principal"),
                        rs.getBigDecimal("interestamount"),
                        rs.getBigDecimal("repaymentamount"),
                        rs.getDate("dopay"),
                        rs.getBigDecimal("balance"),
                        rs.getBigDecimal("paid"),
                        rs.getString("paidthrough"),
                        rs.getString("assignemployee"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityLoanHistory> getLoanHistory(String fullname) {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT lt.name as ltype, c.fullname,l.amount,l.equatedmvalue,l.dogranted,l.duration,l.domaturity,pl.name as purposeofloan,ls.name as status FROM nhda.loan as l,nhda.loantype as lt,nhda.client as c,nhda.gurantordetail as g,nhda.propertydetail as p,nhda.purposeofloan as pl,nhda.loanstatus as ls where lt.id=l.loantype_id and ls.id=l.loanstatus_id and pl.id=l.purposeofloan_id and c.id=l.client_id and g.id=l.gurantordetail_id and p.id=l.propertydetail_id and c.fullname='" + fullname + "';";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityLoanHistory> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityLoanHistory report = new ReportEntityLoanHistory(
                        rs.getString("ltype"),
                        rs.getString("fullname"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("equatedmvalue"),
                        rs.getDate("dogranted"),
                        rs.getInt("duration"),
                        rs.getDate("domaturity"),
                        rs.getString("purposeofloan"),
                        rs.getString("status"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityLoanHistory> getLoanHistories() {
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT lt.name as ltype, c.fullname,l.amount,l.equatedmvalue,l.dogranted,l.duration,l.domaturity,pl.name as purposeofloan,ls.name as status FROM nhda.loan as l,nhda.loantype as lt,nhda.client as c,nhda.gurantordetail as g,nhda.propertydetail as p,nhda.purposeofloan as pl,nhda.loanstatus as ls where lt.id=l.loantype_id and ls.id=l.loanstatus_id and pl.id=l.purposeofloan_id and c.id=l.client_id and g.id=l.gurantordetail_id and p.id=l.propertydetail_id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityLoanHistory> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityLoanHistory report = new ReportEntityLoanHistory(
                        rs.getString("ltype"),
                        rs.getString("fullname"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("equatedmvalue"),
                        rs.getDate("dogranted"),
                        rs.getInt("duration"),
                        rs.getDate("domaturity"),
                        rs.getString("purposeofloan"),
                        rs.getString("status"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityLoantypeLoan> getLoantypeLoansReport() {

        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT lt.name as loantype,count(l.id) as loans FROM nhda.loantype lt ,nhda.loan l where lt.id=l.loantype_id group by lt.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityLoantypeLoan> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityLoantypeLoan report = new ReportEntityLoantypeLoan(
                        rs.getString("loantype"),
                        rs.getInt("loans"));

                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public static List<ReportEntityBranchEmployees> getBranchEmployeesReport() {

//
        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT b.name as branch,count(e.id) as employees FROM nhda.branch b,nhda.employee e where b.id=e.branch_id group by b.id;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityBranchEmployees> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityBranchEmployees report = new ReportEntityBranchEmployees(
                        rs.getString("branch"),
                        rs.getInt("employees"));

                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }




















}












