package com.nhdasystem.dao;


import com.nhdasystem.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface ProvinceDao extends JpaRepository<Province, Integer>
{

    @Query(value="SELECT new Province(d.id,d.name) FROM Province d")
    List<Province> list();


    @Query("SELECT p FROM Province p WHERE p.name= :name")
    Province findByName(@Param("name")String name);


}