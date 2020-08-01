package com.nhdasystem.dao;


import com.nhdasystem.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;



public interface CriteriaDao extends JpaRepository<Criteria, Integer> {

    @Query(value = "SELECT new Criteria (c.id,c.name,c.value,c.constraintId) FROM Criteria c")
    List<Criteria> list();


    @Query(value = "SELECT new Criteria (c.id,c.name,c.value,c.constraintId) FROM Criteria c where c.loantypeId.id=:loantypeId")
    List<Criteria> listbyloantype(@Param("loantypeId") Integer loantypeId);


    @Query("SELECT c FROM Criteria c WHERE c.name= :name")
    Criteria findByName(@Param("name")String name);

//    @Query("SELECT c FROM Criteria c WHERE c.id= :id")
//    Criteria findById(@Param("id")Integer id);

  
}
