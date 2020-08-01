package com.nhdasystem.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ui/report")
public class ReportUiController {

    @RequestMapping("/systemaccessanalysis")
    public ModelAndView login(){
        ModelAndView model = new ModelAndView();
        model.setViewName("systemaccessanalysis.html");
        return model;
    }

    @RequestMapping("/borrowersreport")
    public ModelAndView clients(){
        ModelAndView model = new ModelAndView();
        model.setViewName("borrowersreport.html");
        return model;
    }
    @RequestMapping("/loansreport")
    public ModelAndView loansreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("loansreport.html");
        return model;
    }
    @RequestMapping("/loanpaymentdetailsreport")
    public ModelAndView loanpaymentdetailsreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("loanpaymentdetailsreport.html");
        return model;
    }
    @RequestMapping("/borrowersdetailsreport")
    public ModelAndView borrowerdetailsreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("borrowersdetailsreport.html");
        return model;
    }
    @RequestMapping("/branchreport")
    public ModelAndView branchreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("branchreport.html");
        return model;
    }
    @RequestMapping("/borrowerrepaymentsreport")
    public ModelAndView borrowerrepaymentsreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("borrowerrepaymentsreport.html");
        return model;
    }
    @RequestMapping("/branchborrowers")
    public ModelAndView branchborrowersreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("branchborrowers.html");
        return model;
    }
    @RequestMapping("/loanhistory")
    public ModelAndView loanhistoryreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("loanhistory1.html");
        return model;
    }
    @RequestMapping("/loanpaymenthistory")
    public ModelAndView loanpaymenthistoryreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("loanpaymenthistory.html");
        return model;
    }
    @RequestMapping("/loantypeloans")
    public ModelAndView loantypeloansreport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("loantypeloans.html");
        return model;
    }
    @RequestMapping("/branchemployee")
    public ModelAndView branchemployeereport(){
        ModelAndView model = new ModelAndView();
        model.setViewName("branchemployees.html");
        return model;
    }



}