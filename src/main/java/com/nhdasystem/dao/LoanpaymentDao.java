package com.nhdasystem.dao;


import com.nhdasystem.entity.Loanpayment;
import com.nhdasystem.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface LoanpaymentDao extends JpaRepository<Loanpayment, Integer>
{

    @Query(value="SELECT new Loanpayment(l.id,l.name,l.repaymentamount,l.lastpayment,l.paid,l.loanId) FROM Loanpayment l")
    List<Loanpayment> list();

    @Query("SELECT l FROM Loanpayment l ORDER BY l.id DESC")
    Page<Loanpayment> findAll(Pageable of);

//    @Query(value="SELECT new Loanpayment (l.id,l.name,l.repaymentamount,l.lastpayment,l.paid,l.loanId) FROM Loanpayment l where l.loanId.id=:loanId")
//    List<Loanpayment> listByLoan(@Param("loanId") Integer loanId);
//

    @Query("SELECT SUBSTRING(MAX(l.name),2,5) FROM Loanpayment l")
    String lastLoanpayno();

//    @Query("SELECT sum(l.repaymentamount) FROM Loanpayment l where l.loanId.clientId.id= :clientId")
//    String totalrepayments(@Param("clientId") Integer clientId);

    @Query("SELECT sum(l.repaymentamount) FROM Loanpayment l where l.loanId.id= :loanId")
    String totalrepayments(@Param("loanId") Integer loanId);

    @Query("SELECT max(l.dopay) FROM Loanpayment l where l.loanId.id= :loanId")
    LocalDate lastpaydate(@Param("loanId") Integer loanId);

    @Query("SELECT l FROM Loanpayment l WHERE l.name= :name")
    Loanpayment findByName(@Param("name") String name);


    @Query("SELECT l FROM Loanpayment l WHERE l.loanpaymentstatusId= :loanpaymentstatusId")
    Loanpayment findByLoanpaymentstatusId(@Param("loanpaymentstatusId") Integer loanpaymentstatusId);



}