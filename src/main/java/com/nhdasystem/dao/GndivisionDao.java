package com.nhdasystem.dao;


import com.nhdasystem.entity.Gndivision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GndivisionDao extends JpaRepository<Gndivision,Integer> {

    @Query(value="SELECT new Gndivision (g.id,g.name) FROM Gndivision g")
    List<Gndivision> list();
    
    @Query(value="SELECT new Gndivision (g.id,g.name) FROM Gndivision g where g.dsdivisionId.id=:dsdivisionId")
    List<Gndivision> listByDsdivision(@Param("dsdivisionId") Integer dsdivisionId);


    @Query("SELECT g FROM Gndivision g ORDER BY g.id DESC")
    Page<Gndivision> findAll(Pageable of);

    @Query("SELECT g FROM Gndivision g WHERE g.name= :name")
    Gndivision findByName(@Param("name")String name);
    
    @Query("SELECT g FROM Gndivision g WHERE g.code= :code")
    Gndivision findByCode(@Param("code")String code);

    @Query("SELECT g FROM Gndivision g WHERE g.number= :number")
    Gndivision findByNumber(@Param("number")String number);

//    @Query("SELECT g FROM Gndivision g WHERE g.number= :number")
//    Gndivision findByNumber(@Param("number")String number);

}
