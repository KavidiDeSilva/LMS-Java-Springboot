package com.nhdasystem.dao;

import com.nhdasystem.entity.District;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface DistrictDao extends JpaRepository<District, Integer> {

    @Query(value="SELECT new District (d.id,d.name) FROM District d")
    List<District> list();

    @Query(value="SELECT new District (d.id,d.name) FROM District d where d.provinceId.id=:provinceId")
    List<District> listByProvince(@Param("provinceId") Integer provinceId);

    @Query("SELECT d FROM District d ORDER BY d.id DESC")
    Page<District> findAll(Pageable of);

    @Query("SELECT d FROM District d WHERE d.name= :name")
    District findByName(@Param("name")String name);

    @Query("SELECT d FROM District d WHERE d.code= :code")
    District findByCode(@Param("code")String code);


}
