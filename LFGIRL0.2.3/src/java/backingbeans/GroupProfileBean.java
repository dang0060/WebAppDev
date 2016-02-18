/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Groups;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.GroupsService;

/**
 *
 * @author Alayna
 */
@ManagedBean(name = "groupProfile")
@ViewScoped
public class GroupProfileBean {
    @Autowired
    transient GroupsService groupsService;
    
    int gid;
    Groups group;
    
    @PostConstruct // this will execute init() after id is injected
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        
        String tempId=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gid");
        gid=Integer.parseInt(tempId);
        group=groupsService.findGroupById(gid);
    }
    
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
    public void setGid(int gid){
        this.gid=gid;
    }
    public int getGid(){
        return gid;
    }
    
    public void setGroup(Groups group){
        this.group=group;
    }
    
    public Groups getGroup(){
        return group;
    }
    
    public void setGroupsService(GroupsService groupService){
        this.groupsService=groupService;
    }
    
    public GroupsService getGroupService(){
        return groupsService;
    }
    
    public String getGroupName(){
        return group.getGroupname();
    }
    
    public String getDescription(){
        return group.getDescription();
    }
    /**
     * Creates a new instance of GroupProfileView
     */
    public GroupProfileBean() {
        
    }

    
    
}
