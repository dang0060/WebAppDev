/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.UsersGroups;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.GroupsService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name="GroupsView")
@ViewScoped
public class GroupsView {
    
    @Autowired
    transient GroupsService groupsService;
    
    private List<Groups> groups = new ArrayList<>();
    private Groups group = new Groups();
    
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
    
    public void setGroupsService(GroupsService groupsService) {
        this.groupsService = groupsService;
    }
    
    public GroupsService getGroupsService() {
        return groupsService;
    }
    
    public List<Groups> getGroups() {
        return groups;
    }
    
    public void getGroupsAsList(int userId) {
        groups.clear();
        groups = groupsService.findGroupsByUserId(userId);
    }
    
    public void searchForGroup(String searchTerm) {
        groups.clear();
        groups = groupsService.findGroupsByName(searchTerm);
        if (groups.isEmpty()) {
            groups = groupsService.findGroupByDescription(searchTerm);
        }
    }
    
    public void displayGroups() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginView lv = (LoginView) facesContext.getExternalContext().getSessionMap().get("LoginView");
        if (lv != null && lv.getUserName() != null) {
            getGroupsAsList(lv.getUserId());
        }
        else
        {
            System.out.println("No groups to display");
        }
        
    }
    
    /*to be used in the group creation page, checks for existing group first, remove if needed @yawei*/
    public void addGroup(String groupName, String description){
        RequestContext context = RequestContext.getCurrentInstance();
      /*if username exists, it will show a dialog on creation page*/
      if(groupsService.groupCheck(groupName)){
         context.execute("PF('groupFailDlg').show()");
      } else {
        group.setGroupname(groupName);
        group.setDescription(description);
        groupsService.addGroup(group);
        context.execute("PF('groupSuccessDlg').show()");
      }
    }
}
