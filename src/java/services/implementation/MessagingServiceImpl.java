/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.ChatDAO;
import dao.UsersDAO;
import hibernate.dataobjects.Conversation;
import hibernate.dataobjects.ConversationMessage;
import hibernate.dataobjects.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import services.interfaces.MessagingService;

/**
 *
 * @author Protostar
 */
@Primary
@Service
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    private ChatDAO chatDAO;
    
    @Autowired
    private UsersDAO userDAO;
    
    public void setChatDAO(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }
    
    public void setUserDAO(UsersDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public HashMap<Integer, Conversation> retrieveConversations(int userId) {
        List<Object[]> information = chatDAO.getMyMessages(userId);
        HashMap<Integer, Conversation> conversations = new HashMap<>();
        
        for (Object[] o : information) {
            Conversation c = (Conversation) o[0];
            if(!conversations.containsKey(c.getConversationId())) {
                Conversation x = new Conversation();
                x.setConversationId(c.getConversationId());
                x.setStatus(c.getStatus());
                x.setUsersByUserStartId(c.getUsersByUserStartId());
                x.setUsersByUserRecieveId(c.getUsersByUserRecieveId());
                conversations.put(x.getConversationId(), x);
            }
        }
        
        for (Object[] o : information) {
            ConversationMessage cm = (ConversationMessage) o[1];
            Conversation c = conversations.get(cm.getConversation().getConversationId());
            if (c != null) {
                c.getConversationMessages().add(cm);
            }
        }
        return conversations;
    }

    @Override
    public void speakConversation(String userName, String targetUser, String message) {
        Users user1 = userDAO.findUserByName(userName);
        Users user2 = userDAO.findUserByName(targetUser);
        if(!chatDAO.checkForConversation(user1.getUsername(), user2.getUsername())) {
            Conversation c = new Conversation();
            c.setUsersByUserStartId(user1);
            c.setUsersByUserRecieveId(user2);
            c.setStatus("ACTIVE");
            chatDAO.buildConversation(c);
        }
        Conversation convo = chatDAO.getConversation(user1.getUsername(), user2.getUsername());
        ConversationMessage cm = new ConversationMessage();
        cm.setConversation(convo);
        cm.setMessage(message);
        cm.setUsers(user1);
        cm.setTimeStamp(new Date());
        chatDAO.insertMessage(cm);
    }
    
}
