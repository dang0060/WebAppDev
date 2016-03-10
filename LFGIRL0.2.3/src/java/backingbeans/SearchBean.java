/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
@ManagedBean(name="SearchBean" ,eager=true)
@RequestScoped
public class SearchBean {
    
    @Autowired
    transient GroupsService groupsService;
    
    @Autowired
    transient UsersService usersService;
    
    private List<Groups> groups = new ArrayList<>();
    private List<Users> users = new ArrayList<>();
    private LoginBean user = (LoginBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean"); //check login bean for user session info
    private boolean loggedIn = false; //user login status
    private FacesContext context = FacesContext.getCurrentInstance(); //for I18n of yes and no strings
    private ResourceBundle msg = context.getApplication().evaluateExpressionGet(context,"#{msg}", ResourceBundle.class); //for I18n of yes and no strings
    
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
     * @return the users
     */
    public List<Users> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Users> users) {
        this.users = users;
    }
    
    public void userNameSearch(String username) {
        setUsers(usersService.getUsersByName(username));
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Searching...", "Search in progress"));
    }
    
    public void searchForGroup(String searchTerm) {
        setGroups(groupsService.findGroupsByName(searchTerm));
        if (getGroups().isEmpty()) {
            setGroups(groupsService.findGroupByDescription(searchTerm));
        }
    }
      
     //get the name of the group leader @yawei 
     public String searchGroupLeader(int gid) {
        return groupsService.findGroupLeaedr(gid);
    }
    
    //indicates if the current user is the leader for a group @yawei
    public String returnLeaderStatus(int gid){
      //put yes and no under I18n   
      if(user.getUserName().equals(groupsService.findGroupLeaedr(gid))){
        return msg.getString("yesButton");
      }
       return msg.getString("noButton");    
    }
    
    //display the column if user is logged in @yawei
    public boolean displayJoinedColumn(){
      if(user!=null && user.getUserName()!= null){ 
          loggedIn = true;
          return true;      
    }   
      return false;
    }
   
    //show if user has joined a group @yawei
    public String JoinedGroup(int gid){    
      Groups group=groupsService.findGroupById(gid);
      Boolean isLeader = false;
      Boolean isMember = false; 
      //check to see if a user is a member or a leader in the group
        if(user!=null){
            Set<UsersGroups> members = group.getUsersGroupses();
            for(UsersGroups member:members){
                if(member.getUsers().getUserId()==user.getUserId()){
                   // isLeader=member.getIsLeader();
                    isLeader = member.getIsLeader();
                    isMember = true; 
                }
            }
        }
        if(isLeader==null)
         isLeader = false;
        
      //default is no if not logged in, even is not showing 
      if(!loggedIn){
        return msg.getString("noButton");  
        } 
      //in the group if is member or leader
      if(isMember || isLeader){
        return  msg.getString("yesButton");    
      }
      //if not a member or leader, than has not joined
      return msg.getString("noButton");                
    }
}
