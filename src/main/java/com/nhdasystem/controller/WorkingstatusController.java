package com.nhdasystem.controller;

import com.nhdasystem.dao.WorkingstatusDao;
import com.nhdasystem.entity.Workingstatus;
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
public class WorkingstatusController {
    @Autowired
    private WorkingstatusDao dao;

    @RequestMapping(value = "/workingstatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Workingstatus> workingstatuses() {
        return dao.list();
    }


    @RequestMapping(value = "/workingstatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Workingstatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value=
            "password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.WORKINGSTATUS,AuthProvider.SELECT)) {

            List<Workingstatus> workingstatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Workingstatus> workstatusstream = workingstatuses.stream();

            workstatusstream = workstatusstream.filter(e -> e.getName().startsWith(name));

            List<Workingstatus> workstatusstream2 = workstatusstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < workstatusstream2.size() ? start + size : workstatusstream2.size();
            Page<Workingstatus> workstatuspage = new PageImpl<>(workstatusstream2.subList(start, end),
                    PageRequest.of(page, size), workstatusstream2.size());

            return workstatuspage;
        }

        return null;

    }


    @RequestMapping(value = "/workingstatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Workingstatus workingstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.WORKINGSTATUS,AuthProvider.INSERT)) {
            Workingstatus workname = dao.findByName(workingstatus.getName());
            if (workname != null)
                return "Error-Validation : Working status Exists";
            else
                try {
                    dao.save(workingstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/workingstatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Workingstatus workingstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.WORKINGSTATUS,AuthProvider.INSERT)) {
            Workingstatus workstatus = dao.findByName(workingstatus.getName());
            if (workstatus != null)
                return "Error-Validation : Working status Exists";
            else
                try {
                    dao.save(workingstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/workingstatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Workingstatus workingstatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.WORKINGSTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(workingstatus.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

}
