package com.nhdasystem.dao;


import com.nhdasystem.entity.Employee;
import com.nhdasystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer>
{
    @Query("SELECT u FROM User u WHERE u.username= :username")
    User findByUsername(@Param("username")String username);


    @Query("SELECT u FROM User u WHERE u.employeeId= :employee")
    User findByEmployee(@Param("employee")Employee employee);

    @Query(value="SELECT new User(u.id,u.username) FROM User u")
    List<User> list();


    @Modifying
    @Transactional
    @Query("delete from User i where id = ?1")
    void deleteByQuery(Integer id);




}