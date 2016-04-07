/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import hibernate.dataobjects.GroupMessages;
import java.util.List;

/**
 *
 * @author Ya
 */
public interface GroupMessageService {
    
    //obtain messages that belong to a group 
    public List<GroupMessages> listMessages(int gid, int receiverId);            
     /*add message to database @yawei*/
   
    public void addMessage(GroupMessages gm); 
         
    //delete message from database  
    public void deleteMessage(int message_id); 
    
    /*update read status  @yawei*/
    public void updateReadStatus(int messageId);
    
    /*delete all messages belongs to a user*/
    public void deleteUserAllMessage(int uid, int gid);
    
    /*check receiver id, make sure the member is a group member*/
    public Boolean verifyReceiverId(int gid, int receiverId);
           
}
