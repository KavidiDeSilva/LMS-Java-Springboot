package com.nhdasystem.controller;


import com.nhdasystem.dao.ConstraintDao;
import com.nhdasystem.entity.Constraint1;
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
public class ConstraintController {
    @Autowired
    private ConstraintDao dao;

    @RequestMapping(value = "/constraints/list", method = RequestMethod.GET, produces = "application/json")
    public List<Constraint1> constraints() {
        return dao.list();
    }

    @RequestMapping(value = "/constraints", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Constraint1> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value=
            "password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.CONSTRAINT,AuthProvider.SELECT)) {

            List<Constraint1> constraints = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Constraint1> constraintstream = constraints.stream();

            constraintstream = constraintstream.filter(c -> c.getName().startsWith(name));

            List<Constraint1> constraintstream2 = constraintstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < constraintstream2.size() ? start + size : constraintstream2.size();
            Page<Constraint1> constraintpage = new PageImpl<>(constraintstream2.subList(start, end),
                    PageRequest.of(page, size), constraintstream2.size());

            return constraintpage;
        }

        return null;

    }


    @RequestMapping(value = "/constraints", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Constraint1 constraint) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CONSTRAINT,AuthProvider.INSERT)) {
            Constraint1 constrname = dao.findByName(constraint.getName());
            if (constrname != null)
                return "Error-Validation : Constraint1 Exists";
            else
                try {
                    dao.save(constraint);
                    return "0";
                } catch (Exception c) {
                    return "Error-Saving : " + c.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/constraints", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Constraint1 constraint) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CONSTRAINT,AuthProvider.INSERT)) {
            Constraint1 constrname = dao.findByName(constraint.getName());
            if (constrname != null)
                return "Error-Validation : Constraint1 Exists";
            else
                try {
                    dao.save(constraint);
                    return "0";
                } catch (Exception c) {
                    return "Error-Saving : " + c.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/constraints", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Constraint1 constraint ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.CONSTRAINT,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(constraint.getId()));
                return "0";
            }
            catch(Exception c) {
                return "Error-Deleting : "+c.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
   
}
