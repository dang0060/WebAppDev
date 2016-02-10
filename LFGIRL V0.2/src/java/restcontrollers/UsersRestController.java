/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restcontrollers;

import dao.UsersDAO;
import hibernate.dataobjects.Users;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Protostar
 */

@RestController
public class UsersRestController {
    
    @Autowired
    private UsersDAO usersDAO;
    
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ResponseEntity<List<Users>> listAllUsers() {
        List<Users> users = usersDAO.listUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<List<Users>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users/userid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUserById(@PathVariable("id") int id) {
        System.out.println("Fetching User with id: " + id);
        Users user = usersDAO.findUserById(id);
        if (user == null) {
            System.out.println("User with id: " + id + " not found");
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Users>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users/username/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUserByUserName(@PathVariable("userName") String userName) {
        System.out.println("Fetching User with id: " + userName);
        Users user = usersDAO.findUserByUserName(userName);
        if (user == null) {
            System.out.println("User with user name: " + userName + " not found");
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Users>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@RequestBody Users u, UriComponentsBuilder ucBuilder) {
        if(usersDAO.userCheck(u.getUsername())) {
            System.out.println("A User with name: " + u.getUsername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        
        usersDAO.addUser(u);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(u.getUserId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
}
