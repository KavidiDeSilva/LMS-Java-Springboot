package com.nhdasystem.controller;

import com.nhdasystem.dao.ProvinceDao;
import com.nhdasystem.entity.Province;
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
public class ProvinceController {

    @Autowired
    private ProvinceDao dao;

    @RequestMapping(value = "/provinces/list", method = RequestMethod.GET, produces = "application/json")
    public List<Province> province() {
        return dao.list();
    }

    @RequestMapping(value = "/provinces", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Province> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value=
            "password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.PROVINCE,AuthProvider.SELECT)) {

            List<Province> provinces = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Province> provincestream = provinces.stream();

            provincestream = provincestream.filter(p -> p.getName().startsWith(name));

            List<Province> provincestream2 = provincestream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < provincestream2.size() ? start + size : provincestream2.size();
            Page<Province> provpage = new PageImpl<>(provincestream2.subList(start, end),
                    PageRequest.of(page, size), provincestream2.size());

            return provpage;
        }

        return null;

    }


    @RequestMapping(value = "/provinces", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Province province) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.PROVINCE,AuthProvider.INSERT)) {
            Province provname = dao.findByName(province.getName());
            if (provname != null)
                return "Error-Validation : Client status Exists";
            else
                try {
                    dao.save(province);
                    return "0";
                } catch (Exception p) {
                    return "Error-Saving : " + p.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/provinces", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Province province) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.PROVINCE,AuthProvider.INSERT)) {
            Province proname = dao.findByName(province.getName());
            if (proname != null)
                return "Error-Validation : Client status Exists";
            else
                try {
                    dao.save(province);
                    return "0";
                } catch (Exception p) {
                    return "Error-Saving : " + p.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/provinces", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Province province ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.PROVINCE,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(province.getId()));
                return "0";
            }
            catch(Exception p) {
                return "Error-Deleting : "+p.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


}
