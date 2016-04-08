/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.Conversation;
import hibernate.dataobjects.ConversationMessage;
import hibernate.dataobjects.Users;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import serializer.Autowirer;
import services.interfaces.MessagingService;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@ManagedBean(name = "MessagingBean")
@ViewScoped
public class MessagingBean implements Serializable {
    
    @Autowired
    transient MessagingService messagingService;
    
    @Autowired
    transient UsersService usersService;
    
    private List<Users> allUsers;
    private String targetUser;
    private HashMap<Integer, Conversation> myConversations;
    private List<Conversation> displayConversations;
    private List<ConversationMessage> myMessages;
    private String userName;
    
    @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        
        onLoad();
    }
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
    private void onLoad() {
        LoginBean lbean = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
        setUserName(lbean.getUserName());
        setAllUsers(usersService.listUsers());
        setMyConversations(messagingService.retrieveConversations(lbean.getUserId()));
        ArrayList<Conversation> holdConvo = new ArrayList<>(getMyConversations().values());
        setDisplayConversations(holdConvo);
    }
    
    public void sendMessage(String message) {
        System.out.println(targetUser);
        messagingService.speakConversation(userName, targetUser, message);
    }
    
    public List<String> getUserNames() {
        List<String> userNames = new ArrayList<>();
        for (Users u : getAllUsers()) {
            userNames.add(u.getUsername());
        }
        return userNames;
    }
    
    public String nameCheck(Conversation c) {
        if (c.getUsersByUserStartId().getUsername().equals(userName)) {
            return c.getUsersByUserRecieveId().getUsername();
        } else
            return c.getUsersByUserStartId().getUsername();
    }
    
    public ArrayList<Conversation> caster() {
        ArrayList<Conversation> holdConvo = new ArrayList<>(getMyConversations().values());
        return holdConvo;
    }
    
    public void castMessages(Conversation c) {
        setMyMessages(new ArrayList<>(c.getConversationMessages()));
    }

    /**
     * @return the allUsers
     */
    public List<Users> getAllUsers() {
        return allUsers;
    }

    /**
     * @param allUsers the allUsers to set
     */
    public void setAllUsers(List<Users> allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * @return the targetUser
     */
    public String getTargetUser() {
        return targetUser;
    }

    /**
     * @param targetUser the targetUser to set
     */
    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    /**
     * @return the myConversations
     */
    public HashMap<Integer, Conversation> getMyConversations() {
        return myConversations;
    }

    /**
     * @param myConversations the myConversations to set
     */
    public void setMyConversations(HashMap<Integer, Conversation> myConversations) {
        this.myConversations = myConversations;
    }

    /**
     * @return the myMessages
     */
    public List<ConversationMessage> getMyMessages() {
        return myMessages;
    }

    /**
     * @param myMessages the myMessages to set
     */
    public void setMyMessages(List<ConversationMessage> myMessages) {
        this.myMessages = myMessages;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the displayConversations
     */
    public List<Conversation> getDisplayConversations() {
        return displayConversations;
    }

    /**
     * @param displayConversations the displayConversations to set
     */
    public void setDisplayConversations(List<Conversation> displayConversations) {
        this.displayConversations = displayConversations;
    }
    
}
