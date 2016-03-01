/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import hibernate.dataobjects.UsersGroupsId;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.GroupsService;
import services.interfaces.UsersService;

/**
 *
 * @author Alayna
 */
@ManagedBean(name = "GroupBean")
@ViewScoped
public class GroupBean {
    @Autowired
    transient GroupsService groupsService;
    
    @Autowired
    transient UsersService usersService;
    
    private int gid;
    private Groups group;
    private Boolean updateFlag;
    private Boolean isLeader;
    
   @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
                
        if(!FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("groupCreate"))
            setGID();
    }
    
    private void setGID(){
        String tempId=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gid");
        gid=Integer.parseInt(tempId);
        group=groupsService.findGroupById(gid);
        updateFlag=false;
            LoginBean user = (LoginBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
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
    
    public int getGid(){
        return gid;
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
    
    public void setUsersService(UsersService usersService){
        this.usersService=usersService;
    }
    
    public UsersService getUsersService(){
        return usersService;
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
    
    /*to be used in the group creation page, checks for existing group first, remove if needed @yawei*/
    public void addGroup(String groupName, String description){
        RequestContext context = RequestContext.getCurrentInstance();
      //if username exists, it will show a dialog on creation page
      if(groupsService.groupCheck(groupName)){
         context.execute("PF('groupFailDlg').show()");
      } else {
        group=new Groups();
        group.setGroupname(groupName);
        group.setDescription(description);
        groupsService.addGroup(group);
       
        List<Groups> groups=groupsService.findGroupsByName(groupName);
        
        if (groups.size()>1){//if there is more than one group returned, sort by id
           //DON'T convert to lambda experession, it will break the bean
            groups.sort(new Comparator<Groups>() {
                @Override
                public int compare(Groups g1, Groups g2) {
                    return g1.getGroupId().compareTo(g2.getGroupId());
                }
            });
            
        }
      
        //set group to the group with the highest id
        group=groups.get(groups.size()-1);
        
        if(group!=null){
            context.execute("PF('groupSuccessDlg').show()");
            gid=group.getGroupId();
            isLeader=true;
            LoginBean lv=(LoginBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
            addMember(lv.getUserId());
        }    
        else
           context.execute("PF('groupFailDlg').show()"); 
      }
    }
    
    public void deleteGroup() throws IOException {
        int gid = group.getGroupId();
        System.out.println(gid);
        groupsService.deleteGroup(gid);
        FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
    }


    public void addMember(int userID){
        Users member=usersService.getUserById(userID);
        UsersGroupsId ugid=new UsersGroupsId(userID, gid);
        UsersGroups ug;
       if(group.getUsersGroupses().isEmpty())
            ug=new UsersGroups(ugid, group, member, true);
        else
            ug=new UsersGroups(ugid, group, member, false); 
       groupsService.addMember(ug);
    }
        /**
     * Creates a new instance of GroupProfileView
     */
    public GroupBean() {
        
    }

    
    
}
