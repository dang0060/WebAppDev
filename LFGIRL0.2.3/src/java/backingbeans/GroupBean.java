/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.UsersGroups;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@ManagedBean(name = "GroupBean")
@ViewScoped
public class GroupBean {
    @Autowired
    transient GroupsService groupsService;
    
    private int gid;
    private Groups group;
    private Boolean updateFlag;
    private Boolean isLeader;
    
    @PostConstruct // this will execute init() after id is injected
    public void init(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        
        String tempId=ec.getRequestParameterMap().get("gid");
        gid=Integer.parseInt(tempId);
        group=groupsService.findGroupById(gid);
        updateFlag=false;
            LoginBean user = (LoginBean)ec.getSessionMap().get("LoginBean");
        if(user!=null){
            Set<UsersGroups> members = group.getUsersGroupses();
            for(UsersGroups member:members){
                if(member.getUsers().getUserId()==user.getUserId()){
                    isLeader=member.getIsLeader();
                }
            }
        }
        if(isLeader==null)
            isLeader=false;
        
        if(isLeader==false && FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("groupEdit")){
            String groupPage=String.format("groupProfile.xhtml?gid=%d", gid);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(groupPage);
            } catch (IOException ex) {
                Logger.getLogger(GroupBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean getIsLeader(){
        return isLeader;
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

    public void setGroupName(String name){
        if(!name.equals(getGroupName())){
            group.setGroupname(name);
            updateFlag=true;
        }
        
    }
    
    public String getDescription(){
        return group.getDescription();
    }
    
    public void setDescription(String description){
        if(!description.equals(getDescription())){
            group.setDescription(description);
            updateFlag=true;
        }
    }
    
    public void editGroup() throws IOException{
        if(updateFlag==true)
            groupsService.updateGroupInfo(group);
        
        String groupPage=String.format("groupProfile.xhtml?gid=%d", gid);
        FacesContext.getCurrentInstance().getExternalContext().redirect(groupPage);
    }
    /**
     * Creates a new instance of GroupProfileView
     */
    public GroupBean() {
        
    }

    
    
}
