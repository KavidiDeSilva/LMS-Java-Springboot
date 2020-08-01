package com.nhdasystem.controller;

import com.nhdasystem.dao.DistrictDao;
import com.nhdasystem.entity.District;
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
public class DistrictController {
    @Autowired
    private DistrictDao dao;

    @RequestMapping(value = "/districts/list", method = RequestMethod.GET, produces = "application/json")
    public List<District> district() {
        return dao.list();
    }

    @RequestMapping(value = "/districts/listbyprovince", params = "provinceId", method = RequestMethod.GET, produces = "application/json")
    public List<District> districts(@Param("provinceId") Integer provinceId) {
        return dao.listByProvince(provinceId);
    }


    @RequestMapping(value = "/districts", params = {"page", "size","name","code"}, method = RequestMethod.GET, produces = "application/json")
    public Page<District> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value= "password", required=false) String password,
        @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("code") String code) {

        // System.out.println(name);
        if(AuthProvider.isAuthorized(username,password, ModuleList.DISTRICT,AuthProvider.SELECT)) {

            List<District> districts = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<District> disstrictstream = districts.stream();

            disstrictstream = disstrictstream.filter(d -> d.getName().startsWith(name));
            disstrictstream = disstrictstream.filter(d -> d.getCode().startsWith(code));

            List<District> disstrictstream2 = disstrictstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < disstrictstream2.size() ? start + size : disstrictstream2.size();
            Page<District> dispage = new PageImpl<>(disstrictstream2.subList(start, end), PageRequest.of(page, size), disstrictstream2.size());

            return dispage;
        }

        return null;

    }


    @RequestMapping(value = "/districts", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody District district) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.DISTRICT,AuthProvider.INSERT)) {
            District disname = dao.findByName(district.getName());
            if (disname != null)
                return "Error-Validation : District Exists";
            else
                try {
                    dao.save(district);
                    return "0";
                } catch (Exception d) {
                    return "Error-Saving : " + d.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/districts", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody District district) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.DISTRICT,AuthProvider.INSERT)) {
            District disname = dao.findByName(district.getName());
            if (disname != null)
                return "Error-Validation : District Exists";
            else
                try {
                    dao.save(district);
                    return "0";
                } catch (Exception d) {
                    return "Error-Saving : " + d.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/districts", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody District district ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.DISTRICT,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(district.getId()));
                return "0";
            }
            catch(Exception d) {
                return "Error-Deleting : "+d.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}

