package com.nhdasystem.dao;


import com.nhdasystem.entity.Loanstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanstatusDao extends JpaRepository<Loanstatus, Integer>
{

    @Query(value="SELECT new Loanstatus (l.id,l.name) FROM Loanstatus l")
    List<Loanstatus> list();

    @Query("SELECT l FROM Loanstatus l WHERE l.name= :name")
    Loanstatus findByName(@Param("name")String name);

   
}