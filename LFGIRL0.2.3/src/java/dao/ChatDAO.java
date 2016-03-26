/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.dataobjects.Conversation;
import hibernate.dataobjects.ConversationMessage;
import hibernate.dataobjects.Users;
import java.util.List;

/**
 *
 * @author Protostar
 */
public interface ChatDAO {
    
    public Conversation getConversation(String user1, String user2);
    
    public void insertMessage(ConversationMessage cm);
    
    public List<Object[]> getMyMessages(int userId);
    
    public boolean checkForConversation(String user1, String user2);
    
    public void buildConversation(Conversation c);
    
}
