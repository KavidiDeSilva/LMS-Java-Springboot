package com.nhdasystem.controller;

import com.nhdasystem.dao.DesignationDao;
import com.nhdasystem.entity.Designation;
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
public class DesignationController {

    @Autowired
    private DesignationDao dao;


    @RequestMapping(value = "/designations/list", method = RequestMethod.GET, produces = "application/json")
    public List<Designation> designation() {
        return dao.list();
    }



    @RequestMapping(value = "/designations", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Designation> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestParam("name") String name,@RequestParam("page") int page, @RequestParam("size") int size) {

       // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.DESIGNATION,AuthProvider.SELECT)) {

            List<Designation> designations = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Designation> designationstream = designations.stream();

            designationstream = designationstream.filter(e -> e.getName().startsWith(name));

            List<Designation> designationstream2 = designationstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < designationstream2.size() ? start + size : designationstream2.size();
            Page<Designation> despage = new PageImpl<>(designationstream2.subList(start, end), PageRequest.of(page, size), designationstream2.size());

            return despage;
        }

        return null;

    }


    @RequestMapping(value = "/designations", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Designation designation) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.DESIGNATION,AuthProvider.INSERT)) {
            Designation desname = dao.findByName(designation.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(designation);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/designations", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Designation designation) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.DESIGNATION,AuthProvider.INSERT)) {
            Designation desname = dao.findByName(designation.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(designation);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/designations", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Designation designation ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.DESIGNATION,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(designation.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


/*

    @RequestMapping(value = "/employees", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Designation employee) {

        if(AuthProvider.isAuthorized(username,password,"Designation",AuthProvider.UPDATE)) {
            Designation emp = dao.findByNIC(employee.getNic());
            if(emp==null || emp.getId()==employee.getId()) {
                try {
                    dao.save(employee);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }
            else {  return "Error-Updating : NIC Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/employees", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Designation employee ) {
        if(AuthProvider.isAuthorized(username,password,"Designation",AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(employee.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

*/

}
