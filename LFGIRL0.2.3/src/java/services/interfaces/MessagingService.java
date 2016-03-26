/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import hibernate.dataobjects.Conversation;
import hibernate.dataobjects.ConversationMessage;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Protostar
 */
public interface MessagingService {
    
    public void speakConversation(String userName, String targetUser, String message);
    
    public HashMap<Integer, Conversation> retrieveConversations(int userId);
    
}
