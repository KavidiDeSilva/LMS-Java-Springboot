package com.nhdasystem.controller;


import com.nhdasystem.dao.ModuleDao;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;




@RestController
@RequestMapping("/ui")
public class UiController {


    @Autowired
    private ModuleDao daomodule;


    @RequestMapping("/config")
    public ModelAndView config(){
        ModelAndView model = new ModelAndView();
        model.setViewName("config.html");
        return model;
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView model = new ModelAndView();
        model.setViewName("login.html");
        return model;
    }


    @RequestMapping("/mainwindow")
    public ModelAndView mainwindow(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("mainwindow.html",username,password);
    }
    @RequestMapping("/home")
    public ModelAndView home(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("home-branch.html",username,password);
    }

    @RequestMapping("/branchhome")
    public ModelAndView branchhome(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("home-branch.html",username,password);
    }
    @RequestMapping("/branch")
    public ModelAndView branch(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("branch.html",username,password);
    }
    @RequestMapping("/branches")
    public ModelAndView branches(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("branches.html",username,password);
    }

    @RequestMapping("/employee")
    public ModelAndView employee(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("employee.html",username,password);
    }

    @RequestMapping("/client")
    public ModelAndView client(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password){
        return getModelView("client.html",username,password);
    }

    @RequestMapping("/gurantor")
    public ModelAndView gurantor(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("gurantor.html",username,password);
    }

    @RequestMapping("/admin")
    public ModelAndView admin(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("admin.html",username,password);
    }

    @RequestMapping("/user")
    public ModelAndView user(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("user.html",username,password);
    }

    @RequestMapping("/previlage")
    public ModelAndView previlage(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("previlage.html",username,password);

    }

    @RequestMapping("/changepassword")
    public ModelAndView changepassword(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("changepassword.html",username,password);
    }

    @RequestMapping("/designation")
    public ModelAndView designation(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("designation.html",username,password);
    }

    @RequestMapping("/gndivision")
    public ModelAndView gndivision(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("gndivision.html",username,password);
    }

    @RequestMapping("/dsdivision")
    public ModelAndView dsdivision(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("dsdivision.html",username,password);
    }

    @RequestMapping("/district")
    public ModelAndView district(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("district.html",username,password);
    }

    @RequestMapping("/province")
    public ModelAndView province(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("province.html",username,password);
    }

    @RequestMapping("/loan")
    public ModelAndView loan(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loan.html",username,password);
    }

    @RequestMapping("/loantypestatus")
    public ModelAndView loantypestatus(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loantypestatus.html",username,password);
    }

    @RequestMapping("/loanstatus")
    public ModelAndView loanstatus(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loanstatus.html",username,password);
    }

    @RequestMapping("/loancategory")
    public ModelAndView loancategory(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loancategory.html",username,password);
    }

    @RequestMapping("/loantype")
    public ModelAndView loantype(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loantype.html",username,password);
    }

    @RequestMapping("/loancalculator")
    public ModelAndView loancalculator(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loancalculator.html",username,password);
    }
    @RequestMapping("/loanpaymentstatus")
    public ModelAndView loanpaymentstatus(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loanpaymentstatus.html",username,password);
    }
    @RequestMapping("/loanpayment")
    public ModelAndView loanpayment(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("loanpayment.html",username,password);
    }
    @RequestMapping("/property")
    public ModelAndView property(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("property.html",username,password);
    }
    @RequestMapping("/constraint")
    public ModelAndView constraint(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("constraint.html",username,password);
    }
    @RequestMapping("/criteria")
    public ModelAndView criteria(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("criteria.html",username,password);
    }

    @RequestMapping("/propertystatus")
    public ModelAndView propertystatus(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password){
        return getModelView("propertystatus.html",username,password);
    }
    @RequestMapping("/reportview")
    public ModelAndView reporttab(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password){
        return getModelView("reporttab.html",username,password);
    }
    @RequestMapping("/role")
    public ModelAndView role(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password){
        return getModelView("role.html",username,password);
    }






    // search krla athin liwwa code ekak
//    @RequestMapping("/error")
//    public ModelAndView handleError(String page,String username, String password){
//
//        ModelAndView status = new ModelAndView();
//        Object  = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        if (status != null) {
//            Integer statusCode = Integer.valueOf(status.toString());
//
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                status.setViewName("404.html");
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                status.setViewName("noprivilage.html");
//            }
//        }
//        return status;
//    }





    public ModelAndView getModelView(String page,String username, String password){

        ModelAndView model = new ModelAndView();

        if(AuthProvider.isUser(username,password)) {

            model.setViewName(page);
        }
        else if (AuthProvider.isNotUser(username,password)){
            model.setViewName("noprivilage.html");

        }
        return model;

    }
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView getStatus(){
//
//        ModelAndView model = new ModelAndView();
//
//        model.setViewName("404.html");
//
//        return model;
//
//    }




}
