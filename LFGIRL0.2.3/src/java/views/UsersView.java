/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;


import hibernate.dataobjects.UserInfo;
import hibernate.dataobjects.Users;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name="UsersView")
@ViewScoped
public class UsersView implements Serializable {
    
    @Autowired
    transient UsersService usersService;
    
    private Users user = new Users();
    private UserInfo userInfo = new UserInfo();
    private List<Users> users = new ArrayList<>();
    
    @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
    }
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
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
    
    public void displayUserInfo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginView lv = (LoginView) facesContext.getExternalContext().getSessionMap().get("LoginView");
        if (lv != null && lv.getUserName() != null) {
            setUserInfo(usersService.getUserById(lv.getUserId()).getUserInfo());
            System.out.println("Show me the money");
        }
    }
    
    public void updateUserInfo(String firstname, String lastname, String address, String city, String postalcode) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginView lv = (LoginView) facesContext.getExternalContext().getSessionMap().get("LoginView");
        if (lv != null && lv.getUserName() != null) {
            Users updatedUser = new Users();
            UserInfo updatedUserInfo = new UserInfo();
            updatedUser.setUserInfo(updatedUserInfo);
            updatedUser.getUserInfo().setFirstName(firstname);
            updatedUser.getUserInfo().setLastName(lastname);
            updatedUser.getUserInfo().setAddress(address);
            updatedUser.getUserInfo().setCity(city);
            updatedUser.getUserInfo().setPostalCode(postalcode);
            updatedUser.setUserId(lv.getUserId());
            usersService.updateUserInfo(updatedUser);
            userInfo = usersService.getUserById(lv.getUserId()).getUserInfo();
        }
    }
    
    /*public void userLogin(String username, String password) {
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
    }*/

    /**
     * @return the userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
