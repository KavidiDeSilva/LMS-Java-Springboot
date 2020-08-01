package com.nhdasystem.dao;


import com.nhdasystem.entity.Dsdivision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DsdivisionDao extends JpaRepository<Dsdivision, Integer>
{

    @Query(value="SELECT new Dsdivision(d.id,d.name) FROM Dsdivision d")
    List<Dsdivision> list();

    @Query(value="SELECT new Dsdivision (d.id,d.name) FROM Dsdivision d where d.districtId.id=:districtId")
    List<Dsdivision> listByDistrict(@Param("districtId") Integer districtId);

    @Query("SELECT d FROM Dsdivision d ORDER BY d.id DESC")
    Page<Dsdivision> findAll(Pageable of);

    @Query("SELECT d FROM Dsdivision d WHERE d.name= :name")
    Dsdivision findByName(@Param("name")String name);
    
    @Query("SELECT d FROM Dsdivision d WHERE d.code= :code")
    Dsdivision findByCode(@Param("code")String code);


}