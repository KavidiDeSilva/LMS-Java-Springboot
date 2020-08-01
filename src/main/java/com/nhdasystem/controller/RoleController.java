package com.nhdasystem.controller;

import com.nhdasystem.dao.RoleDao;
import com.nhdasystem.entity.Role;
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
public class RoleController {

    @Autowired
    private RoleDao dao;

    @RequestMapping(value = "/roles/list", method = RequestMethod.GET, produces = "application/json")
    public List<Role> roles() {
        return dao.list();
    }


    @RequestMapping(value = "/roles", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Role> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.ROLE,AuthProvider.SELECT)) {

            List<Role> roles = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Role> rolestream = roles.stream();

            rolestream = rolestream.filter(e -> e.getName().startsWith(name));

            List<Role> rolestream2 = rolestream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < rolestream2.size() ? start + size : rolestream2.size();
            Page<Role> rolepage = new PageImpl<>(rolestream2.subList(start, end), PageRequest.of(page, size), rolestream2.size());

            return rolepage;
        }

        return null;

    }


    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Role role) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.ROLE,AuthProvider.INSERT)) {
            Role desname = dao.findByName(role.getName());
            if (desname != null)
                return "Error-Validation : Role Exists";
            else
                try {
                    dao.save(role);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Role role) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.ROLE,AuthProvider.INSERT)) {
            Role desname = dao.findByName(role.getName());
            if (desname != null)
                return "Error-Validation : Role Exists";
            else
                try {
                    dao.save(role);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/roles", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Role role ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.ROLE,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(role.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


}
