package com.nhdasystem.dao;


import com.nhdasystem.entity.Employeestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeestatusDao extends JpaRepository<Employeestatus, Integer>
{

    @Query(value="SELECT new Employeestatus(e.id,e.name) FROM Employeestatus e")
    List<Employeestatus> list();

    @Query("SELECT e FROM Employeestatus e WHERE e.name= :name")
    Employeestatus findByName(@Param("name")String name);
}