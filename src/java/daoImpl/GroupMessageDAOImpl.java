/*
 * GroupMessage DAO implementation @yawei
 */
package daoImpl;

import dao.GroupMessageDAO;
import hibernate.dataobjects.GroupMessages;
import hibernate.dataobjects.UsersGroups;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.*;
import org.springframework.stereotype.Repository;
/**
 *
 * @author Ya
 */
@Repository
public class GroupMessageDAOImpl implements GroupMessageDAO {
    private SessionFactory sFac;  
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        sFac = sessionFactory;
    }
    
    //obtain messages for a user that belongs to one group
    @Override
    public List<GroupMessages> listMessages(int gid, int receiverId) {      
         Session session = null;
         List<GroupMessages> groupMessages = null; 
      try{
        session = sFac.openSession();
        Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.groups.groupId = :group_id AND GM.receiverUserId= :rev_Id").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("group_id", gid);
        query.setParameter("rev_Id", receiverId);
        groupMessages = query.list();      
      }
      catch(NullPointerException | HibernateException np){
        throw np;
      }finally{
       if(session != null && session.isOpen())
           session.close();   
      }
        return groupMessages;
    }
    
    //check receiver id, make sure the member is a group member
    @Override
    public Boolean verifyReceiverId(int gid, int receiverId){
        Session session = null;
        UsersGroups possibleMember = null;
      try{
        session = sFac.openSession();
        Query query = session.createQuery("SELECT UG from UsersGroups UG where UG.groups.groupId= :group_id AND UG.users.userId= :rev_id");
        query.setParameter("group_id", gid);
        query.setParameter("rev_id", receiverId);
        possibleMember = (UsersGroups)query.uniqueResult();
        session.close();      
      }
       catch(NullPointerException  | HibernateException np){
        throw np;
      }
       finally{
        if(session != null && session.isOpen())
           session.close();    
       }
        if(possibleMember == null)
            return false;
        return true;
      
    }
    
     /*add message to database @yawei*/
    @Override
    public void addMessage(GroupMessages gm) {
       Transaction tx = null;
       Session session = null;
      try{  
       session = sFac.openSession();
       tx=session.beginTransaction();
       session.persist(gm);
       tx.commit();
       session.close();    
      }
      catch(NullPointerException np){
        throw np;
      }
      catch(HibernateException he){
        if(tx !=null) tx.rollback();
        throw he;
      }
      finally{
        if(session != null && session.isOpen())
           session.close();   
      }
    }
    
    //delete selected message from database 
    @Override
    public void deleteMessage(int message_id) {       
        Transaction tx=null;
        Session session = null;
        try {
             session=sFac.openSession();
             tx = session.beginTransaction();
             Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.messageId = :mes_id ");
             query.setParameter("mes_id", message_id);
             GroupMessages selectedMessage = (GroupMessages)query.uniqueResult();
             session.delete(selectedMessage);             
             tx.commit();          
        } 
        catch(NullPointerException np){
          throw np;
        } 
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
                throw he;
            }
            he.printStackTrace();
        }
        finally{
          if(session != null && session.isOpen())
             session.close();      
        }
    }
    
     //delete all messages belongs to a user in one group
    @Override
    public void deleteUserAllMessage(int uid, int gid) {    
        Transaction tx=null;
        Session session = null;
        try {
            session=sFac.openSession();
             tx = session.beginTransaction();
             Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.receiverUserId = :receiver_id  AND GM.groups.groupId = :group_id").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
             query.setParameter("receiver_id", uid);
             query.setParameter("group_id", gid);
              List<GroupMessages> selectedMessages = query.list();
              //delete all messages, one by one
              for (GroupMessages message : selectedMessages) {
                 session.delete(message);
                }                    
              tx.commit();           
        }
         catch(NullPointerException np){
           throw np;
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
                throw he;
            }
            he.printStackTrace();
        }
        finally{
          if(session != null && session.isOpen())
             session.close();      
        }
    }
    
    
    /*change the read status of the selected message to true @yawei*/
    @Override
    public void updateReadStatus(int messageId){     
        Transaction tx=null;
        Session session = null;
        try {
            session=sFac.openSession();
             tx = session.beginTransaction();
             Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.messageId = :mes_id ");
             query.setParameter("mes_id", messageId);
             GroupMessages selectedMessage = (GroupMessages)query.uniqueResult();
             if(!selectedMessage.getMessageReadStatus()){ // set to true if status is false             
                  selectedMessage.setMessageReadStatus(true);             
                  session.update(selectedMessage);
                  tx.commit();             
             }                     
        } 
        catch(NullPointerException np){
          throw np;
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
                throw he;
            }
            he.printStackTrace();
        }
        finally{
          if(session != null && session.isOpen())
             session.close();     
        }
    }
}
