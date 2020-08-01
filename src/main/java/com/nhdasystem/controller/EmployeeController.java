package com.nhdasystem.controller;

import com.nhdasystem.dao.DesignationDao;
import com.nhdasystem.dao.EmployeeDao;
import com.nhdasystem.dao.EmployeestatusDao;
import com.nhdasystem.entity.Employee;
import com.nhdasystem.entity.Employeestatus;
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
public class EmployeeController {

    @Autowired
    private EmployeeDao dao;

    @Autowired
    private DesignationDao daoDesignation;

    @Autowired
    private EmployeestatusDao daoEmployeestatus;



    @RequestMapping(value = "/employees/list", method = RequestMethod.GET, produces = "application/json")
    public List<Employee> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }


    @RequestMapping(value = "/employees/list/withoutusers",  method = RequestMethod.GET, produces = "application/json")
    public List<Employee> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
        return dao.listWithoutUsers();
        }
        return null;
    }

    @RequestMapping(value = "/employees/list/withuseraccount",  method = RequestMethod.GET, produces = "application/json")
    public List<Employee> listwithuseraccount(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
        return dao.listWithUseraccount();
        }
        return null;
    }




    @RequestMapping(value = "/employees", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Employee> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/employees", params = {"page", "size","name","nic","designationid","employeestatusid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Employee> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("nic") String nic, @RequestParam("designationid") Integer designationid, @RequestParam("employeestatusid") Integer employeestatusid) {

       // System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {

            List<Employee> employees = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Employee> employeestream = employees.stream();
            employeestream = employeestream.filter(e -> !(e.getCallingname().equals("Admin")));

            if (designationid != null)
                employeestream = employeestream.filter(e -> e.getDesignationId().equals(daoDesignation.getOne(designationid)));
            if (employeestatusid != null)
                employeestream = employeestream.filter(e -> e.getEmployeestatusId().equals(daoEmployeestatus.getOne(employeestatusid)));
            employeestream = employeestream.filter(e -> e.getNic().startsWith(nic));
            employeestream = employeestream.filter(e -> e.getFullname().contains(name));

            List<Employee> employees2 = employeestream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < employees2.size() ? start + size : employees2.size();
            Page<Employee> emppage = new PageImpl<>(employees2.subList(start, end), PageRequest.of(page, size), employees2.size());

            return emppage;
        }

        return null;

    }


    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Employee employee) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.INSERT)) {
            Employee empnic = dao.findByNIC(employee.getNic());
            Employee empnumber = dao.findByNumber(employee.getNumber());
            if (empnic != null)
                return "Error-Validation : NIC Exists";
            else if (empnumber != null)
                return "Error-Validation : Number Exists";
            else
                try {
                    dao.save(employee);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/employees", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Employee employee) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.UPDATE)) {
        Employee emp = dao.findByNIC(employee.getNic());
        if(emp==null || emp.getId()==employee.getId()) {
            try {
                dao.save(employee);
                return "0";
            }
            catch(Exception e) {
                return "Error-Updating : "+e.getMessage();
            }
        }
        else {  return "Error-Updating : NIC Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/employees", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Employee employee ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEE,AuthProvider.DELETE)) {
            Employee emp = dao.findByNIC(employee.getNic());
        try {
            Employeestatus status = new Employeestatus(4);
            emp.setEmployeestatusId(status);
            dao.save(emp);
            //dao.delete(dao.getOne(employee.getId()));
            return "0";
        }
        catch(Exception e) {
            return "Error-Deleting : "+e.getMessage();
        }
    }
        return "Error-Deleting : You have no Permission";

    }

    @RequestMapping(value = "/employees/empno", method = RequestMethod.GET, produces = "application/json")
    public String lastEmpNumber(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            String empno = dao.lastEmpno();
            //System.out.println(clicode);
            Integer EmpNo = Integer.parseInt(empno);
            String employeeno="";
            if(EmpNo<9)
                employeeno = "0000"+(EmpNo+1);
            else if(EmpNo<99)
                employeeno = "000"+(EmpNo+1);
            else if(EmpNo<999)
                employeeno = "00"+(EmpNo+1);
            else if(EmpNo<9999)
                employeeno = "0"+ (EmpNo+1);

            return "{\"no\":"+"\""+employeeno+"\"}";
        }
        return null;
    }


}
