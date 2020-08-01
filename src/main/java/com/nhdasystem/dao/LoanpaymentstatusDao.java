package com.nhdasystem.dao;


import com.nhdasystem.entity.Loanpaymentstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface LoanpaymentstatusDao extends JpaRepository<Loanpaymentstatus, Integer>
{

    @Query(value="SELECT new Loanpaymentstatus(l.id,l.name) FROM Loanpaymentstatus l")
    List<Loanpaymentstatus> list();


    @Query("SELECT l FROM Loanpaymentstatus l WHERE l.name= :name")
    Loanpaymentstatus findByName(@Param("name") String name);


}