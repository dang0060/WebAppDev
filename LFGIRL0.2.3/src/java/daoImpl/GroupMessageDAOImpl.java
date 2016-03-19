/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    //obtain messages that belong to a group
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
    
    //delete message from database 
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
    
    @Override
    public void updateReadStatus(int messageId){
        Session session=sFac.openSession();
        Transaction tx=null;
        try {
             tx = session.beginTransaction();
             Query query = session.createQuery("SELECT GM from GroupMessages GM where GM.messageId = :mes_id ");
             query.setParameter("mes_id", messageId);
             GroupMessages selectedMessage = (GroupMessages)query.uniqueResult();
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
