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
 * @author Protostar
 */
@Repository
public class ChatDAOImpl implements ChatDAO {

    private SessionFactory sFac;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        sFac = sessionFactory;
    }
    
    @Override
    public List<ConversationMessage> getConversation(int conversationId, int userRequesting, int userTarget) {
        Session session = sFac.openSession();
        Query query = session.createQuery("SELECT C.conversationMessages\n" +
                                            "FROM Conversation C\n" +
                                            "WHERE C.conversationId = :convo_id");
        Query testQuery = session.createQuery("FROM ConversationMessage as cm\n" +
                                                "LEFT JOIN cm.users as U\n" +
                                                "LEFT JOIN cm.conversation as C\n" +
                                                "WHERE C.conversationId = :convo_id");
        query.setParameter("convo_id", conversationId);
        testQuery.setParameter("convo_id", conversationId);
        List<ConversationMessage> convoMessages = testQuery.list();
        List<ConversationMessage> convoMessages2 = query.list();
        session.close();
        return convoMessages2;
    }

    @Override
    public List<?> getConversationTest(int conversationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
      
}
