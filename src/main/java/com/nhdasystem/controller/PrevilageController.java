package com.nhdasystem.controller;

import com.nhdasystem.dao.ModuleDao;
import com.nhdasystem.dao.PrivilageDao;
import com.nhdasystem.dao.RoleDao;
import com.nhdasystem.entity.Privilage;
import com.nhdasystem.entity.Userrole;
import com.nhdasystem.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PrevilageController {

    @Autowired
    private PrivilageDao dao;

    @Autowired
    private RoleDao daoRole;

    @Autowired
    private ModuleDao daoModule;


    @RequestMapping(value = "/privilages", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Privilage> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.PRIVILAGE,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        } else return null;
    }





    @RequestMapping(value = "/privilages", params = {"page", "size","roleid","moduleid","employeeid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Privilage> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("roleid") Integer roleid, @RequestParam("moduleid") Integer moduleid, @RequestParam("employeeid") Integer employeeid) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.PRIVILAGE,AuthProvider.SELECT)) {

        List<Privilage> privilages = dao.findAll(Sort.by(Sort.Direction.DESC,"id"));
        Stream<Privilage> privilagestream = privilages.stream();

        if(roleid!=null)
            privilagestream =  privilagestream.filter(p -> p.getRoleId().equals(daoRole.getOne(roleid)));

        if(moduleid!=null)
            privilagestream =  privilagestream.filter(p -> p.getModuleId().equals(daoModule.getOne(moduleid)));


        if(employeeid!=null) {
            privilagestream = privilagestream.filter(p -> {
                for (Userrole ur: p.getRoleId().getUserroleList())
                    if(ur.getUserId().getEmployeeId().getId().equals(employeeid))
                        return true;
                return false;
            });
        }


        List<Privilage> privilages2=privilagestream.collect(Collectors.toList());

        int start = page*size;
        int end = start+size<privilages2.size()?start+size:privilages2.size();
        Page<Privilage> privilagepage = new PageImpl<>(privilages2.subList(start,end), PageRequest.of(page, size), privilages2.size());


        return privilagepage;
        }
        else return null;

    }

    @RequestMapping(value = "/privilages", params = {"module"}, method = RequestMethod.GET, produces = "application/json")
    public HashMap<String,Boolean> getModulePrivilage(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("module") String module) {

        System.out.println("gggggg");
           return AuthProvider.getPrivilages(username,password,module);


    }





    @RequestMapping(value = "/privilages", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Privilage privilage) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.PRIVILAGE,AuthProvider.INSERT)) {
        Privilage prirolemodule = dao.findByRoleModule(privilage.getRoleId(),privilage.getModuleId());
        if(prirolemodule!=null)
            return "Error-Validation : Privilage Exists";
        else
             try {
                dao.save(privilage);
                return "0";
            }
            catch(Exception e) {
                return "Error-Saving : "+e.getMessage();
            }
        }
        else
            return "Error-Saving : You have no Permission";


    }



    @RequestMapping(value = "/privilages", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Privilage privilage) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.PRIVILAGE,AuthProvider.UPDATE)) {
        Privilage prirolemodule = dao.findByRoleModule(privilage.getRoleId(),privilage.getModuleId());
        if(prirolemodule==null)
            return "Error-Validation : Privilage Does Not Exists";
        else {
            try {
                dao.save(privilage);
                return "0";
            } catch (Exception e) {
                return "Error-Updating : " + e.getMessage();
            }
        }
        }
        else
            return "Error-Updating : You have no Permission";


        }



    @RequestMapping(value = "/privilages", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestBody Privilage privilage ) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.PRIVILAGE, AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(privilage.getId()));
                return "0";
            } catch (Exception e) {
                return "Error-Deleting : " + e.getMessage();
            }

        } else
            return "Error-Deleting : You have no Permission";

    }


}
