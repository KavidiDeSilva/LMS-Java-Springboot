package com.nhdasystem.dao;

import com.nhdasystem.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleDao  extends JpaRepository<Module, Integer> {

    @Query(value="SELECT new Module(m.id,m.name) FROM Module m WHERE m NOT IN (SELECT p.moduleId FROM Privilage p WHERE p.roleId.id= :roleid)")
    List<Module> listUnassignedToThisRole(@Param("roleid")Integer roleid);

//    @Query(value="SELECT new Module(m.id,m.name) FROM Module m WHERE m IN (SELECT p.moduleId FROM Privilage p WHERE p.roleId.id= :roleid)")
//    List<Module> listAssignedToThisRole(@Param("roleid")Integer roleid);

    @Query(value="SELECT new Module(m.id,m.name) FROM Module m")
    List<Module> list();


}
