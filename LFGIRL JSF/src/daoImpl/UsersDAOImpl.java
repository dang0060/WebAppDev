/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import dao.UsersDAO;
import hibernate.dataobjects.HibernateUtil;
import hibernate.dataobjects.Users;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

/**
 *
 * @author Protostar
 */
public class UsersDAOImpl implements UsersDAO{
    
    private SessionFactory sFac;
    
    public UsersDAOImpl(){
        sFac=HibernateUtil.getSessionFactory();
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
    public Users findUserByUserName(String userName) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Users U left join fetch U.usersGroupses where U.username = :user_name");
        query.setParameter("user_name", userName);
        Users user = (Users)query.uniqueResult();
        session.close();
        return user;
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
    public void deleteUser(int userId){
        Session session = sFac.openSession();
        Transaction transaction = session.beginTransaction();
    // delete usersgroups rows for this user 
        Query query=session.createQuery("delete from UsersGroups UG where UG.id.userId = :inputString");
        query.setParameter("inputString", userId);
        int result = query.executeUpdate();
    // delete user in users
        query = session.createQuery("delete from Users U where U.userId = :inputString");
        query.setParameter("inputString", userId);
        result = query.executeUpdate();
        transaction.commit();
        session.close();
    }
    @Override
    public boolean userCheck(String s) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Users U where U.username = :inputString");
        query.setParameter("inputString", s);
        Users user = (Users)query.uniqueResult();
        session.close();
        if (user == null) {
            return false;
        } else
            return true;
    }
    
    @Override
    public boolean emailCheck(String email){
        Session session = sFac.openSession();
        Query query = session.createQuery("from Users U where U.email = :inputString");
        query.setParameter("inputString", email);
        Users user = (Users)query.uniqueResult();
        session.close();
        return user != null;
    }
    
    @Override
    public Users login(String username, String password){
        Session session = sFac.openSession();
        Query query = session.createQuery("from Users U where U.username = :user_name and U.password = :pass_word");
        query.setParameter("user_name", username);
        query.setParameter("pass_word", password);
        Users user = (Users)query.uniqueResult();
        session.close();
        return user;
    }
}
