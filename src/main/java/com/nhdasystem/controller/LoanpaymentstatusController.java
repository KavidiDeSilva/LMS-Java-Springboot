package com.nhdasystem.controller;



import com.nhdasystem.dao.LoanpaymentDao;
import com.nhdasystem.dao.LoanpaymentstatusDao;
import com.nhdasystem.entity.Loanpaymentstatus;
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
public class LoanpaymentstatusController {
    @Autowired
    private LoanpaymentstatusDao dao;

    @RequestMapping(value = "/loanpaymentstatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Loanpaymentstatus> loanpaymentstatus() {
        return dao.list();
    }

    @RequestMapping(value = "/loanpaymentstatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Loanpaymentstatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENTSTATUS,AuthProvider.SELECT)) {

            List<Loanpaymentstatus> loanpaymentstatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Loanpaymentstatus> loanpaystatusstream = loanpaymentstatuses.stream();

            loanpaystatusstream = loanpaystatusstream.filter(l -> l.getName().startsWith(name));

            List<Loanpaymentstatus> loanpaystatusstream2 = loanpaystatusstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < loanpaystatusstream2.size() ? start + size : loanpaystatusstream2.size();
            Page<Loanpaymentstatus> loanpaystapage = new PageImpl<>(loanpaystatusstream2.subList(start, end), PageRequest.of(page, size), loanpaystatusstream2.size());

            return loanpaystapage;
        }

        return null;

    }


    @RequestMapping(value = "/loanpaymentstatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loanpaymentstatus loanpaymentstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANPAYMENTSTATUS,AuthProvider.INSERT)) {
            Loanpaymentstatus loanpaystname = dao.findByName(loanpaymentstatus.getName());
            if (loanpaystname != null)
                return "Error-Validation : Loanpaymentstatus Exists";
            else
                try {
                    dao.save(loanpaymentstatus);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/loanpaymentstatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loanpaymentstatus loanpaymentstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANPAYMENTSTATUS,AuthProvider.INSERT)) {
            Loanpaymentstatus loanpaystname = dao.findByName(loanpaymentstatus.getName());
            if (loanpaystname != null)
                return "Error-Validation : Loanpaymentstatus Exists";
            else
                try {
                    dao.save(loanpaymentstatus);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/loanpaymentstatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Loanpaymentstatus loanpaymentstatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANPAYMENTSTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(loanpaymentstatus.getId()));
                return "0";
            }
            catch(Exception l) {
                return "Error-Deleting : "+l.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

    
}
