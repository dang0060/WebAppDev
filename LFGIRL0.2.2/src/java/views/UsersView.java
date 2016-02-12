/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import hibernate.dataobjects.Users;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name="UsersView")
@ViewScoped
public class UsersView {
    
    @ManagedProperty("#{UsersService}")
    private UsersService usersService;
    
    private Users user = new Users();
    private List<Users> users = new ArrayList<Users>();
    
    public void setUsersService(UsersService userService) {
        this.usersService = userService;
    }
    
    public UsersService getUsersService() {
        return usersService;
    }
    
    public Users getUser() {
        return user;
    }
    
    public void setUser(Users user) {
        this.user = user;
    }
    
    public List<Users> getUsers() {
        return users;
    }
    
    public void setUsers(List<Users> users) {
        this.users = users;
    }
    
    public void userNameSearch(String username) {
        users = usersService.getUsersByName(username);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Search in progress"));
    }
}
