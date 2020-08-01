package com.nhdasystem.controller;

import com.nhdasystem.dao.PurposeofloanDao;
import com.nhdasystem.entity.Purposeofloan;
import com.nhdasystem.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PurposeofloanController {

    @Autowired
    private PurposeofloanDao dao;

    @RequestMapping(value = "/purposeofloans/list", method = RequestMethod.GET, produces = "application/json")
    public List<Purposeofloan> purposeofloan() {
        return dao.list();
    }



}
