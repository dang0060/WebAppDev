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
    }
}
