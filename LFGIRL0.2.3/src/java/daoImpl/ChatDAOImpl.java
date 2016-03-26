/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import dao.ChatDAO;
import hibernate.dataobjects.Conversation;
import hibernate.dataobjects.ConversationMessage;
import hibernate.dataobjects.UserInfo;
import hibernate.dataobjects.Users;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.*;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Protostar
 */
@Repository
public class ChatDAOImpl implements ChatDAO {

    private SessionFactory sFac;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        sFac = sessionFactory;
    }
    
    @Override
    public Conversation getConversation(String user1, String user2) {
        Session session = sFac.openSession();
        Query query = session.createQuery("FROM Conversation C\n" +
                                            "WHERE C.usersByUserStartId.username = :user1\n" +
                                            "AND C.usersByUserRecieveId.username = :user2");
        query.setParameter("user1", user1);
        query.setParameter("user2", user2);
        Conversation result = (Conversation) query.uniqueResult();
        if (result != null) {
            session.close();
            return result;
        }
        Query query2 = session.createQuery("FROM Conversation C\n" +
                                            "WHERE C.usersByUserStartId.username = :user2\n" +
                                            "AND C.usersByUserRecieveId.username = :user1");
        query2.setParameter("user1", user1);
        query2.setParameter("user2", user2);
        Conversation result2 = (Conversation) query2.uniqueResult();
        if (result2 != null) {
            session.close();
            return result2;
        }
        session.close();
        return null;
    }

    @Override
    public void insertMessage(ConversationMessage cm) {
        Session session = sFac.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(cm);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Object[]> getMyMessages(int userId) {
        Session session = sFac.openSession();
        Query query = session.createQuery("FROM Conversation C \n" +
                                            "inner join C.conversationMessages as CM\n" +
                                            "inner join CM.users as U \n" +
                                            "WHERE C.usersByUserStartId.userId = :user_id \n" +
                                            "OR C.usersByUserRecieveId = :user_id");
//        Query query = session.createQuery("SELECT conversationMessages \n" +
//                                            "FROM Conversation C \n" +
//                                            "WHERE C.usersByUserStartId.userId = :user_id\n" +
//                                            "OR C.usersByUserRecieveId = :user_id");
        query.setParameter("user_id", userId);
        List<Object[]> myConversations = query.list();
        //for (ConversationMessage cm : myMessages) {
        //    Hibernate.initialize(cm);
        //}
        session.close();
        return myConversations;
    }

    @Override
    public boolean checkForConversation(String user1, String user2) {
        Session session = sFac.openSession();
        Query query = session.createQuery("FROM Conversation C\n" +
                                            "WHERE C.usersByUserStartId.username = :user1\n" +
                                            "AND C.usersByUserRecieveId.username = :user2");
        query.setParameter("user1", user1);
        query.setParameter("user2", user2);
        Conversation result = (Conversation) query.uniqueResult();
        if (result != null) {
            session.close();
            return true;
        }
        Query query2 = session.createQuery("FROM Conversation C\n" +
                                            "WHERE C.usersByUserStartId.username = :user2\n" +
                                            "AND C.usersByUserRecieveId.username = :user1");
        query2.setParameter("user1", user1);
        query2.setParameter("user2", user2);
        Conversation result2 = (Conversation) query2.uniqueResult();
        if (result2 != null) {
            session.close();
            return true;
        }
        session.close();
        return false;          
    }

    @Override
    public void buildConversation(Conversation c) {
        Session session = sFac.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
      
}
