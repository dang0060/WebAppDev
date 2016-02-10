/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restcontrollers;

import hibernate.dataobjects.Groups;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import dao.GroupsDAO;
import hibernate.dataobjects.Groups;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Protostar
 */
@RestController
public class GroupsRestController {
    
    @Autowired
    private GroupsDAO groupsDAO;
    
    @RequestMapping(value = "/groups/", method = RequestMethod.GET)
    public ResponseEntity<List<Groups>> listAllUsers() {
        List<Groups> users = groupsDAO.listGroups();
        if (users.isEmpty()) {
            return new ResponseEntity<List<Groups>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Groups>>(users, HttpStatus.OK);
        List<Groups> groups = groupsDAO.listGroups();
        if (groups.isEmpty()) {
            return new ResponseEntity<List<Groups>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Groups>>(groups, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/groups/groupid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Groups> getGroupById(@PathVariable("id") int id) {
        System.out.println("Fetching Group with id: " + id);
        Groups group = groupsDAO.findGroupById(id);
        if (group == null) {
            System.out.println("Group with id " + id + " not found");
            return new ResponseEntity<Groups>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Groups>(group, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/groups/groupname/{groupName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Groups> getGroupByGroupName(@PathVariable("groupName") String groupName) {
        System.out.println("Fetching Group with name: " + groupName);
        Groups group = groupsDAO.findGroupByName(groupName);
        if (group == null) {
            System.out.println("Group with name " + groupName + " not found");
            return new ResponseEntity<Groups>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Groups>(group, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/groups/groupdescription/{groupDesc}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Groups>> getGroupByGroupDesc(@PathVariable("groupDesc") String groupDesc) {
        System.out.println("Fetching grou with description like: " + groupDesc);
        List<Groups> group = groupsDAO.findGroupByDesc(groupDesc);
        if (group == null) {
            System.out.println("Group description " + groupDesc + " not recognized");
            return new ResponseEntity<List<Groups>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Groups>>(group, HttpStatus.OK);
    }
}
