package com.nhdasystem.controller;

import com.nhdasystem.dao.*;
import com.nhdasystem.entity.*;
//import lk.earth.bitproject.dao.*;
//import lk.earth.bitproject.entity.*;
import com.nhdasystem.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private UserDao userdao;

    @Autowired
    private RoleDao roledao;

    @Autowired
    private GenderDao genderdao;

    @Autowired
    private BranchDao branchdao;

    @Autowired
    private DesignationDao designationdao;

    @Autowired
    private CivilstatusDao civilstatusdao;

    @Autowired
    private EmployeestatusDao employeestatusdao;

    @Autowired
    private EmployeeDao employeedao;

    @Autowired
    private UserstatusDao userstatusdao;

    @Autowired
    private SessionDao sessiondao;

    @Autowired
    private SessionstatusDao sessionstatusdao;

    @Autowired
    private ModuleDao moduledao;

    @RequestMapping(value = "/session",params = {"username","password"}, method = RequestMethod.POST, produces = "application/json")
    public Sessionlog getSession( @RequestParam("username") String username, @RequestParam("password") String password) {

        User user = userdao.findByUsername(username);
        if(user!=null){
            System.out.println("----------------------------------------------------------");
            System.out.println(user.getSalt());
            System.out.println(AuthProvider.getHash(password+user.getSalt()));
            if(user.getPassword().equals(AuthProvider.getHash(password+user.getSalt()))){
                Sessionlog sessionlog = new Sessionlog();
                sessionlog.setUserId(user);
                sessionlog.setSessionstatusId(sessionstatusdao.findByName("Login"));
                sessionlog.setLogintime(LocalDateTime.now());
                sessiondao.save(sessionlog);
                return sessionlog;
            }
            else return null;
        }
        else  return null;

    }


    @RequestMapping(value = "/session",method = RequestMethod.PUT, produces = "application/json")
    public String logout(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@CookieValue(value="sessionid", required=false) Integer sessionid ) {

        Sessionlog sessionlog = sessiondao.getOne(sessionid);
        if(sessionlog!=null){

            sessionlog.setSessionstatusId(sessionstatusdao.findByName("Logout"));
            sessionlog.setLogouttime(LocalDateTime.now());
            sessiondao.save(sessionlog);

            return "You are logout successfully";
        }
        else  return null;

    }




    @Transactional
    @RequestMapping(value = "/config",params = {"fullname","password"}, method = RequestMethod.POST, produces = "application/json")
    public String config( @RequestParam("fullname") String fullname, @RequestParam("password") String password) {

        User adminuser = userdao.findByUsername("admin");
        if(adminuser!=null)
            return "Already Configured";
        else {
            Employee employee = new Employee();
            User user = new User();

            try {

                Gender gender = new Gender();
                gender.setName("Male");
                genderdao.save(gender);

                System.out.println("---gender-----");

                Civilstatus civilstatus = new Civilstatus();
                civilstatus.setName("Married");
                civilstatusdao.save(civilstatus);

                Employeestatus employeestatus = new Employeestatus();
                employeestatus.setName("Operational");
                employeestatusdao.save(employeestatus);

                Designation designation = new Designation();
                designation.setName("Administrator");
                designationdao.save(designation);

                System.out.println("---designation-----");

                Branch branch = new Branch();
                branch.setName("Colombo-N");
                branchdao.save(branch);

                System.out.println("---branch-----");

                employee.setCallingname("Admin");
                employee.setFullname(fullname);
                employee.setGenderId(gender);
                employee.setCivilstatusId(civilstatus);
                employee.setEmployeestatusId(employeestatus);
                employee.setDesignationId(designation);
                employee.setBranchId(branch);
                employee.setNic("000000000V");


                Userstatus userstatus = new Userstatus();
                userstatus.setName("Operational");
                userstatusdao.save(userstatus);


                System.out.println("---userstatus-----");

                Sessionstatus sessionstatus = new Sessionstatus();
                sessionstatus.setName("Login");
                sessionstatusdao.save(sessionstatus);


                Sessionstatus sessionstatus2 = new Sessionstatus();
                sessionstatus2.setName("Logout");
                sessionstatusdao.save(sessionstatus2);

                System.out.println("---sessstaus-----");

                user.setEmployeeId(employee);
                user.setEmployeeCreatedId(employee);
                user.setUserstatusId(userstatus);
                user.setUsername("admin");
                user.setPassword(password);
                user.setSalt(AuthProvider.generateSalt());
                user.setPassword(AuthProvider.getHash(user.getPassword()+user.getSalt()));
                System.out.println(user.getSalt());
                System.out.println(AuthProvider.getHash(password+user.getSalt()));
                System.out.println(user.getPassword());
                user.setDocreation(LocalDate.now());
                user.setDescription("RootUser");
                List<User> userlist = new ArrayList();
                userlist.add(user);

                System.out.println("---password-----");

                employee.setUserList(userlist);
                employeedao.save(employee);

                List<Module> modules = new ArrayList<>();

                for (ModuleList m:ModuleList.values()
                        ) {
                    Module mod = new Module();
                    mod.setName(m.toString());
                    modules.add(mod);

                }

                moduledao.saveAll(modules);

                System.out.println("---employee-----");

                Role admin1 = new Role();
                admin1.setName("Admin1");
                roledao.save(admin1);

                Role admin2 = new Role();
                admin2.setName("Admin2");
                roledao.save(admin2);


                return "Successfully Configured";
            } catch (Exception ex) {
                return "Failed to configure as " + ex.getMessage();
            }

        }}}




