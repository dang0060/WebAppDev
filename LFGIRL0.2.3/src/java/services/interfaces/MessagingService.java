/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import hibernate.dataobjects.ConversationMessage;
import java.util.List;

/**
 *
 * @author Protostar
 */
public interface MessagingService {
    
    public List<ConversationMessage> retrieveConversation(int conversationId, int userRequesting, int userTarget);
    
    public void speakConversation(int conversationId, int userId, String message);
    
}
