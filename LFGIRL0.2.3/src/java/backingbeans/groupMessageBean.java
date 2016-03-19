/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.GroupMessages;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.GroupsService;
import services.interfaces.GroupMessageService;

/**
 *
 * @author Ya
 */
@ManagedBean(name = "groupMessageBean", eager=true)
@ViewScoped
public class groupMessageBean implements Serializable {    
    @Autowired
    transient GroupsService groupsService;
    
    @Autowired
    transient GroupMessageService groupMessageService;
    
    private String selectedUserName;
    private List<Users> groupMembers;
    private List<GroupMessages> groupMessages;
    private GroupMessages selectedMessage;
    private String selMessageTitle;
    private String selMessageContent;
    private int selMessageId; 
    private int selectedUserID;
    private int gid;
    private int uid;
    
    @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        
        LoginBean lbean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
        String tempId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gid");
        if(tempId != null)
            gid=Integer.parseInt(tempId);
        if(lbean != null)
            uid = lbean.getUserId();
         
              
     }
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
     /**
     * @return the selectedUser
     */
    public int getSelectedUserID() {
        return selectedUserID;
    }

    /**
     * @param selectedUserID the selectedUserID to set
     */
    public void setSelectedUserID(int selectedUserID) {
        this.selectedUserID = selectedUserID;
    }
    
     /**
     * @return the selMessageId
     */
    public int getSelMessageId() {
        return selMessageId;
    }

    /**
     * @param selMessageId the selMessageId to set
     */
    public void setSelMessageId(int selMessageId) {
        this.selMessageId = selMessageId;
    }
     /**
     * @return the selMessageTitle
     */
    public String getSelMessageTitle() {
        return selMessageTitle;
    }

    /**
     * @param selMessageTitle the selMessageTitle to set
     */
    public void setSelMessageTitle(String selMessageTitle) {
        this.selMessageTitle = selMessageTitle;
    }
    
    /**
     * @return the selMessageContent
     */
    public String getSelMessageContent() {
        return selMessageContent;
    }

    /**
     * @param selMessageContent the selMessageContent to set
     */
    public void setSelMessageContent(String selMessageContent) {
        this.selMessageContent = selMessageContent;
    }
    
     /**
     * @return the selectedMessage
     */
    public GroupMessages getSelectedMessage() {
        return selectedMessage;
    }

    /**
     * @param selectedMessage the selectedMessage to set
     */
    public void setSelectedMessage(GroupMessages selectedMessage) {
        this.selectedMessage = selectedMessage;
    }    
     /**
     * @return the gid
     */
    public int getGid() {
        return gid;
    }

    /**
     * @param gid the gid to set
     */
    public void setGid(int gid) {
        this.gid = gid;
    }
    
    /**
     * @return the uid
     */
    public int getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(int uid) {
        this.uid = uid;
    }
    
     /**
     * @return the selectedUser
     */
    public String getSelectedUserName() {
        return selectedUserName;
    }

    /**
     * @param selectedUserName the selectedUserName to set
     */
    public void setSelectedUserName(String selectedUserName) {
        this.selectedUserName = selectedUserName;
    }
    
    /**
     * @return the groupMembers
     */
    public List<Users> getGroupMembers() {
        return groupMembers;
    }

    /**
     * @param groupMembers the groupMembers to set
     */
    public void setGroupMembers(List<Users> groupMembers) {
        this.groupMembers = groupMembers;
    }
    
    /**
     * @return the groupMessages
     */
    public List<GroupMessages> getGroupMessages() {
        return groupMessages;
    }
    
    public void testUidGid(){
       System.out.println(uid);
       System.out.println(gid);
    }

    /**
     * @param groupMessages the groupMessages to set
     */
    public void setGroupMessages(List<GroupMessages> groupMessages) {
        this.groupMessages = groupMessages;
    }
    
    /*get member of a group @yawei*/
    public List<Users> findGroupMembers(int gid){   
      setGroupMembers(groupsService.findGroupMembers(gid));
      return groupMembers;
    }
    
    /*setup SelectedUserId and SelecteduserName using params*/
    public void setUpSelectedUser(){      
     String tempselectedUserName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedUserName");
     String tempselectedUserID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedUserID");
     String tempselectedGid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("gid");
     
     if((tempselectedUserID != null) && (tempselectedUserName!= null) && (tempselectedGid != null)){
        selectedUserID=Integer.parseInt(tempselectedUserID);
        gid =Integer.parseInt(tempselectedGid);
        selectedUserName = tempselectedUserName;
     } 
    }
    
    public void setUpSelectedMessage(){
       String tempMesTitle = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("messageTitle");
       String tempMesContent = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("messageContent");
       String tempMesId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("messagId");
       
       if(tempMesTitle != null && tempMesContent != null &&tempMesId != null ){
         selMessageTitle = tempMesTitle;
         selMessageContent = tempMesContent;
         selMessageId = Integer.parseInt(tempMesId);
         
         groupMessageService.updateReadStatus(selMessageId);
       }
           
    }
  
 //send message to the target user
  public void sendMessage (int gid, int senderId, String messageTitle, String messageContent){
   String date;
   GroupMessages newMessage = new GroupMessages();
   FacesContext fc = FacesContext.getCurrentInstance();
   RequestContext context = RequestContext.getCurrentInstance();
   boolean readStatus = false; //defualt status is false, as the message has not been read yet 
   FacesMessage message;
   
   //get today's date
     Date dNow = new Date( );
     SimpleDateFormat ft = 
     new SimpleDateFormat ("yyyy.MM.dd");
     date =  ft.format(dNow);
     
     //setup the groupMessage object's attributes
     newMessage.setGroups(groupsService.findGroupById(gid));
     newMessage.setMessageContent(messageContent);
     newMessage.setMessageDate(date);
     newMessage.setMessageReadStatus(readStatus);
     newMessage.setMessageTitle(messageTitle);
     newMessage.setReceiverUserId(selectedUserID);
     newMessage.setSenderUserId(senderId);
     groupMessageService.addMessage(newMessage); //call service to store message in database
     
     //display message after message is sent
     message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent to",selectedUserName);
     context.execute("PF('messageDialog').hide()"); //close the message dialog after sending
     fc.addMessage(null, message);
  }
  
  public List<GroupMessages> listMessages(){
     setGroupMessages(groupMessageService.listMessages(gid, uid));
     return groupMessages;
  }
}
