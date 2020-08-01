package com.nhdasystem.dao;


import com.nhdasystem.entity.Constraint1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ConstraintDao extends JpaRepository<Constraint1, Integer>
{

    @Query(value="SELECT new Constraint1 (c.id,c.name) FROM Constraint1 c")
    List<Constraint1> list();

    @Query("SELECT con FROM Constraint1 con WHERE con.name= :name")
    Constraint1 findByName(@Param("name") String name);

}