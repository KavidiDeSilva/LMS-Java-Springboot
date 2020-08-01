package com.nhdasystem.dao;


import com.nhdasystem.entity.Loantypestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoantypestatusDao extends JpaRepository<Loantypestatus, Integer>
{

    @Query(value="SELECT new Loantypestatus (l.id,l.name) FROM Loantypestatus l")
    List<Loantypestatus> list();

    @Query("SELECT l FROM Loantypestatus l WHERE l.name= :name")
    Loantypestatus findByName(@Param("name")String name);

}