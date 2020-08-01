package com.nhdasystem.dao;

import com.nhdasystem.entity.Gurantordetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface GurantordetailDao extends JpaRepository<Gurantordetail,Integer> {


    @Query(value="SELECT new Gurantordetail(g.id,g.name) FROM Gurantordetail g")
    List<Gurantordetail> list();

    @Query(value="SELECT new Gurantordetail(g.id,g.name) FROM Gurantordetail g WHERE g not in " +
            "(Select l.gurantordetailId from Loan l)")
    List<Gurantordetail> listWithoutLoans();
    
    @Query("SELECT g FROM Gurantordetail g ORDER BY g.id DESC")
    Page<Gurantordetail> findAll(Pageable of);

    @Query("SELECT SUBSTRING(MAX(g.code),2,5) FROM Gurantordetail g")
    String lastGurantorno();

    @Query("SELECT g FROM Gurantordetail g WHERE g.nic= :nic")
    Gurantordetail findByNIC(@Param("nic")String nic);
    
    @Query("SELECT g FROM Gurantordetail g WHERE g.code= :code")
    Gurantordetail findByCode(@Param("code")String code);

    @Query("SELECT g FROM Gurantordetail g WHERE g.name= :name")
    Gurantordetail findByName(@Param("name")String name);
}
