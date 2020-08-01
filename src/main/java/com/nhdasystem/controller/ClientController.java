package com.nhdasystem.controller;

import com.nhdasystem.dao.ClientDao;
import com.nhdasystem.dao.ClientDao;
import com.nhdasystem.dao.ClientstatusDao;
import com.nhdasystem.dao.GndivisionDao;
import com.nhdasystem.entity.Client;
//import com.nhdasystem.entity.Client;
import com.nhdasystem.entity.Client;
import com.nhdasystem.entity.Cstatus;
import com.nhdasystem.entity.Purposeofloan;
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
public class ClientController {

    @Autowired
    private ClientDao dao;

    @Autowired
    private ClientstatusDao daoClientstatus;

    @Autowired
    private GndivisionDao daoGndivision;

    @RequestMapping(value = "/clients/list", method = RequestMethod.GET, produces = "application/json")
    public List<Client> list(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.list();
        }
        return null;
    }

    @RequestMapping(value = "/clients/list/withoutloans",  method = RequestMethod.GET, produces = "application/json")
    public List<Client> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return dao.listWithoutLoans();
        }
        return null;
    }

    @RequestMapping(value = "/clients", params = {"page", "size"}, method = RequestMethod.GET, produces =
            "application/json")
    public Page<Client> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.CLIENT,AuthProvider.SELECT)) {
            return dao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/clients", params = {"page", "size","name","nic","gndivisionId","cstatusId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Client> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("nic") String nic, @RequestParam("gndivisionId") Integer gndivisionId, @RequestParam("cstatusId") Integer cstatusId) {

        // System.out.println(name+"-"+nic+"-"+gndivisionId+"-"+cstatusId);


        if(AuthProvider.isAuthorized(username,password, ModuleList.CLIENT,AuthProvider.SELECT)) {

            List<Client> clients = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Client> clientstream = clients.stream();
           // clientstream = clientstream.filter(c -> !(c.getCallingname().equals("Admin")));

            if (gndivisionId != null)
                clientstream = clientstream.filter(c -> c.getGndivisionId().equals(daoGndivision.getOne(gndivisionId)));
            if (cstatusId != null)
                clientstream = clientstream.filter(c -> c.getCstatusId().equals(daoClientstatus.getOne(cstatusId)));

            clientstream = clientstream.filter(c -> c.getNic().startsWith(nic));
            clientstream = clientstream.filter(c -> c.getName().contains(name));

            List<Client> clientstream2 = clientstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < clientstream2.size() ? start + size : clientstream2.size();
            Page<Client> clientpg = new PageImpl<>(clientstream2.subList(start, end), PageRequest.of(page, size), clientstream2.size());

            return clientpg;
        }

        return null;

    }



    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password",
            required=false) String password, @Validated @RequestBody Client client) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CLIENT,AuthProvider.INSERT)) {
            Client clinic = dao.findByNIC(client.getNic());
//            Client clicode = dao.findByCode(client.getCode());
            if (clinic != null)
                return "Error-Validation : NIC Exists";
            else
                try {
                    dao.save(client);
                    return "0";
                } catch (Exception c) {
                    return "Error-Saving : " + c.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/clients", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@Validated @RequestBody Client client) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CLIENT,AuthProvider.UPDATE)) {
            Client cli = dao.findByCode(client.getCode());
            if(cli==null || cli.getId()==client.getId()) {
                try {
                    dao.save(client);
                    return "0";
                }
                catch(Exception c) {
                    return "Error-Updating : "+c.getMessage();
                }
            }
            else {  return "Error-Updating : Code Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/clients", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue
            (value="password", required=false) String password,@RequestBody Client client ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.CLIENT,AuthProvider.DELETE)) {
            Client cli = dao.findByCode(client.getCode());
            try {
                Cstatus status = new Cstatus(2);
                cli.setCstatusId(status);
                dao.save(cli);
               // dao.delete(dao.getOne(client.getId()));
                return "0";
            }
            catch(Exception c) {
                return "Error-Deleting : "+c.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

    @RequestMapping(value = "/clients/clicode", method = RequestMethod.GET, produces = "application/json")
    public String lastClientCode(@CookieValue(value="username") String username, @CookieValue(value="password") String
            password) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.CLIENT,AuthProvider.SELECT)) {
            String clicode = dao.lastClientno();
            //System.out.println(clicode);
            Integer clCode = Integer.parseInt(clicode);
            String clientno="";
            if(clCode<9)
                clientno = "C0000"+(clCode+1);
            else if(clCode<99)
                clientno = "C000"+(clCode+1);
            else if(clCode<999)
                clientno = "C00"+(clCode+1);
            else if(clCode<9999)
                clientno = "C0"+ (clCode+1);

            return "{\"no\":"+"\""+clientno+"\"}";
        }
        return null;
    }




}
