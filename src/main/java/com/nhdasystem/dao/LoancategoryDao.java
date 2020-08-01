package com.nhdasystem.dao;


import com.nhdasystem.entity.Loancategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoancategoryDao extends JpaRepository<Loancategory, Integer>
{

    @Query(value="SELECT new Loancategory (l.id,l.code,l.name) FROM Loancategory l")
    List<Loancategory> list();

    @Query("SELECT l FROM Loancategory l ORDER BY l.id DESC")
    Page<Loancategory> findAll(Pageable of);

    @Query("SELECT SUBSTRING(MAX(l.code),2,5) FROM Loancategory l")
    String lastLoanCategorycode();

    @Query("SELECT l FROM Loancategory l WHERE l.name= :name")
    Loancategory findByName(@Param("name")String name);

    @Query("SELECT l FROM Loancategory l WHERE l.code= :code")
    Loancategory findByCode(@Param("code")String code);
}