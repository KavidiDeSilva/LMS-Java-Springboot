package com.nhdasystem.controller;


import com.nhdasystem.dao.LoancategoryDao;
import com.nhdasystem.entity.Loancategory;
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
public class LoancategoryController {
    @Autowired
    private LoancategoryDao dao;

    @RequestMapping(value = "/loancategories/list", method = RequestMethod.GET, produces = "application/json")
    public List<Loancategory> loancategory() {
        return dao.list();
    }

//
//    @RequestMapping(value = "/loancategories", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
//    public Page<Loancategory> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String
//            password, @RequestParam("page") int page, @RequestParam("size") int size) {
//        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANCATEGORY,AuthProvider.SELECT)) {
//            return dao.findAll(PageRequest.of(page, size));
//        }
//        return null;
//    }


    @RequestMapping(value = "/loancategories", params = {"page", "size","name","code"}, method = RequestMethod.GET,
            produces = "application/json")
    public Page<Loancategory> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,
             @RequestParam("page") int page, @RequestParam("size") int size
            ,@RequestParam("name") String name, @RequestParam("code") String code) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANCATEGORY,AuthProvider.SELECT)) {

            List<Loancategory> loancategories = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Loancategory> loancatstream = loancategories.stream();

            loancatstream = loancatstream.filter(l -> l.getCode().startsWith(code));
            loancatstream = loancatstream.filter(l -> l.getName().startsWith(name));

            List<Loancategory> loancatstream2 = loancatstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < loancatstream2.size() ? start + size : loancatstream2.size();
            Page<Loancategory> loancatpage = new PageImpl<>(loancatstream2.subList(start, end), PageRequest.of(page, size), loancatstream2.size());

            return loancatpage;
        }

        return null;

    }


    @RequestMapping(value = "/loancategories", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loancategory loancategory) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANCATEGORY,AuthProvider.INSERT)) {
            Loancategory loancatname = dao.findByName(loancategory.getName());
            if (loancatname != null)
                return "Error-Validation : Loancategory Exists";
            else
                try {
                    dao.save(loancategory);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/loancategories", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Loancategory loancategory) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANCATEGORY,AuthProvider.INSERT)) {
            Loancategory loancatname = dao.findByName(loancategory.getName());
            if (loancatname != null)
                return "Error-Validation : Loancategory Exists";
            else
                try {
                    dao.save(loancategory);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/loancategories", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Loancategory loancategory ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANCATEGORY,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(loancategory.getId()));
                return "0";
            }
            catch(Exception l) {
                return "Error-Deleting : "+l.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
    @RequestMapping(value = "/loancategories/lccode", method = RequestMethod.GET, produces = "application/json")
    public String lastLoanCategorycode(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANCATEGORY,AuthProvider.SELECT)) {
            String lccode = dao.lastLoanCategorycode();
            //System.out.println(clicode);
            Integer lcCode = Integer.parseInt(lccode);
            String lcategorycode="";
            if(lcCode<9)
                lcategorycode = "0000"+(lcCode+1);
            else if(lcCode<99)
                lcategorycode = "000"+(lcCode+1);
            else if(lcCode<999)
                lcategorycode = "00"+(lcCode+1);
            else if(lcCode<9999)
                lcategorycode = "0"+ (lcCode+1);

            return "{\"no\":"+"\""+lcategorycode+"\"}";
        }
        return null;
    }

}
