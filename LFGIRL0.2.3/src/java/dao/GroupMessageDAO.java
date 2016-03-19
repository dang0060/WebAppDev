/*Group message DAO*/
package dao;

import hibernate.dataobjects.GroupMessages;
import java.util.List;
/**
 *
 * @author Ya
 */
public interface GroupMessageDAO {
   
    /*get all messages for a user of a group*/
    public List<GroupMessages> listMessages(int gid, int receiverId);            
     
    /*add message to database @yawei*/
    public void addMessage(GroupMessages gm); 
         
    /*delete message from database  @yawei*/
    public void deleteMessage(int message_id); 
    
      /*update read status  @yawei*/
     public void updateReadStatus(int messageId);
}
