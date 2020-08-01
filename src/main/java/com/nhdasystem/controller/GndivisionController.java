package com.nhdasystem.controller;

import com.nhdasystem.dao.GndivisionDao;
import com.nhdasystem.entity.Gndivision;
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
public class GndivisionController {
    @Autowired
    private GndivisionDao dao;

    @RequestMapping(value = "/gndivisions/list", method = RequestMethod.GET, produces = "application/json")
    public List<Gndivision> gndivisions() {
        return dao.list();
    }

    @RequestMapping(value = "/gndivisions/listbydsdivision", params = "dsdivisionId", method = RequestMethod.GET, produces = "application/json")
    public List<Gndivision> districts(@Param("dsdivisionId") Integer dsdivisionId) {
        return dao.listByDsdivision(dsdivisionId);
    }

    @RequestMapping(value = "/gndivisions", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Gndivision> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password,
                                    @RequestParam("page") int page, @RequestParam("size") int size) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.GNDIVISION,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/gndivisions", params = {"page", "size","name","code"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Gndivision> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value=
            "password", required=false) String password, @RequestParam("page") int page, @RequestParam("size") int size,
                                    @RequestParam("name") String name, @RequestParam("code") String code
                                    /*@RequestParam("number") String number*/) {
        //System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.GNDIVISION,AuthProvider.SELECT)) {

            List<Gndivision> gndivisions = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Gndivision> gndivisionstream = gndivisions.stream();

            gndivisionstream = gndivisionstream.filter(g -> g.getName().startsWith(name));
            gndivisionstream = gndivisionstream.filter(g -> g.getCode().startsWith(code));
//            gndivisionstream = gndivisionstream.filter(g -> g.getNumber().startsWith(number));


            List<Gndivision> gndivisionstream2 = gndivisionstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < gndivisionstream2.size() ? start + size : gndivisionstream2.size();
            Page<Gndivision> gndivisionpage = new PageImpl<>(gndivisionstream2.subList(start, end),
                    PageRequest.of(page, size), gndivisionstream2.size());

            return gndivisionpage;
        }

        return null;

    }


    @RequestMapping(value = "/gndivisions", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Gndivision gndivision) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.GNDIVISION,AuthProvider.INSERT)) {
            Gndivision gndname = dao.findByName(gndivision.getName());
//            Gndivision gncode = dao.findByCode(gndivision.getCode());
//            Gndivision gnno = dao.findByNumber(gndivision.getNumber());
            if (gndname != null)
                return "Error-Validation : GN Division Name Exists";

            else
                try {
                    dao.save(gndivision);
                    return "0";
                } catch (Exception g) {
                    return "Error-Saving : " + g.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";
    }

    @RequestMapping(value = "/gndivisions", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Gndivision gndivision) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.GNDIVISION,AuthProvider.UPDATE)) {
            Gndivision gnd = dao.findByName(gndivision.getName());
            if(gnd==null || gnd.getId()==gndivision.getId()) {
                try {
                    dao.save(gndivision);
                    return "0";
                }
                catch(Exception g) {
                    return "Error-Updating : "+g.getMessage();
                }
            }
            else {  return "Error-Updating : GN Division Name Exists"; }
        }
        return "Error-Updating : You have no Permission";

    }


    @RequestMapping(value = "/gndivisions", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Gndivision gndivision ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.GNDIVISION,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(gndivision.getId()));
                return "0";
            }
            catch(Exception g) {
                return "Error-Deleting : "+g.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}
