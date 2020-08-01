package com.nhdasystem.dao;


import com.nhdasystem.entity.Propertystatus;
import com.nhdasystem.entity.Propertystatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertystatusDao extends JpaRepository<Propertystatus, Integer>
{

    @Query(value="SELECT new Propertystatus (p.id,p.name) FROM Propertystatus p")
    List<Propertystatus> list();

    @Query("SELECT p FROM Propertystatus p ORDER BY p.id DESC")
    Page<Propertystatus> findAll(Pageable of);


    @Query("SELECT p FROM Propertystatus p WHERE p.name= :name")
    Propertystatus findByName(@Param("name") String name);

}