/*
 * GroupMessage DAO implementation @yawei
 */
package daoImpl;

import dao.GroupMessageDAO;
import hibernate.dataobjects.GroupMessages;
import java.util.List;
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
        Session session = sFac.openSession();
        Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.groups.groupId = :group_id AND GM.receiverUserId= :rev_Id").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("group_id", gid);
        query.setParameter("rev_Id", receiverId);
        List<GroupMessages> groupMessages = query.list();
        session.close();
        return groupMessages;
    }
    
     /*add message to database @yawei*/
    @Override
    public void addMessage(GroupMessages gm) {
       Session session = sFac.openSession();
       Transaction tx=session.beginTransaction();
       session.persist(gm);
       tx.commit();
       session.close();
    }
    
    //delete selected message from database 
    @Override
    public void deleteMessage(int message_id) {
        Session session=sFac.openSession();
        Transaction tx=null;
        try {
             tx = session.beginTransaction();
             Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.messageId = :mes_id ");
             query.setParameter("mes_id", message_id);
             GroupMessages selectedMessage = (GroupMessages)query.uniqueResult();
             session.delete(selectedMessage);             
             tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally{
            session.close();
        }
    }
    
    /*change the read status of the selected message to true @yawei*/
    @Override
    public void updateReadStatus(int messageId){
        Session session=sFac.openSession();
        Transaction tx=null;
        try {
             tx = session.beginTransaction();
             Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.messageId = :mes_id ");
             query.setParameter("mes_id", messageId);
             GroupMessages selectedMessage = (GroupMessages)query.uniqueResult();
             if(selectedMessage.getMessageReadStatus()){ //return if status is already set to true
                 return; 
             }            
             selectedMessage.setMessageReadStatus(true);             
             session.update(selectedMessage);
             tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally{
            session.close();
        }
    }
}
