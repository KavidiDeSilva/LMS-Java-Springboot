package com.nhdasystem.controller;

import com.nhdasystem.dao.RoleDao;
import com.nhdasystem.dao.UserDao;
import com.nhdasystem.dao.UserstatusDao;
import com.nhdasystem.entity.User;
import com.nhdasystem.entity.Userrole;
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
public class UserController {


    @Autowired
    private UserDao dao;

    @Autowired
    private RoleDao daoRole;


    @Autowired
    private UserstatusDao daoUserstatus;


    @RequestMapping(value = "/users/list", method = RequestMethod.GET, produces = "application/json")
    public List<User> user() {
        return dao.list();
    }


    @RequestMapping(value = "/users", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<User> getAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("page") int page, @RequestParam("size") int size ) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.USER,AuthProvider.SELECT)) {
            //  System.out.println(dao.findAll(PageRequest.of(page, size)).getContent().get(0).getUserroleList().get(0).getRoleId().getName());

            //if(AuthProvider.isAuthorized(username,password,"user","sel"))
            //    return dao.findAll(PageRequest.of(page, size));
            //else
            return dao.findAll(PageRequest.of(page, size));
        }
        else
        return null;
    }


    @RequestMapping(value = "/users", params = {"page", "size","username","roleid","userstatusid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<User> findAll(@CookieValue(value="username", required=false) String uname, @CookieValue(value="password", required=false) String pword,@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("username") String username, @RequestParam("roleid") Integer roleid, @RequestParam("userstatusid") Integer userstatusid) {

        //System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);

        if(AuthProvider.isAuthorized(uname,pword,ModuleList.USER,AuthProvider.SELECT)) {

            List<User> users = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<User> userstream = users.stream();
            userstream = userstream.filter(u -> !u.getUsername().equals("admin"));


            if (roleid != null) {
                userstream = userstream.filter(u -> {
                    for (Userrole ur : u.getUserroleList())
                        if (ur.getRoleId().getId().equals(roleid))
                            return true;
                    return false;
                });
            }

            if (userstatusid != null)
                userstream = userstream.filter(u -> u.getUserstatusId().equals(daoUserstatus.getOne(userstatusid)));

            userstream = userstream.filter(u -> u.getUsername().startsWith(username));

            List<User> users2 = userstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < users2.size() ? start + size : users2.size();
            Page<User> userpage = new PageImpl<>(users2.subList(start, end), PageRequest.of(page, size), users2.size());


            return userpage;
        }
        else
        return  null;

    }


   // @RequestMapping(value = "/user", method = RequestMethod.POST)
   // public String add(@Validated @RequestBody User user, @CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password ) {

        //if(AuthProvider.isAuthorized(username,password,"employee","sel")){

     //   System.out.println(user.getName()+"\n"+user.getPassword()+"\n"+user.getDescription()+"\n"+user.getEmployeeId().getCallingname()+"\n"+user.getUserroleList());

     //   for(Userrole u : user.getUserroleList())
    //          u.setUserId(user);

    //    dao.save(user);
  //      return null; //String.valueOf(user.getId());
        //}else  return "-101";

   // }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody User user) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.USER,AuthProvider.INSERT)) {
        User userbyusername = dao.findByUsername(user.getUsername());
        User userbyemployee = dao.findByEmployee(user.getEmployeeId());

        if(userbyusername!=null)
            return "Error-Validation : Username Exists";
        else
        if(userbyemployee!=null)
            return "Error-Validation : This Employee is a User already";
        else
            try {
                for(Userrole u : user.getUserroleList())
                    u.setUserId(user);
                user.setSalt(AuthProvider.generateSalt());
                user.setPassword(AuthProvider.getHash(user.getPassword()+user.getSalt()));

                dao.save(user);
                return "0";
            }
            catch(Exception e) {
                return "Error-Saving : "+ e.getMessage();
            }
        }
                return "Error-Inserting : You have no Permission";

    }



    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody User user) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.USER,AuthProvider.UPDATE)) {
        try {

            User userfrompersistent = dao.findByUsername(user.getUsername());
            userfrompersistent.setDescription(user.getDescription());
            userfrompersistent.setUserstatusId(user.getUserstatusId());
            userfrompersistent.getUserroleList().clear();
            for (Userrole ur:user.getUserroleList())
            { userfrompersistent.getUserroleList().add(ur);
                ur.setUserId(userfrompersistent);
            }

            dao.save(userfrompersistent);
                return "0";
            }
            catch(Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }
        else
                    return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/resetpassword", method = RequestMethod.PUT)
    public String resetPassword(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody User user) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.USER,AuthProvider.UPDATE)) {
            try {

                User userfrompersistent = dao.findByUsername(user.getUsername());
                userfrompersistent.setPassword(AuthProvider.getHash(user.getPassword()+userfrompersistent.getSalt()));
                dao.save(userfrompersistent);
                return "0";
            }
            catch(Exception e) {
                return "Error-Saving : " + e.getMessage();
            }
        }
        else
            return "Error-Updating : You have no Permission";
    }



    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody User user ) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.USER,AuthProvider.DELETE)) {
            try {
                User us = dao.getOne(user.getId());
                  us.getUserroleList().clear();
                    dao.save(us);
                    dao.deleteByQuery(us.getId());
                    return "0";

            } catch (Exception e) {
                return "Error-Deleting : " + e.getMessage();
            }
        }

        else
             return "Error-Deleting : You have no Permission";

    }




    @RequestMapping(value = "/changepassword",params = {"username","exsistingpassword","newpassword"}, method = RequestMethod.POST, produces = "application/json")
    public String config( @RequestParam("username") String username, @RequestParam("exsistingpassword") String exsistingpassword, @RequestParam("newpassword") String newpassword) {

        User user =null;

        if(AuthProvider.isUser(username,exsistingpassword))
        user = dao.findByUsername(username);

        if(user==null)
            return "0";
        else {

            user.setSalt(AuthProvider.generateSalt());
            user.setPassword(newpassword);
            user.setPassword(AuthProvider.getHash(user.getPassword()+user.getSalt()));


           try {

               dao.save(user);
                return "1";
            }

            catch (Exception ex){

                return "Failed to change as "+ex.getMessage();}

        }


    }




}