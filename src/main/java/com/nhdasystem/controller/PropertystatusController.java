package com.nhdasystem.controller;

import com.nhdasystem.dao.ClientstatusDao;
import com.nhdasystem.dao.PropertystatusDao;
import com.nhdasystem.entity.Propertystatus;
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
public class PropertystatusController {
    @Autowired
    private PropertystatusDao dao;

    @RequestMapping(value = "/propertystatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Propertystatus> propertystatuses() {
        return dao.list();
    }


    @RequestMapping(value = "/propertystatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Propertystatus> findAll(@CookieValue(value="username", required=false) String username,
                                        @CookieValue(value= "password", required=false) String password, @RequestParam("page") int page,
                                        @RequestParam("size") int size,@RequestParam("name") String name) {

        //System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.PROPERTYSTATUS,AuthProvider.SELECT)) {

            List<Propertystatus> propertystatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Propertystatus> prostatusstream = propertystatuses.stream();

            prostatusstream = prostatusstream.filter(p -> p.getName().startsWith(name));

            List<Propertystatus> prostatusstream2 = prostatusstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < prostatusstream2.size() ? start + size : prostatusstream2.size();
            Page<Propertystatus> prostatuspage = new PageImpl<>(prostatusstream2.subList(start, end),
                    PageRequest.of(page, size), prostatusstream2.size());

            return prostatuspage;
        }

        return null;

    }


    @RequestMapping(value = "/propertystatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Propertystatus propertystatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.PROPERTYSTATUS,AuthProvider.INSERT)) {
            Propertystatus prostatusname = dao.findByName(propertystatus.getName());
            if (prostatusname != null)
                return "Error-Validation : Property status Exists";
            else
                try {
                    dao.save(propertystatus);
                    return "0";
                } catch (Exception p) {
                    return "Error-Saving : " + p.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/propertystatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Propertystatus propertystatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.PROPERTYSTATUS,AuthProvider.INSERT)) {
            Propertystatus prostatusnm = dao.findByName(propertystatus.getName());
            if (prostatusnm != null)
                return "Error-Validation : Property status Exists";
            else
                try {
                    dao.save(propertystatus);
                    return "0";
                } catch (Exception p) {
                    return "Error-Saving : " + p.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/propertystatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Propertystatus propertystatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.PROPERTYSTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(propertystatus.getId()));
                return "0";
            }
            catch(Exception p) {
                return "Error-Deleting : "+p.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}
