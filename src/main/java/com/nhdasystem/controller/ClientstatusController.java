package com.nhdasystem.controller;

import com.nhdasystem.dao.ClientstatusDao;
import com.nhdasystem.entity.Cstatus;
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
public class ClientstatusController {
    @Autowired
    private ClientstatusDao dao;

    @RequestMapping(value = "/clientstatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Cstatus> clientstatuses() {
        return dao.list();
    }


    @RequestMapping(value = "/clientstatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Cstatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value=
            "password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

       // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.CLIENTSTATUS,AuthProvider.SELECT)) {

            List<Cstatus> clientstatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Cstatus> clistatusstream = clientstatuses.stream();

            clistatusstream = clistatusstream.filter(e -> e.getName().startsWith(name));

            List<Cstatus> clistatusstream2 = clistatusstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < clistatusstream2.size() ? start + size : clistatusstream2.size();
            Page<Cstatus> clistatuspage = new PageImpl<>(clistatusstream2.subList(start, end),
                    PageRequest.of(page, size), clistatusstream2.size());

            return clistatuspage;
        }

        return null;

    }


    @RequestMapping(value = "/clientstatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Cstatus clientstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CLIENTSTATUS,AuthProvider.INSERT)) {
            Cstatus cliname = dao.findByName(clientstatus.getName());
            if (cliname != null)
                return "Error-Validation : Client status Exists";
            else
                try {
                    dao.save(clientstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/clientstatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Cstatus clientstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CLIENTSTATUS,AuthProvider.INSERT)) {
            Cstatus clistatus = dao.findByName(clientstatus.getName());
            if (clistatus != null)
                return "Error-Validation : Client status Exists";
            else
                try {
                    dao.save(clientstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/clientstatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Cstatus clientstatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.CLIENTSTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(clientstatus.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}
