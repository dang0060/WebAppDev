/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.UsersGroups;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import services.interfaces.GroupsService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name="GroupsView")
@ViewScoped
public class GroupsView {
    
    @ManagedProperty("#{GroupsService}")
    private GroupsService groupsService;
    
    private List<Groups> groups = new ArrayList<>();
    
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
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session.getAttribute("userId") != null) {
            getGroupsAsList((int)session.getAttribute("userId"));
        }
        System.out.println("Here is the session");
    }
}
