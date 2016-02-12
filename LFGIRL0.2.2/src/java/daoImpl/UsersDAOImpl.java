/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import dao.UsersDAO;
import hibernate.dataobjects.Users;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Protostar
 */
@Repository
public class UsersDAOImpl implements UsersDAO{
    
    @Autowired
    private SessionFactory sFac;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        sFac = sessionFactory;
    }

    @Override
    public List<Users> listUsers() {
        Session session = sFac.openSession();
        List<Users> userList = session.createQuery("from Users U left join fetch U.usersGroupses").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE).list();
        session.close();
        return userList;
    }

    @Override
    public Users findUserById(int id) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Users U left join fetch U.usersGroupses where U.id = :user_id");
        query.setParameter("user_id", id);
        Users user = (Users)query.uniqueResult();
        session.close();
        return user;
    }
    
    @Override
    public List<Users> findUsersByUserName(String userName) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Users U left join fetch U.usersGroupses where U.username like concat('%', :user_name, '%')").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("user_name", userName);
        List<Users> users = query.list();
        session.close();
        return users;
    }
    
    @Override
    public void addUser(Users u) {
        Session session = sFac.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(u);
        tx.commit();
        session.close();
    }
    
    @Override
    public boolean userCheck(String s) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Users U where U.username = :inputString");
        query.setParameter("inputString", s);
        Users user = (Users)query.uniqueResult();
        if (user == null) {
            return false;
        } else
            return true;
    }
    
}
