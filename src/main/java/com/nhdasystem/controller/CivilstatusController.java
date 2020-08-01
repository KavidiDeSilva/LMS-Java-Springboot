package com.nhdasystem.controller;

import com.nhdasystem.dao.CivilstatusDao;
import com.nhdasystem.entity.Civilstatus;
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
public class CivilstatusController {

    @Autowired
    private CivilstatusDao dao;

    @RequestMapping(value = "/civilstatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Civilstatus> civilstatuses() {
        return dao.list();
    }


    @RequestMapping(value = "/civilstatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Civilstatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value=
            "password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.CIVILSTATUS,AuthProvider.SELECT)) {

            List<Civilstatus> civilstatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Civilstatus> civilstatusstream = civilstatuses.stream();

            civilstatusstream = civilstatusstream.filter(e -> e.getName().startsWith(name));

            List<Civilstatus> civilstatusstream2 = civilstatusstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < civilstatusstream2.size() ? start + size : civilstatusstream2.size();
            Page<Civilstatus> civilstatuspage = new PageImpl<>(civilstatusstream2.subList(start, end),
                    PageRequest.of(page, size), civilstatusstream2.size());

            return civilstatuspage;
        }

        return null;

    }


    @RequestMapping(value = "/civilstatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Civilstatus civilstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CIVILSTATUS,AuthProvider.INSERT)) {
            Civilstatus civilname = dao.findByName(civilstatus.getName());
            if (civilname != null)
                return "Error-Validation : Civilstatus Exists";
            else
                try {
                    dao.save(civilstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/civilstatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Civilstatus civilstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CIVILSTATUS,AuthProvider.INSERT)) {
            Civilstatus civstatus = dao.findByName(civilstatus.getName());
            if (civstatus != null)
                return "Error-Validation : Civilstatus Exists";
            else
                try {
                    dao.save(civilstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/civilstatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Civilstatus civilstatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.CIVILSTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(civilstatus.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}
