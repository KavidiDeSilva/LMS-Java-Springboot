package com.nhdasystem.dao;

import com.nhdasystem.entity.Workingstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WorkingstatusDao extends JpaRepository<Workingstatus, Integer>
{
        @Query(value="SELECT new Workingstatus (w.id,w.name) FROM Workingstatus w")
        List<Workingstatus> list();

        @Query("SELECT w FROM Workingstatus w WHERE w.name= :name")
        Workingstatus findByName(@Param("name")String name);


}

