package com.nhdasystem.dao;


import com.nhdasystem.entity.Cstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientstatusDao extends JpaRepository<Cstatus, Integer>
{

    @Query(value="SELECT new Cstatus (c.id,c.name) FROM Cstatus c")
    List<Cstatus> list();



    @Query("SELECT c FROM Cstatus c WHERE c.name= :name")
    Cstatus findByName(@Param("name")String name);

}