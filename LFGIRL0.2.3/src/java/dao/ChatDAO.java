/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.dataobjects.ConversationMessage;
import java.util.List;

/**
 *
 * @author Protostar
 */
public interface ChatDAO {
    
    public List<ConversationMessage> getConversation(int conversationId, int userRequesting, int userTarget);
    
    public void insertMessage(ConversationMessage cm);
    
    public List<?> getConversationTest(int conversationId);
    
}
