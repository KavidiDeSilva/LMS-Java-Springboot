package com.nhdasystem.dao;

import com.nhdasystem.entity.Propertydetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertydetailDao extends JpaRepository<Propertydetail,Integer> {

    @Query(value="SELECT new Propertydetail(p.id,p.number,p.ownership) FROM Propertydetail p")
    List<Propertydetail> list();

    @Query(value="SELECT new Propertydetail(p.id,p.number,p.ownership) FROM Propertydetail p WHERE p not in " +
            "(Select l.propertydetailId from Loan l)")
    List<Propertydetail> listWithoutLoans();

    @Query("SELECT p FROM Propertydetail p ORDER BY p.id DESC")
    Page<Propertydetail> findAll(Pageable of);
    
    @Query("SELECT p FROM Propertydetail p WHERE p.number= :number")
    Propertydetail findByNumber(@Param("number")String number);

    @Query("SELECT SUBSTRING(MAX(p.number),3,5) FROM Propertydetail p")
    String lastPropertyno();


}
