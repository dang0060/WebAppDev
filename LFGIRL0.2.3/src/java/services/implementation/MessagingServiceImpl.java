/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.ChatDAO;
import hibernate.dataobjects.Conversation;
import hibernate.dataobjects.ConversationMessage;
import hibernate.dataobjects.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    public void setChatDAO(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }
    
    @Override
    public List<ConversationMessage> retrieveConversation(int conversationId, int userRequesting, int userTarget) {
        return chatDAO.getConversation(conversationId, userRequesting, userTarget);
//        List<ConversationMessage> convo = chatDAO.getConversation(conversationId, userRequesting, userTarget);
//        List<ConversationMessage> sanitized = new ArrayList<>();
//        convo.stream().forEach((c) -> {
//            Users u = c.getUsers();
//        });
//        return convo;
    }

    @Override
    public void speakConversation(int conversationId, int userId, String message) {
        Users user = new Users();
        user.setUserId(userId);
        Conversation c = new Conversation();
        c.setConversationId(conversationId);
        ConversationMessage cm = new ConversationMessage();
        cm.setMessage(message);
        cm.setUsers(user);
        cm.setConversation(c);
        Date d = new Date();
        cm.setTimeStamp(d);
        chatDAO.insertMessage(cm);
    }
    
}
