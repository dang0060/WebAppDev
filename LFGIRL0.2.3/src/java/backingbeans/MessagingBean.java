/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import hibernate.dataobjects.ConversationMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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

/**
 *
 * @author Protostar
 */
@ManagedBean(name = "MessagingBean")
@RequestScoped
public class MessagingBean implements Serializable {
    
    @Autowired
    transient MessagingService messagingService;
    
    private List<ConversationMessage> convo;
    
    @PostConstruct
    private void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sC = (ServletContext) ec.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(sC).getAutowireCapableBeanFactory().autowireBean(this);
        
        if(FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("chat")) {
            onLoad();
        }
        
    }
    
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        Autowirer.wireObject(this);
    }
    
    private void onLoad() {
        LoginBean lv = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
        if (lv != null) {
            convo = messagingService.retrieveConversation(1, 1, 3);
        }
    }

    /**
     * @return the convo
     */
    public List<ConversationMessage> getConvo() {
        return convo;
    }

    /**
     * @param convo the convo to set
     */
    public void setConvo(List<ConversationMessage> convo) {
        this.convo = convo;
    }
    
//    public List<ConversationMessage> updateConversation() {
//        convo = messagingService.retrieveConversation(1, 1, 3);
//    }
    
    public void updateConversation() {
        convo = messagingService.retrieveConversation(1, 1, 3);
        //System.out.println("breakpoint");
    }
    
    public void sendMessage(String message) {
        LoginBean lv = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");
        if (lv != null) {
            messagingService.speakConversation(1, lv.getUserId(), message);
        }
    }

    
}
