package com.nhdasystem.controller;


import com.nhdasystem.dao.LoantypestatusDao;
import com.nhdasystem.entity.Loantypestatus;
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
public class LoantypestatusController {
    @Autowired
    private LoantypestatusDao dao;

    @RequestMapping(value = "/loantypestatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Loantypestatus> loantypestatus() {
        return dao.list();
    }


    @RequestMapping(value = "/loantypestatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Loantypestatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANTYPESTATUS,AuthProvider.SELECT)) {

            List<Loantypestatus> loantypestatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Loantypestatus> loantypestatusstream = loantypestatuses.stream();

            loantypestatusstream = loantypestatusstream.filter(l -> l.getName().startsWith(name));

            List<Loantypestatus> loantypestatusstream2 = loantypestatusstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < loantypestatusstream2.size() ? start + size : loantypestatusstream2.size();
            Page<Loantypestatus> lstatuspage = new PageImpl<>(loantypestatusstream2.subList(start, end), PageRequest.of(page, size), loantypestatusstream2.size());

            return lstatuspage;
        }

        return null;

    }


    @RequestMapping(value = "/loantypestatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loantypestatus loantypestatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANTYPESTATUS,AuthProvider.INSERT)) {
            Loantypestatus lnstatusname = dao.findByName(loantypestatus.getName());
            if (lnstatusname != null)
                return "Error-Validation : Loantypestatus Exists";
            else
                try {
                    dao.save(loantypestatus);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/loantypestatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loantypestatus loantypestatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANTYPESTATUS,AuthProvider.INSERT)) {
            Loantypestatus lnstatusname = dao.findByName(loantypestatus.getName());
            if (lnstatusname != null)
                return "Error-Validation : Loantypestatus Exists";
            else
                try {
                    dao.save(loantypestatus);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/loantypestatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Loantypestatus loantypestatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANTYPESTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(loantypestatus.getId()));
                return "0";
            }
            catch(Exception l) {
                return "Error-Deleting : "+l.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
    
}
