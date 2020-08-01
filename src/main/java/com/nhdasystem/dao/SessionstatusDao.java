package com.nhdasystem.dao;


import com.nhdasystem.entity.Sessionstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SessionstatusDao extends JpaRepository<Sessionstatus, Long>
{
    @Query("SELECT s FROM Sessionstatus s WHERE s.name= :name")
    Sessionstatus findByName(@Param("name")String name);

}

