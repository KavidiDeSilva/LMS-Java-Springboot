package com.nhdasystem.dao;

import com.nhdasystem.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchDao extends JpaRepository<Branch,Integer> {
    
    @Query(value="SELECT new Branch(b.id,b.name,b.dcode) FROM Branch b")
    List<Branch> list();

    @Query(value="SELECT new Branch(b.id,b.name,b.number,b.address,b.districtmanager,b.mobileno,b.landno,b.fax,b.email,b.districtId) FROM Branch b")
    List<Branch> listall();

    @Query("SELECT b FROM Branch b ORDER BY b.id DESC")
    Page<Branch> findAll(Pageable of);

    @Query("SELECT b FROM Branch b WHERE b.name= :name")
    Branch findByName(@Param("name")String name);

    @Query("SELECT b FROM Branch b WHERE b.number= :number")
    Branch findByNumber(@Param("number")String number);
}