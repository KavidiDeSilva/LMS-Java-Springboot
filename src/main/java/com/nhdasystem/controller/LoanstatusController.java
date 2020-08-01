package com.nhdasystem.controller;

import com.nhdasystem.dao.ClientstatusDao;
import com.nhdasystem.dao.LoanstatusDao;
import com.nhdasystem.entity.Loanstatus;
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
public class LoanstatusController {
    @Autowired
    private LoanstatusDao dao;

    @RequestMapping(value = "/loanstatuses/list", method = RequestMethod.GET, produces =
            "application/json")
    public List<Loanstatus> loanstatus() {
        return dao.list();
    }



    @RequestMapping(value = "/loanstatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Loanstatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestParam("name") String name,@RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANSTATUS,AuthProvider.SELECT)) {

            List<Loanstatus> loanstatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Loanstatus> loanstatustream = loanstatuses.stream();

            loanstatustream = loanstatustream.filter(l -> l.getName().startsWith(name));

            List<Loanstatus> loanstatustream2 = loanstatustream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < loanstatustream2.size() ? start + size : loanstatustream2.size();
            Page<Loanstatus> lstatuspage = new PageImpl<>(loanstatustream2.subList(start, end), PageRequest.of(page, size), loanstatustream2.size());

            return lstatuspage;
        }

        return null;

    }


    @RequestMapping(value = "/loanstatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loanstatus loanstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANSTATUS,AuthProvider.INSERT)) {
            Loanstatus lnstatusname = dao.findByName(loanstatus.getName());
            if (lnstatusname != null)
                return "Error-Validation : Loanstatus Exists";
            else
                try {
                    dao.save(loanstatus);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/loanstatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loanstatus loanstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANSTATUS,AuthProvider.INSERT)) {
            Loanstatus lnstatusname = dao.findByName(loanstatus.getName());
            if (lnstatusname != null)
                return "Error-Validation : Loanstatus Exists";
            else
                try {
                    dao.save(loanstatus);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/loanstatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Loanstatus loanstatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANSTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(loanstatus.getId()));
                return "0";
            }
            catch(Exception l) {
                return "Error-Deleting : "+l.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}
