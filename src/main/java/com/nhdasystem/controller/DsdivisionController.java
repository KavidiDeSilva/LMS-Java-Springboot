package com.nhdasystem.controller;

import com.nhdasystem.dao.DsdivisionDao;
import com.nhdasystem.entity.Dsdivision;
import com.nhdasystem.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DsdivisionController {

    @Autowired
    private DsdivisionDao dao;

    @RequestMapping(value = "/dsdivisions/list", method = RequestMethod.GET, produces = "application/json")
    public List<Dsdivision> gender() {
        return dao.list();
    }

    @RequestMapping(value = "/dsdivisions/listbydistrict", params = "districtId", method = RequestMethod.GET, produces = "application/json")
    public List<Dsdivision> districts(@Param("districtId") Integer districtId) {
        return dao.listByDistrict(districtId);
    }

    @RequestMapping(value = "/dsdivisions", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Dsdivision> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.DSDIVISION,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/dsdivisions", params = {"page", "size","name","code"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Dsdivision> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value= "password", required=false) String password,
                                    @RequestParam("page") int page, @RequestParam("size") int size,
                                    @RequestParam("name") String name, @RequestParam("code") String code) {

       // System.out.println(name);
        if(AuthProvider.isAuthorized(username,password, ModuleList.DSDIVISION,AuthProvider.SELECT)) {

            List<Dsdivision> dsdivisions = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Dsdivision> dsdivisionstream = dsdivisions.stream();

            dsdivisionstream = dsdivisionstream.filter(d -> d.getName().startsWith(name));
            dsdivisionstream = dsdivisionstream.filter(d -> d.getCode().startsWith(code));

            List<Dsdivision> dsdivisionstream2 = dsdivisionstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < dsdivisionstream2.size() ? start + size : dsdivisionstream2.size();
            Page<Dsdivision> dsdivisionpage = new PageImpl<>(dsdivisionstream2.subList(start, end),
                    PageRequest.of(page, size), dsdivisionstream2.size());

            return dsdivisionpage;
        }

        return null;

    }


    @RequestMapping(value = "/dsdivisions", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Dsdivision dsdivision) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.DSDIVISION,AuthProvider.INSERT)) {
            Dsdivision dsdname = dao.findByName(dsdivision.getName());

            if (dsdname != null)
                return "Error-Validation : DS Division Name Exists";
            else
                try {
                    dao.save(dsdivision);
                    return "0";
                } catch (Exception d) {
                    return "Error-Saving : " + d.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/dsdivisions", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Dsdivision dsdivision) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.DSDIVISION,AuthProvider.UPDATE)) {
            Dsdivision dsd = dao.findByName(dsdivision.getName());
            if(dsd==null || dsd.getId()==dsdivision.getId()) {
                try {
                    dao.save(dsdivision);
                    return "0";
                }
                catch(Exception d) {
                    return "Error-Updating : "+d.getMessage();
                }
            }
            else {  return "Error-Updating : DS Division Name Exists"; }
        }
        return "Error-Updating : You have no Permission";

    }


    @RequestMapping(value = "/dsdivisions", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Dsdivision dsdivision ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.DSDIVISION,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(dsdivision.getId()));
                return "0";
            }
            catch(Exception d) {
                return "Error-Deleting : "+d.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

}
