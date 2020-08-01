package com.nhdasystem.dao;


import com.nhdasystem.entity.Civilstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CivilstatusDao extends JpaRepository<Civilstatus, Integer>
{

    @Query(value="SELECT new Civilstatus(c.id,c.name) FROM Civilstatus c")
    List<Civilstatus> list();


    @Query("SELECT c FROM Civilstatus c WHERE c.name= :name")
    Civilstatus findByName(@Param("name")String name);


}