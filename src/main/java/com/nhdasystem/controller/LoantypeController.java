package com.nhdasystem.controller;



import com.nhdasystem.dao.LoancategoryDao;
import com.nhdasystem.dao.LoantypeDao;
import com.nhdasystem.dao.LoantypestatusDao;
import com.nhdasystem.entity.Criteria;
import com.nhdasystem.entity.Loancategory;
import com.nhdasystem.entity.Loantype;
import com.nhdasystem.entity.Loantypestatus;
import com.nhdasystem.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
public class LoantypeController {

    @Autowired
    private LoantypeDao dao;

//    @Autowired
//    private LoancategoryDao daoLoancategory;

    @Autowired
    private LoantypestatusDao daoLoantypestatus;


    @RequestMapping(value = "/loantypes/list", method = RequestMethod.GET, produces = "application/json")
    public List<Loantype> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) { return dao.list(); }return null; }


    @RequestMapping(value = "/loantypes", params = {"page","size"}, method = RequestMethod.GET, produces =
            "application/json")
    public Page<Loantype> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANTYPE,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }



    @RequestMapping(value = "/loantypes", params = {"page", "size","name","loantypestatusId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Loantype> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page,
                              @RequestParam("size") int size, @RequestParam("name") String name,@RequestParam("loancategoryId") Integer loancategoryId, @RequestParam("loantypestatusId") Integer loantypestatusId) {

        // System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANTYPE,AuthProvider.SELECT)) {

            List<Loantype> loantypes = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Loantype> loantypestream = loantypes.stream();
            // loantypestream = loantypestream.filter(l -> !(l.getCallingname().equals("Admin")));
//
//            if (loancategoryId != null)
//                loantypestream = loantypestream.filter(l -> l.getLoancategoryId().equals(daoLoancategory.getOne(loancategoryId)));

            if (loantypestatusId != null)
                loantypestream = loantypestream.filter(l -> l.getLoantypestatusId().equals(daoLoantypestatus.getOne(loantypestatusId)));
            
            loantypestream = loantypestream.filter(l -> l.getName().startsWith(name));

            List<Loantype> loantypestream2 = loantypestream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < loantypestream2.size() ? start + size : loantypestream2.size();
            Page<Loantype> loantypepage = new PageImpl<>(loantypestream2.subList(start, end), PageRequest.of(page, size), loantypestream2.size());

            return loantypepage;
        }

        return null;

    }

    @RequestMapping(value = "/loantypes", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password",
            required=false) String password, @Validated @RequestBody Loantype loantype) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANTYPE,AuthProvider.INSERT)) {

            Loantype loantypename = dao.findByName(loantype.getName());
            if (loantypename != null)
                return "Error-Validation : Name Exists";

            else
                try {
                    for (Criteria lc : loantype.getCriteriaList())
                        lc.setLoantypeId(loantype);

                    dao.save(loantype);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/loantypes", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@Validated @RequestBody Loantype loantype) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANTYPE,AuthProvider.UPDATE)) {
            Loantype loantypeid = dao.findByName(loantype.getName());
            if(loantypeid==null || loantypeid.getId()==loantype.getId()) {
                try {
                    for (Criteria mc : loantype.getCriteriaList())
                        mc.setLoantypeId(loantype);
                    dao.save(loantype);
                    return "0";
                }
                catch(Exception l) {
                    return "Error-Updating : "+l.getMessage();
                }
            }
            else {  return "Error-Updating : Loan Type Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/loantypes", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@RequestBody Loantype loantype ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANTYPE,AuthProvider.DELETE)) {
            Loantype loantypeid = dao.findByName(loantype.getName());
            try {
                Loantypestatus status = new Loantypestatus(3);
                loantypeid.setLoantypestatusId(status);
                dao.save(loantypeid);
               // dao.delete(dao.getOne(loantype.getId()));
                return "0";
            }
            catch(Exception l) {
                return "Error-Deleting : "+l.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


}
