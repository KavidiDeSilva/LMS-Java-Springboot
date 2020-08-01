package com.nhdasystem.controller;



import com.nhdasystem.dao.*;
import com.nhdasystem.entity.*;
import com.nhdasystem.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class LoanpaymentController {
    @Autowired
    private LoanpaymentDao dao;

    @Autowired
    private LoanpaymentstatusDao daoLoanpaymentstatus;

    @Autowired
    private LoanDao daoLoan;

    @Autowired
    private LoanstatusDao daoLoanstatus;

    @Autowired
    private ClientstatusDao daoCstatus;

    @Autowired
    private ClientDao daoClient;

    @Autowired
    private GurantordetailDao daoGurantor;

    @Autowired
    private PropertydetailDao daoProperty;


    @RequestMapping(value = "/loanpayments/list", method = RequestMethod.GET, produces = "application/json")
    public List<Loanpayment> loanpayments() {
        return dao.list();
    }


//
//    @RequestMapping(value = "/loanpayments/listbyloan", params = "loanId", method = RequestMethod.GET, produces = "application/json")
//    public List<Loanpayment> listbyloan(@Param("loanId") Integer loanId) {
//        return dao.listByLoan(loanId);
//    }


    @RequestMapping(value = "/loanpayments/totalrepayments", params = {"loanId"},method = RequestMethod.GET, produces = "application/json")
    public String totalrepayment(@CookieValue(value="username") String username, @CookieValue(value="password")
            String password, @RequestParam("loanId") Integer loanId) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT ,AuthProvider.SELECT)) {
            String totalPay = dao.totalrepayments(loanId);
             System.out.println(totalPay);
            return "{\"no\":"+"\""+totalPay+"\"}";
        }
        return null;
    }
    @RequestMapping(value = "/loanpayments/lastpaydate", params = {"loanId"},method = RequestMethod.GET, produces =
            "application/json")
    public String lastpaydate(@CookieValue(value="username") String username, @CookieValue(value="password")
            String password, @RequestParam("loanId") Integer loanId) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT ,AuthProvider.SELECT)) {
            LocalDate lastpay = dao.lastpaydate(loanId);
            System.out.println(lastpay);
            return "{\"no\":"+"\""+lastpay+"\"}";
        }
        return null;
    }

    @RequestMapping(value = "/loanpayments", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Loanpayment> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/loanpayments", params = {"page", "size","name","loanId","loanpaymentstatusId"}, method =
            RequestMethod.GET, produces = "application/json")
    public Page<Loanpayment> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password,
                                     @RequestParam("page") int page, @RequestParam("size") int size,
                                     @RequestParam("name") String name,@RequestParam("loanId") Integer loanId,
                                     @RequestParam("loanpaymentstatusId") Integer loanpaymentstatusId) {
         //System.out.println(name+"-"+loanId+"-"+loanpaymentstatusId);

        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT,AuthProvider.SELECT)) {

            List<Loanpayment> loanpayments = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Loanpayment> loanpaymentstream = loanpayments.stream();
            // loanpaymentstream = loanpaymentstream.filter(l -> !(l.getCallingname().equals("Admin")));
//
            if (loanId != null)
                loanpaymentstream = loanpaymentstream.filter(l -> l.getLoanId().equals(daoLoan.getOne(loanId)));

            if (loanpaymentstatusId != null)
                loanpaymentstream = loanpaymentstream.filter(l -> l.getLoanpaymentstatusId().equals(daoLoanpaymentstatus.getOne
                        (loanpaymentstatusId)));
            loanpaymentstream = loanpaymentstream.filter(l -> l.getName().startsWith(name));

            List<Loanpayment> loanpaymentstream2 = loanpaymentstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < loanpaymentstream2.size() ? start + size : loanpaymentstream2.size();
            Page<Loanpayment> loanpaymentpg = new PageImpl<>(loanpaymentstream2.subList(start, end), PageRequest.of(page, size), loanpaymentstream2.size());

            return loanpaymentpg;
        }

        return null;

    }
