/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import services.interfaces.GroupsService;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name="UsersView")
@SessionScoped
public class UsersView implements Serializable {
    
    @ManagedProperty("#{UsersService}")
    private UsersService usersService;
    
    private Users user = new Users();
    private List<Users> users = new ArrayList<>();
    
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
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Searching...", "Search in progress"));
    }
    
    public void userLogin(String username, String password) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message;
        boolean loggedIn;
         
        if(username == null || password == null){
            loggedIn=false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
        }
        else{
            user = usersService.getUserByName(username);

            if(user != null){
                HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("name", user.getUsername());
                session.setAttribute("userId", user.getUserId());
                loggedIn = true;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
            }
            else {
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials");
            }
        } 
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }
}
