package com.nhdasystem.controller;

import com.nhdasystem.entity.*;
import com.nhdasystem.util.RegexPattern;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

@RestController
@RequestMapping("/regexes")
public class RegexController {

    @RequestMapping(value = "/employee", produces = "application/json")
    public HashMap<String, HashMap<String, String>> employee() {
        return getRegex(new Employee());
    }

    @RequestMapping(value = "/user", produces = "application/json")
    public HashMap<String, HashMap<String, String>> user() {
        return getRegex(new User());
    }

    @RequestMapping(value = "/designation", produces = "application/json")
    public HashMap<String, HashMap<String, String>> designation() {
        return getRegex(new Designation());
    }

    @RequestMapping(value = "/client", produces = "application/json")
    public HashMap<String, HashMap<String, String>> client() {
        return getRegex(new Client());
    }

    @RequestMapping(value = "/employeestatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> employeestatus() {
        return getRegex(new Employeestatus());
    }

    @RequestMapping(value = "/gurantor", produces = "application/json")
    public HashMap<String, HashMap<String, String>> gurantor() {
        return getRegex(new Gurantordetail());
    }

    @RequestMapping(value = "/clientstatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> clientstatus() { return getRegex(new Cstatus()); }

    @RequestMapping(value = "/purposeofloan", produces = "application/json")
    public HashMap<String, HashMap<String, String>> purposeofloan() { return getRegex(new Purposeofloan()); }

    @RequestMapping(value = "/workingstatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> workingstatus() { return getRegex(new Workingstatus()); }

    @RequestMapping(value = "/civilstatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> civilstatus() { return getRegex(new Civilstatus()); }

    @RequestMapping(value = "/branch", produces = "application/json")
    public HashMap<String, HashMap<String, String>> branch() { return getRegex(new Branch()); }

    @RequestMapping(value = "/gndivision", produces = "application/json")
    public HashMap<String, HashMap<String, String>> gndivision() { return getRegex(new Gndivision()); }

    @RequestMapping(value = "/dsdivision", produces = "application/json")
    public HashMap<String, HashMap<String, String>> dsdivision() { return getRegex(new Dsdivision()); }

    @RequestMapping(value = "/district", produces = "application/json")
    public HashMap<String, HashMap<String, String>> district() { return getRegex(new District()); }

    @RequestMapping(value = "/province", produces = "application/json")
    public HashMap<String, HashMap<String, String>> province() { return getRegex(new Province()); }

    @RequestMapping(value = "/loan", produces = "application/json")
    public HashMap<String, HashMap<String, String>> loan() { return getRegex(new Loan()); }

    @RequestMapping(value = "/loanstatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> loanstatus() { return getRegex(new Loanstatus()); }

    @RequestMapping(value = "/loantypestatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> loantypestatus() { return getRegex(new Loantypestatus()); }

    @RequestMapping(value = "/loancategory", produces = "application/json")
    public HashMap<String, HashMap<String, String>> loancategory() { return getRegex(new Loancategory()); }

    @RequestMapping(value = "/loantype", produces = "application/json")
    public HashMap<String, HashMap<String, String>> loantype() { return getRegex(new Loantype()); }

    @RequestMapping(value = "/loanpaymentstatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> loanpaymentstatus() { return getRegex(new Loanpaymentstatus()); }

    @RequestMapping(value = "/loanpayment", produces = "application/json")
    public HashMap<String, HashMap<String, String>> loanpayment() { return getRegex(new Loanpayment()); }

    @RequestMapping(value = "/property", produces = "application/json")
    public HashMap<String, HashMap<String, String>> property() { return getRegex(new Propertydetail()); }

    @RequestMapping(value = "/criteria", produces = "application/json")
    public HashMap<String, HashMap<String, String>> criteria() { return getRegex(new Criteria()); }

    @RequestMapping(value = "/constraint", produces = "application/json")
    public HashMap<String, HashMap<String, String>> constraint() { return getRegex(new Constraint1()); }

    @RequestMapping(value = "/propertystatus", produces = "application/json")
    public HashMap<String, HashMap<String, String>> propertystatus() { return getRegex(new Propertystatus()); }

    public static <T> HashMap<String, HashMap<String, String>> getRegex(T t) {
        try {
            Class<? extends Object> aClass = t.getClass();
            HashMap<String, HashMap<String, String>> regex = new HashMap<>();

            for (Field field : aClass.getDeclaredFields()) {

                Annotation[] annotations = field.getDeclaredAnnotations();

                for (Annotation annotation : annotations) {

                    if (annotation instanceof Pattern) {
                        Pattern myAnnotation = (Pattern) annotation;
                        HashMap<String, String> map = new HashMap<>();
                        map.put("regex", myAnnotation.regexp());
                        map.put("message", myAnnotation.message());
                        regex.put(field.getName(), map);
                    }

                    if (annotation instanceof RegexPattern) {
                        RegexPattern myAnnotation2 = (RegexPattern) annotation;
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("regex", myAnnotation2.regexp());
                        map2.put("message", myAnnotation2.message());
                        regex.put(field.getName(), map2);
                    }
                }
            }
            return regex;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
