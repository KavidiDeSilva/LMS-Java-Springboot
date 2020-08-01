package com.nhdasystem.dao;


import com.nhdasystem.entity.Designation;
import com.nhdasystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RoleDao extends JpaRepository<Role, Integer>
{

    @Query(value="SELECT new Role(r.id,r.name) FROM Role r")
    List<Role> list();

    @Query("SELECT r FROM Role r WHERE r.name= :name")
    Role findByName(@Param("name")String name);

}