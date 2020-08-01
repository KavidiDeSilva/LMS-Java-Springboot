package com.nhdasystem.controller;

import com.nhdasystem.dao.CriteriaDao;
import com.nhdasystem.entity.Criteria;
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
public class CriteriaController {

    @Autowired
    private CriteriaDao dao;


    @RequestMapping(value = "/criterias/list", method = RequestMethod.GET, produces = "application/json")
    public List<Criteria> criterias() {
        return dao.list();
    }


    @RequestMapping(value = "/criterias/listbyloantype",params ="loantypeId" ,method = RequestMethod.GET, produces = "application/json")
    public List<Criteria> criteriasbyloantype(@RequestParam("loantypeId")Integer loantypeId) {
        return dao.listbyloantype(loantypeId);
    }




    @RequestMapping(value = "/criterias", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Criteria> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value=
            "password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.CRITERIA,AuthProvider.SELECT)) {

            List<Criteria> criterias = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Criteria> criteriastream = criterias.stream();

            criteriastream = criteriastream.filter(c -> c.getName().startsWith(name));

            List<Criteria> criteriastream2 = criteriastream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < criteriastream2.size() ? start + size : criteriastream2.size();
            Page<Criteria> criteriapage = new PageImpl<>(criteriastream2.subList(start, end),
                    PageRequest.of(page, size), criteriastream2.size());

            return criteriapage;
        }

        return null;

    }


    @RequestMapping(value = "/criterias", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Criteria criteria) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CRITERIA,AuthProvider.INSERT)) {
            Criteria civilname = dao.findByName(criteria.getName());
            if (civilname != null)
                return "Error-Validation : Criteria Exists";
            else
                try {
                    dao.save(criteria);
                    return "0";
                } catch (Exception c) {
                    return "Error-Saving : " + c.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/criterias", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Criteria criteria) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CRITERIA,AuthProvider.INSERT)) {
            Criteria critname = dao.findByName(criteria.getName());
            if (critname != null)
                return "Error-Validation : Criteria Exists";
            else
                try {
                    dao.save(criteria);
                    return "0";
                } catch (Exception c) {
                    return "Error-Saving : " + c.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/criterias", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Criteria criteria ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.CRITERIA,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(criteria.getId()));
                return "0";
            }
            catch(Exception c) {
                return "Error-Deleting : "+c.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }










}
