/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.GroupMessageDAO;
import services.interfaces.GroupMessageService;
import hibernate.dataobjects.GroupMessages;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ya
 */
@Primary
@Service
public class GroupMessageServiceImpl implements GroupMessageService{
    
    @Autowired
    private GroupMessageDAO groupMessageDAO;
    
    public void setgroupMessageDAO(GroupMessageDAO groupMessageDAO) {
        this.groupMessageDAO = groupMessageDAO;
    }
    
    /*obtain all message for a user for a particualr group*/
    @Override
    @Transactional
    public List<GroupMessages> listMessages(int gid, int receiverId){
       return groupMessageDAO.listMessages(gid,receiverId);
    }  
    
     /*add message to database @yawei*/
     @Override
     @Transactional
    public void addMessage(GroupMessages gm){
      groupMessageDAO.addMessage(gm);
    }
         
    /*delete message from database*/
     @Override
     @Transactional
    public void deleteMessage(int message_id){
      groupMessageDAO.deleteMessage(message_id);
    }
    
     /*update read status  @yawei*/
     @Override
     @Transactional
     public void updateReadStatus(int messageId){
       groupMessageDAO.updateReadStatus(messageId);
     }
}
