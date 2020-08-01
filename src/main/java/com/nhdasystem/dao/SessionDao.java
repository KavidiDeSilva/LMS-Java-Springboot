package com.nhdasystem.dao;


import com.nhdasystem.entity.Sessionlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionDao extends JpaRepository<Sessionlog, Integer>
{

}