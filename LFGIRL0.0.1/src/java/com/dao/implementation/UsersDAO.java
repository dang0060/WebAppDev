/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao.implementation;

import com.dao.interfaces.IUsersDAO;
import com.hibernate.dataobjects.Users;
import com.hibernate.utilities.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

/**
 *
 * @author Protostar
 */
public class UsersDAO implements IUsersDAO {
    
    @Override
    public List<Users> listUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Users> userList = session.createQuery("from Users U left join fetch U.usersGroupses").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE).list();
        session.close();
        return userList;
    }

    @Override
    public Users findUserById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Users U left join fetch U.usersGroupses where U.id = :user_id");
        query.setParameter("user_id", id);
        Users user = (Users)query.uniqueResult();
        session.close();
        return user;
    }
    
    @Override
    public Users findUserByName(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Users U left join fetch U.usersGroupses where U.username = :user_name");
        query.setParameter("user_name", userName);
        Users user = (Users)query.uniqueResult();
        session.close();
        return user;
    }
    
    @Override
    public List<Users> findUsersByUserName(String userName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Users U left join fetch U.usersGroupses where U.username like concat('%', :user_name, '%')").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("user_name", userName);
        List<Users> users = query.list();
        session.close();
        return users;
    }
    
    @Override
    public void addUser(Users u) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(u);
        tx.commit();
        session.close();
    }
    
    @Override
    public boolean userCheck(String s) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Users U where U.username = :inputString");
        query.setParameter("inputString", s);
        Users user = (Users)query.uniqueResult();
        if (user == null) {
            return false;
        } else
            return true;
    }
}
