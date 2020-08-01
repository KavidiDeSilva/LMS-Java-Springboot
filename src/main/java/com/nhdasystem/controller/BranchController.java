package com.nhdasystem.controller;

import com.nhdasystem.dao.BranchDao;
import com.nhdasystem.dao.DistrictDao;
import com.nhdasystem.entity.Branch;
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
public class BranchController {
    @Autowired
    private BranchDao dao;
    @Autowired
    private DistrictDao daoDistrict;

    @RequestMapping(value = "/branches/list", method = RequestMethod.GET, produces = "application/json")
    public List<Branch> branches() {
        return dao.list();
    }

    @RequestMapping(value = "/allbranches/list", method = RequestMethod.GET, produces = "application/json")
    public List<Branch> branchesall() {
        return dao.listall();
    }

    @RequestMapping(value = "/branches", params = {"page", "size"}, method = RequestMethod.GET, produces =
            "application/json")
    public Page<Branch> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.BRANCH,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }
    @RequestMapping(value = "/branches", params = {"page", "size","name","districtid"}, method =
            RequestMethod.GET, produces = "application/json")
    public Page<Branch> findAll(@CookieValue(value="username") String username,
                                @CookieValue(value="password") String password, @RequestParam("page") int page,
                                @RequestParam("size") int size,
                                @RequestParam("name") String name, @RequestParam("districtid") Integer districtid) {

       // System.out.println(name+"-"+districtid);
        if(AuthProvider.isAuthorized(username,password, ModuleList.BRANCH,AuthProvider.SELECT)) {

            List<Branch> branches = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Branch> branchstream = branches.stream();
            //branchstream = branchstream.filter(b -> !(b.getCallingname().equals("Admin")));

            if (districtid != null)
                branchstream = branchstream.filter(b -> b.getDistrictId().equals(daoDistrict.getOne(districtid)));

            branchstream = branchstream.filter(b -> b.getName().contains(name));

            List<Branch> branchstream2 = branchstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < branchstream2.size() ? start + size : branchstream2.size();
            Page<Branch> branpage = new PageImpl<>(branchstream2.subList(start, end), PageRequest.of(page, size), branchstream2.size());

            return branpage;
        }
        return null;
    }


    @RequestMapping(value = "/branches", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Branch branch) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.BRANCH,AuthProvider.INSERT)) {
            Branch branname = dao.findByName(branch.getName());
            Branch branNumber =dao.findByNumber(branch.getNumber());
            if (branname != null)
                return "Error-Validation : Name Exists";
            else if (branNumber != null)
                return "Error-Validation : Number Exists";
            else
                try {
                    dao.save(branch);
                    return "0";
                } catch (Exception b) {
                    return "Error-Saving : " + b.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/branches", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Branch branch) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.BRANCH,AuthProvider.UPDATE)) {
            Branch num = dao.findByNumber(branch.getNumber());
            if(num==null || num.getId()==branch.getId()) {
                try {
                    dao.save(branch);
                    return "0";
                }
                catch(Exception b) {
                    return "Error-Updating : "+b.getMessage();
                }
            }
            else {  return "Error-Updating : Number Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }

    @RequestMapping(value = "/branches", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Branch branch ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.BRANCH,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(branch.getId()));
                return "0";
            }
            catch(Exception b) {
                return "Error-Deleting : "+b.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}


