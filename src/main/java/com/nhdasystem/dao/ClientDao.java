package com.nhdasystem.dao;

import com.nhdasystem.entity.Client;
//import com.nhdasystem.entity.Client;
//import com.nhdasystem.entity.Client;
//import com.nhdasystem.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Integer> {

    @Query(value="SELECT new Client (c.id,c.code,c.name,c.fullname,c.gndivisionId,c.cstatusId) FROM Client c")
    List<Client> list();

    @Query(value="SELECT new Client(c.id,c.code,c.name,c.fullname,c.gndivisionId,c.cstatusId) FROM Client c WHERE c not in " +
            "(Select l.clientId from Loan l)")
    List<Client> listWithoutLoans();

    @Query("SELECT c FROM Client c ORDER BY c.id DESC")
    Page<Client> findAll(Pageable of);

    @Query("SELECT c FROM Client c WHERE c.nic= :nic")
    Client findByNIC(@Param("nic")String nic);

    @Query("SELECT c FROM Client c WHERE c.code= :code")
    Client findByCode(@Param("code")String code);

//    @Query(value = "SELECT concat('C',lpad(substring(max(c.code),2,5)+1,5,'0')) FROM Client c",nativeQuery =true )
//    //SELECT substring(max(c.code),2,5) FROM nhda.client as c;
//    String lastClientno();
//
//    @Query(value = "SELECT substring(max(c.code),2,5)+1 FROM Client c")
//        //SELECT substring(max(c.code),2,5) FROM nhda.client as c;
//    String lastClientno();

    @Query("SELECT SUBSTRING(MAX(c.code),2,5) FROM Client c")
    String lastClientno();




}
