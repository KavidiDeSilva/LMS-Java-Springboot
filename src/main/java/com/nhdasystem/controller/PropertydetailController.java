package com.nhdasystem.controller;

import com.nhdasystem.dao.PropertydetailDao;
import com.nhdasystem.entity.Propertydetail;
import com.nhdasystem.entity.Propertystatus;
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
public class PropertydetailController {
    @Autowired
    private PropertydetailDao dao;

    @RequestMapping(value = "/propertydetails/list", method = RequestMethod.GET, produces = "application/json")
    public List<Propertydetail> propertydetail() {
        return dao.list();
    }

    @RequestMapping(value = "/propertydetails/list/withoutloans",  method = RequestMethod.GET, produces = "application/json")
    public List<Propertydetail> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.listWithoutLoans();
        }
        return null;
    }


    @RequestMapping(value = "/propertydetails", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Propertydetail> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.PROPERTY,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/propertydetails", params = {"page", "size","number","ownership","propertystatusId"},
            method = RequestMethod.GET, produces = "application/json")
    public Page<Propertydetail> findAll(@CookieValue(value="username") String username, @CookieValue(value="password")
            String password, @RequestParam("page") int page, @RequestParam("size") int size,
                                        @RequestParam("number") String number,@RequestParam("ownership") String ownership,
                                        @RequestParam("propertystatusId") Integer propertystatusId) {

        // System.out.println(assessmentno+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.PROPERTY,AuthProvider.SELECT)) {

            List<Propertydetail> propertydetails = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Propertydetail> propertystream = propertydetails.stream();
            // propertystream = propertystream.filter(p -> !(p.getCallingname().equals("Admin")));

            propertystream = propertystream.filter(p -> p.getNumber().startsWith(number));

            List<Propertydetail> propertystream2 = propertystream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < propertystream2.size() ? start + size : propertystream2.size();
            Page<Propertydetail> propertypage = new PageImpl<>(propertystream2.subList(start, end), PageRequest.of(page, size), propertystream2.size());

            return propertypage;
        }

        return null;

    }

    @RequestMapping(value = "/propertydetails", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password",
            required=false) String password, @Validated @RequestBody Propertydetail propertydetail) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.PROPERTY,AuthProvider.INSERT)) {

            Propertydetail num = dao.findByNumber(propertydetail.getNumber());
            if (num != null)
                return "Error-Validation : Number Exists";

            else
                try {
                    dao.save(propertydetail);
                    return "0";
                } catch (Exception p) {
                    return "Error-Saving : " + p.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/propertydetails", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@Validated @RequestBody Propertydetail propertydetail) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.PROPERTY,AuthProvider.UPDATE)) {
            Propertydetail prop = dao.findByNumber(propertydetail.getNumber());
            if(prop==null || prop.getId()==propertydetail.getId()) {
                try {
                    dao.save(propertydetail);
                    return "0";
                }
                catch(Exception p) {
                    return "Error-Updating : "+p.getMessage();
                }
            }
            else {  return "Error-Updating : Number Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/propertydetails", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@RequestBody Propertydetail propertydetail ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.PROPERTY,AuthProvider.DELETE)) {
            Propertydetail prop = dao.findByNumber(propertydetail.getNumber());
            try {
                Propertystatus status = new Propertystatus(3);
                prop.setPropertystatusId(status);
                dao.save(prop);
               // dao.delete(dao.getOne(propertydetail.getId()));
                return "0";
            }
            catch(Exception p) {
                return "Error-Deleting : "+p.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

    @RequestMapping(value = "/propertydetails/pronum", method = RequestMethod.GET, produces = "application/json")
    public String lastPropertyno(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.GURANTOR,AuthProvider.SELECT)) {
            String prono = dao.lastPropertyno();
            Integer pNum = Integer.parseInt(prono);
            String propertyno="";
            if(pNum<9)
                propertyno = "0000"+(pNum+1);
            else if(pNum<99)
                propertyno = "000"+(pNum+1);
            else if(pNum<999)
                propertyno = "00"+(pNum+1);
            else if(pNum<9999)
                propertyno = "0"+ (pNum+1);

            return "{\"no\":"+"\""+propertyno+"\"}";
        }
        return null;
    }


}
