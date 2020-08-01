package com.nhdasystem.dao;

import com.nhdasystem.entity.Loan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface LoanDao extends JpaRepository<Loan, Integer> {

    @Query(value="SELECT new Loan(l.id,l.name,l.amount,l.duration,l.equatedmvalue,l.loantypeId,l.clientId,l.gurantordetailId) FROM Loan l")
    List<Loan> list();

    @Query(value="SELECT new Loan(l.id,l.name,l.amount,l.duration,l.equatedmvalue,l.loantypeId,l.clientId,l.gurantordetailId) " +
            "FROM Loan l WHERE l.loanstatusId.id= 1 ")
    List<Loan> listofopenloans();

//    @Query(value="SELECT new Loan (l.id,l.name,l.amount,l.duration,l.equatedmvalue,l.loantypeId,l.clientId,l" +
//            ".gurantordetailId) FROM Loan l where l.clientId.id=:clientId")
//    List<Loan> listByClients(@Param("clientId") Integer clientId);
//
//    @Query("SELECT l FROM Loan l ORDER BY l.id DESC")
//    Page<Loan> findAll(Pageable of);

    @Query("SELECT l FROM Loan l ORDER BY l.id DESC")
    Page<Loan> findAll(Pageable of);


    @Query("SELECT l FROM Loan l WHERE l.name= :name")
    Loan findByName(@Param("name")String name);


    @Query("SELECT SUBSTRING(l.name,LOCATE('R',l.name)+2) AS INT FROM Loan l")
    List<String> lastLoanno();


}
