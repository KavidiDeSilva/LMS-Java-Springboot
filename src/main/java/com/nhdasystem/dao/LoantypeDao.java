package com.nhdasystem.dao;

import com.nhdasystem.entity.Loantype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;



public interface LoantypeDao extends JpaRepository<Loantype, Integer> {

    @Query(value="SELECT new Loantype (l.id,l.number,name,l.interestrate) FROM Loantype l")
    List<Loantype> list();

//    @Query("SELECT l FROM Loantype l WHERE l.interestrate= :interestrate")
//    Loantype findByInterestrate(@Param("interestrate")String interestrate);

    @Query("SELECT l FROM Loantype l WHERE l.name= :name")
    Loantype findByName(@Param("name")String name);
}