//----------------------------------Add----------------------------------------------------------
    @RequestMapping(value = "/loanpayments", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password",
            required=false) String password, @Validated @RequestBody Loanpayment loanpayment) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANPAYMENT,AuthProvider.INSERT)) {

            Loanpayment loanpaymentname = dao.findByName(loanpayment.getName());

            if (loanpaymentname != null)
                return "Error-Validation : Receipt no Exists";

            else
                try {
//
//                        Loan loanidd = loanpaymentname.getLoanId();
//                        Loanstatus loanstatuss = daoLoanstatus.getOne(3);
//                        Loan loann = daoLoan.getOne(loanidd.getId());
//                        loanidd.setLoanstatusId(loanstatuss);
//                        daoLoan.save(loann);

                    dao.save(loanpayment);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

//---------------------------------------Update----------------------------------------------------------------
//    @RequestMapping(value = "/loanpayments", method = RequestMethod.PUT)
//    public String update(@CookieValue(value="username", required=false) String username, @CookieValue
//            (value="password", required=false) String password,@Validated @RequestBody Loanpayment loanpayment) {
//        //System.out.println(loanpayment);
//        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANPAYMENT,AuthProvider.UPDATE)) {
//
//            Loanpayment loanpayname = dao.findByName(loanpayment.getName());
//
//            if(loanpayname==null || loanpayname.getId()==loanpayment.getId()){
//                try {
//                    Loan loanid = loanpayname.getLoanId();
//                    Loanstatus loanstatus = daoLoanstatus.getOne(3);
//                    Loan loan = daoLoan.getOne(loanid.getId());
//                    loanid.setLoanstatusId(loanstatus);
//                    daoLoan.save(loan);
//
//
//                    dao.save(loanpayment);
//                    return "0";
//                }
//                catch(Exception l) {
//                    return "Error-Updating : "+l.getMessage();
//                }
//            }
//            else {  return "Error-Updating : Receipt no Exists"; }
//        }
//        return "Error-Updating : You have no Permission";
//    }


    @RequestMapping(value = "/loanpayments", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@Validated @RequestBody Loanpayment loanpayment) {
        //System.out.println(loanpayment);
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANPAYMENT,AuthProvider.UPDATE)) {

            Loanpayment loanpayname = dao.findByName(loanpayment.getName());

            if(loanpayname==null || loanpayname.getId()==loanpayment.getId()){
                try {

                    dao.save(loanpayment);
                    return "0";
                }
                catch(Exception l) {
                    return "Error-Updating : "+l.getMessage();
                }
            }
            else {  return "Error-Updating : Receipt no Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }

//-----------------------------Delete-------------------------------------------------------------------
    @RequestMapping(value = "/loanpayments", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@RequestBody Loanpayment loanpayment ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOANPAYMENT,AuthProvider.DELETE)) {
            Loanpayment loanpayname = dao.findByName(loanpayment.getName());
            try {
                Loanpaymentstatus status = new Loanpaymentstatus(4);
                loanpayname.setLoanpaymentstatusId(status);
                dao.save(loanpayname);
               // dao.delete(dao.getOne(loanpayment.getId()));
                return "0";
            }
            catch(Exception l) {
                return "Error-Deleting : "+l.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


    @RequestMapping(value = "/loanpayments/lpname", method = RequestMethod.GET, produces = "application/json")
    public String lastLoanpayCode(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.LOANPAYMENT,AuthProvider.SELECT)) {
            String lpcode = dao.lastLoanpayno();
            //System.out.println(clicode);
            Integer lpCode = Integer.parseInt(lpcode);
            String lpayno="";
            if(lpCode<9)
                lpayno = "R0000"+(lpCode+1);
            else if(lpCode<99)
                lpayno = "R000"+(lpCode+1);
            else if(lpCode<999)
                lpayno = "R00"+(lpCode+1);
            else if(lpCode<9999)
                lpayno = "R0"+ (lpCode+1);

            return "{\"no\":"+"\""+lpayno+"\"}";
        }
        return null;
    }




}
