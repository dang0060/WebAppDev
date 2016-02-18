/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.UserInfo;
import hibernate.dataobjects.Users;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.GroupsService;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name = "UserBean")
@RequestScoped
public class UserBean {

    @Autowired
    transient GroupsService groupsService;

    @Autowired
    transient UsersService usersService;

    private UserInfo userInfo = new UserInfo();
    private List<Groups> groups = new ArrayList<>();

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

    /**
     * @return the groups
     */
    public List<Groups> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

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

    public void displayMyGroups() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean lv = (LoginBean) facesContext.getExternalContext().getSessionMap().get("LoginBean");
        if (lv != null && lv.getUserName() != null) {
            setGroups(groupsService.findGroupsByUserId(lv.getUserId()));
        } else {
            System.out.println("No groups to display");
        }
    }

    public void displayUserInfo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean lv = (LoginBean) facesContext.getExternalContext().getSessionMap().get("LoginBean");
        if (lv != null && lv.getUserName() != null) {
            setUserInfo(usersService.getUserById(lv.getUserId()).getUserInfo());
            System.out.println("Show me the money");
        }
    }

    public void updateUserInfo(String firstname, String lastname, String address, String city, String postalcode) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean lv = (LoginBean) facesContext.getExternalContext().getSessionMap().get("LoginBean");
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
            setUserInfo(usersService.getUserById(lv.getUserId()).getUserInfo());
        }
    }

    public void deleteUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean lv = (LoginBean) facesContext.getExternalContext().getSessionMap().get("LoginBean");
        if (lv != null && lv.getUserName() != null) {
            try {
                usersService.deleteUser(lv.getUserId());
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
