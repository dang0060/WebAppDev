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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Users user;
    private int uid;
    private boolean isUser;

    @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        
        setUID();

    }

    private void setUID(){
        LoginBean lv = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
        String tempId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uid");
        isUser=false;
        
    
        //pages that can be accessed by everyone
        if(FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("userProfile")){
            if(tempId!=null){//set uid to parameter value
                uid=Integer.parseInt(tempId);
                if(lv!=null && lv.getUserId()==uid)
                    isUser=true;
            }
            //if no param, but logged in, get user's own page
            else if(lv.getUserName()!=null){
                uid=lv.getUserId();
                isUser=true;
            }//not logged in with no param, redirect
            else{
                String groupPage=String.format("homepage.xhtml");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(groupPage);
                } catch (IOException ex) {
                    Logger.getLogger(GroupBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
        }
        else{//for pages that can only be accessed if logged in eg. my groups, about me
            if(lv!=null){
                uid=lv.getUserId();
                isUser=true;
            }//not logged in, redirect
            else{
                String groupPage=String.format("homepage.xhtml");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(groupPage);
                    return;
                } catch (IOException ex) {
                    Logger.getLogger(GroupBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        user=usersService.getUserById(uid);
        setGroups(groupsService.findGroupsByUserId(user.getUserId()));
        setUserInfo(user.getUserInfo());
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

    public boolean getIsUser(){
        return isUser;
    }
    public String getUserName(){
        return user.getUsername();
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
            setUserInfo(usersService.getUserById(uid).getUserInfo());
        }
        String profilePage=String.format("userProfile.xhtml?uid=%d", uid);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(profilePage);
        } catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteUser() {
        try {
            usersService.deleteUser(uid);
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
