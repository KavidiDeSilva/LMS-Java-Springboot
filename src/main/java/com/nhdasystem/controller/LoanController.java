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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;


@RestController
public class LoanController {

    @Autowired
    private LoanDao dao;

    @Autowired
    private CriteriaDao criteriaDao;

    @Autowired
    private LoantypeDao loantypeDao;

//    @Autowired
//    private ClientDao daoclient;
//
//    @Autowired
//    private ClientstatusDao daoclientstatus;
//
//    @Autowired
//    private ClientstatusDao daoclientstatus;


    @RequestMapping(value = "/loans/list", method = RequestMethod.GET, produces = "application/json")
    public List<Loan> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }

    @RequestMapping(value = "/loans/list/openloans", method = RequestMethod.GET, produces = "application/json")
    public List<Loan> listofopenloans(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.listofopenloans();
        }
        return null;
    }



    @RequestMapping(value = "/loans", params = {"page","size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Loan> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.LOAN,AuthProvider.SELECT)) {
         //   System.out.println("Ha Ha");
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }




    @RequestMapping(value = "/loans", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Loan> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page,
                              @RequestParam("size") int size, @RequestParam("name") String name) {

        // System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.LOAN,AuthProvider.SELECT)) {

            List<Loan> loans = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Loan> loanstream = loans.stream();
           // loanstream = loanstream.filter(l -> !(l.getCallingname().equals("Admin")));

            loanstream = loanstream.filter(l -> l.getName().startsWith(name));

            List<Loan> loanstream2 = loanstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < loanstream2.size() ? start + size : loanstream2.size();
            Page<Loan> loanpage = new PageImpl<>(loanstream2.subList(start, end), PageRequest.of(page, size), loanstream2.size());

            return loanpage;
        }

        return null;

    }

    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password",
            required=false) String password, @Validated @RequestBody Loan loan) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.LOAN,AuthProvider.INSERT)) {

            Loan loanno = dao.findByName(loan.getName());
            if (loanno != null)
                return "Error-Validation : Loan No Exists";

            else
                try {
                    for (Loancriteria lc : loan.getLoancriteriaList())
                        lc.setLoanId(loan);




                    dao.save(loan);
                    return "0";
                } catch (Exception l) {
                    return "Error-Saving : " + l.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    
    @RequestMapping(value = "/loans", method = RequestMethod.PUT)
    public String updateloan(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@Validated @RequestBody Loan loan) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOAN,AuthProvider.UPDATE)) {
            try {
                Loan loanfrompersisten = dao.getOne(loan.getId());

                loanfrompersisten.setEmployeeId(loan.getEmployeeId());

                loanfrompersisten.setName(loan.getName());
                loanfrompersisten.setAmount(loan.getAmount());

                loanfrompersisten.setDuration(loan.getDuration());
                loanfrompersisten.setReqamount(loan.getReqamount());

                loanfrompersisten.setReqdate(loan.getReqdate());

                loanfrompersisten.setDogranted(loan.getDogranted());
                loanfrompersisten.setDomaturity(loan.getDomaturity());
                loanfrompersisten.setEquatedmvalue(loan.getEquatedmvalue());
                loanfrompersisten.setLoantypeId(loan.getLoantypeId());
                loanfrompersisten.setGurantordetailId(loan.getGurantordetailId());
                loanfrompersisten.setClientId(loan.getClientId());
                loanfrompersisten.setLoanstatusId(loan.getLoanstatusId());
                loanfrompersisten.setPurposeofloanId(loan.getPurposeofloanId());
                loanfrompersisten.setPropertydetailId(loan.getPropertydetailId());
                loanfrompersisten.setDescription(loan.getDescription());

                loanfrompersisten.getLoancriteriaList().clear();

                for (Loancriteria lnc:loan.getLoancriteriaList())
                { loanfrompersisten.getLoancriteriaList().add(lnc);
                    lnc.setLoanId(loanfrompersisten);
                }

                dao.save(loanfrompersisten);
                return "0";
            }
            catch(Exception l) {
                return "Error-Saving : " + l.getMessage();
            }
        }
        else
            return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/loans", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@RequestBody Loan loan ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.LOAN,AuthProvider.DELETE)) {
            Loan loanno = dao.findByName(loan.getName());
            try {
                Loanstatus loanstatus = new Loanstatus(3);
                loanno.setLoanstatusId(loanstatus);
                dao.save(loanno);
               // dao.delete(dao.getOne(loan.getId()));
                return "0";
            }
            catch(Exception l) {
                return "Error-Deleting : "+l.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

    @RequestMapping(value = "/loans/lastloanno", method = RequestMethod.GET, produces = "application/json")
    public String lastloanno(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.LOAN ,AuthProvider.SELECT)) {
            List<Integer> nos = dao.lastLoanno().stream().map(s->{
                return Integer.parseInt(s);
            }).collect(Collectors.toList());

            Collections.sort(nos);
            // System.out.println(porderno);
            return "{\"no\":"+"\""+(nos.get(nos.size() - 1)+1)+"\"}";
        }
        return null;
    }





}
