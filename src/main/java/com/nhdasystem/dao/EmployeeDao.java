package com.nhdasystem.dao;

import com.nhdasystem.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    @Query(value="SELECT new Employee(e.id,e.callingname,e.designationId,e.branchId) FROM Employee e")
    List<Employee> list();

    @Query(value="SELECT new Employee(e.id,e.callingname,e.designationId,e.branchId) FROM Employee e WHERE e not in " +
            "(Select u.employeeId from User u)")
    List<Employee> listWithoutUsers();

    @Query(value="SELECT new Employee(e.id,e.callingname,e.designationId,e.branchId) FROM Employee e WHERE e in (Select u" +
            ".employeeId from " +
            "User u)")
    List<Employee> listWithUseraccount();

    @Query("SELECT e FROM Employee e ORDER BY e.id DESC")
    Page<Employee> findAll(Pageable of);

    @Query("SELECT SUBSTRING(MAX(e.number),2,5) FROM Employee e")
    String lastEmpno();


    @Query("SELECT e FROM Employee e WHERE e.nic= :nic")
    Employee findByNIC(@Param("nic")String nic);

    @Query("SELECT e FROM Employee e WHERE e.number= :number")
    Employee findByNumber(@Param("number")String number);

    /*

    @Query("SELECT e FROM Employee e WHERE e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByEmployeestatus(Pageable of, @Param("employeestatusid")Integer employeestatusid);

    @Query("SELECT e FROM Employee e WHERE e.designationId.id= :designationid")
    Page<Employee> findAllByDesignation(Pageable of, @Param("designationid")Integer designationid);

    @Query("SELECT e FROM Employee e WHERE e.designationId.id= :designationid AND e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByDesignationEmployeestaus(Pageable of, @Param("designationid")Integer designationid, @Param("employeestatusid")Integer employeestatusid);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic")
    Page<Employee> findAllByNameNIC(Pageable of, @Param("name")String name, @Param("nic")String nic);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic AND e.designationId.id= :designationid")
    Page<Employee> findAllByNameNICDesignation(Pageable of, @Param("name")String name, @Param("nic")String nic, @Param("designationid")Integer designationid);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic AND e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByNameNICEmployeestatus(Pageable of, @Param("name")String name, @Param("nic")String nic, @Param("employeestatusid")Integer employeestatusid);

    @Query("SELECT e FROM Employee e WHERE e.fullname like :name AND e.nic like :nic AND e.designationId.id= :designationid AND e.employeestatusId.id= :employeestatusid")
    Page<Employee> findAllByNameNICDesignationEmployeestatus(Pageable of, @Param("name")String name, @Param("nic")String nic, @Param("designationid")Integer designationid, @Param("employeestatusid")Integer employeestatusid);


*/



}

