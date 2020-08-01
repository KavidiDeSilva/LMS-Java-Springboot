package com.nhdasystem.dao;

import com.nhdasystem.entity.Gender;
import com.nhdasystem.entity.Purposeofloan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurposeofloanDao extends JpaRepository<Purposeofloan,Integer> {

    @Query(value="SELECT new Purposeofloan(p.id,p.name) FROM Purposeofloan p")
    List<Purposeofloan> list();

//    @Query("SELECT p FROM Purposeofloan p WHERE p.name= :name")
//    Purposeofloan findByName(@Param("name")String name);
}
