/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import dao.GroupsDAO;
import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import hibernate.dataobjects.UsersGroupsId;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.*;

/**
 *
 * @author Protostar
 */
public class GroupsDAOImpl implements GroupsDAO {
    
    private SessionFactory sFac;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        sFac = sessionFactory;
    }

    @Override
    public List<Groups> listGroups() {
        Session session = sFac.openSession();
        List<Groups> groupList = session.createQuery("from Groups G left join fetch G.usersGroupses").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE).list();
        session.close();
        return groupList;
    }

    @Override
    public Groups findGroupById(int id) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.id = :group_id");
        query.setParameter("group_id", id);
        Groups group = (Groups)query.uniqueResult();
        session.close();
        return group;
    }

    @Override
    public Groups findGroupByName(String groupName) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.name = :group_name");
        query.setParameter("group_name", groupName);
        Groups group = (Groups)query.uniqueResult();
        session.close();
        return group;
    }

    @Override
    public List<Groups> findGroupByDesc(String desc) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.description like concat('%', :desc , '%')").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("desc", desc);
        List<Groups> groups = query.list();
        session.close();
        return groups;
    }

    @Override
    public void addGroup(Groups g, int userId) {
        Groups grp;
        Users usr;
        Session session = sFac.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(g);
        tx.commit();
        
        Query query = session.createQuery("from Groups G where G.name = :group_name");
        query.setParameter("group_name", g.getName());
        grp=(Groups)query.uniqueResult();
        query = session.createQuery("from Users U where U.userId = :user_id");
        query.setParameter("user_id", userId);
        usr=(Users)query.uniqueResult();
        UsersGroupsId ugi = new UsersGroupsId(usr.getUserId(), grp.getGroupId());
        UsersGroups ug = new UsersGroups(ugi, grp, usr, true);
        tx=session.beginTransaction();
        session.persist(ug);
        tx.commit();
        session.close(); 
    }

    @Override
    public boolean groupCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean groupLeaderCheck(int userId, String groupName){
        Session session = sFac.openSession();
        Query query=session.createQuery("from UsersGroups UG where UG.groups.name = :group_name and UG.users.userId = :user_id and UG.isLeader = 1");
        query.setParameter("group_name", groupName);
        query.setParameter("user_id", userId);
        UsersGroups usersGroups = (UsersGroups)query.uniqueResult();
        return usersGroups!=null;
    }
    
    
    @Override 
    public void deleteGroup(int userId, String groupName){
        Session session = sFac.openSession();
        
    }
}
