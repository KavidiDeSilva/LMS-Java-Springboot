package com.nhdasystem.controller;

import com.nhdasystem.dao.ClientstatusDao;
import com.nhdasystem.dao.GurantordetailDao;
import com.nhdasystem.entity.Cstatus;
import com.nhdasystem.entity.Gurantordetail;
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
public class GurantordetailController {
    @Autowired
    private GurantordetailDao dao;

    @Autowired
    private ClientstatusDao daoCstatus;

    @RequestMapping(value = "/gurantors/list", method = RequestMethod.GET, produces = "application/json")
    public List<Gurantordetail> list(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }

    @RequestMapping(value = "/gurantors/list/withoutloans",  method = RequestMethod.GET, produces = "application/json")
    public List<Gurantordetail> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.listWithoutLoans();
        }
        return null;
    }

    @RequestMapping(value = "/gurantors", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Gurantordetail> findAll(@CookieValue(value="username") String username,
                           @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.GURANTOR,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/gurantors", params = {"page", "size","name","nic","cstatusId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Gurantordetail> findAll(@CookieValue(value="username") String username, @CookieValue
            (value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size,
                                        @RequestParam("name") String name, @RequestParam("nic") String nic,
                                        @RequestParam("cstatusId") Integer cstatusId) {

       //  System.out.println(name+"-"+nic+"-"+cstatusId);


        if(AuthProvider.isAuthorized(username,password, ModuleList.GURANTOR,AuthProvider.SELECT)) {

            List<Gurantordetail> gurantors = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Gurantordetail> gurantorstream = gurantors.stream();
           // gurantorstream = gurantorstream.filter(g -> !(g.getCallingname().equals("Admin")));

            if (cstatusId != null)
                gurantorstream = gurantorstream.filter(g -> g.getCstatusId().equals(daoCstatus.getOne(cstatusId)));
            gurantorstream = gurantorstream.filter(g -> g.getNic().startsWith(nic));
            gurantorstream = gurantorstream.filter(g -> g.getName().contains(name));

            List<Gurantordetail> gurantorstream2 = gurantorstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < gurantorstream2.size() ? start + size : gurantorstream2.size();
            Page<Gurantordetail> gurpage = new PageImpl<>(gurantorstream2.subList(start, end), PageRequest.of(page, size), gurantorstream2.size());

            return gurpage;
        }

        return null;

    }


    @RequestMapping(value = "/gurantors", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Gurantordetail gurantordetail) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.GURANTOR,AuthProvider.INSERT)) {
            Gurantordetail gurnic = dao.findByNIC(gurantordetail.getNic());
            Gurantordetail gurcode = dao.findByCode(gurantordetail.getCode());
            if (gurnic != null)
                return "Error-Validation : NIC Exists";
            else if (gurcode != null)
                return "Error-Validation : Code Exists";
            else
                try {
                    dao.save(gurantordetail);
                    return "0";
                } catch (Exception g) {
                    return "Error-Saving : " + g.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/gurantors", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Gurantordetail gurantordetail) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.GURANTOR,AuthProvider.UPDATE)) {
            Gurantordetail gur = dao.findByNIC(gurantordetail.getNic());
            if(gur==null || gur.getId()==gurantordetail.getId()) {
                try {
                    dao.save(gurantordetail);
                    return "0";
                }
                catch(Exception g) {
                    return "Error-Updating : "+g.getMessage();
                }
            }
            else {  return "Error-Updating : NIC Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/gurantors", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Gurantordetail gurantordetail ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.GURANTOR,AuthProvider.DELETE)) {
            Gurantordetail gur = dao.findByNIC(gurantordetail.getNic());
            try {
                Cstatus status = new Cstatus(2);
                gur.setCstatusId(status);
                dao.save(gur);
                //dao.delete(dao.getOne(gurantordetail.getId()));
                return "0";
            }
            catch(Exception g) {
                return "Error-Deleting : "+g.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

    @RequestMapping(value = "/gurantors/gurcode", method = RequestMethod.GET, produces = "application/json")
    public String lastGurantorCode(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.GURANTOR,AuthProvider.SELECT)) {
            String gurcode = dao.lastGurantorno();
            Integer gCode = Integer.parseInt(gurcode);
            String gurantorno="";
            if(gCode<9)
                gurantorno = "G0000"+(gCode+1);
            else if(gCode<99)
                gurantorno = "G000"+(gCode+1);
            else if(gCode<999)
                gurantorno = "G00"+(gCode+1);
            else if(gCode<9999)
                gurantorno = "G0"+ (gCode+1);

            //System.out.println(gurantorno);

            return "{\"no\":"+"\""+gurantorno+"\"}";
        }
        return null;
    }

}
