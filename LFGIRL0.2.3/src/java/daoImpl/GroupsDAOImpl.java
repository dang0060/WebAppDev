/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import dao.GroupsDAO;
import hibernate.dataobjects.Groups;
import hibernate.dataobjects.UsersGroups;
import hibernate.dataobjects.UsersGroupsId;
import java.util.List;
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
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.groupname = :group_name");
        query.setParameter("group_name", groupName);
        Groups group = (Groups)query.uniqueResult();
        session.close();
        return group;
    }
    
    @Override
    public List<Groups> findGroupsByName(String groupName) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.groupname like concat('%', :group_name , '%')").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("group_name", groupName);
        List<Groups> groups = query.list();
        session.close();
        return groups;
    }

    @Override
    public List<Groups> findGroupsByDesc(String desc) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.description like concat('%', :desc , '%')").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("desc", desc);
        List<Groups> groups = query.list();
        session.close();
        return groups;
    }

    @Override
    public void updateGroup(Groups g){
        Session session=sFac.openSession();
        Transaction tx=null;
        try {
             tx = session.beginTransaction();
             Groups oldGroup=(Groups)session.get(Groups.class, g.getGroupId());
             oldGroup.setDescription(g.getDescription());
             oldGroup.setGroupname(g.getGroupname());
             session.update(oldGroup);
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
    
    /*try to define using the same format as addUser, can be removed if better definiation is available @yawei*/
    @Override
    public void addGroup(Groups g) {
       Session session = sFac.openSession();
       Transaction tx=session.beginTransaction();
       session.persist(g);
       tx.commit();
       session.close();
    }

    /*try to define using the same format as userCheck, can be removed if better definiation is available @yawei*/
    @Override
    public boolean groupCheck(String s) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Groups G where G.groupname = :inputString");
        query.setParameter("inputString", s);
        Groups group = (Groups)query.uniqueResult();
        session.close();//should close the session after the query @yawei
        if (group == null) {        
            return false;
        } else         
            return true;
    }

    @Override
    public List<Groups> findGroupsByUserId(int userId) {
        Session session = sFac.openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses UG where UG.users.userId = :user_id");
        query.setParameter("user_id", userId);
        List<Groups> groups = query.list();
        session.close();
        return groups;
    }

    @Override
    public void addMember(UsersGroups ug) {
        Session session = sFac.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(ug);
        tx.commit();
        session.close();
    }

    @Override
    public void deleteGroup(int gid) {
        Session session=sFac.openSession();
        Transaction tx=null;
        try {
             tx = session.beginTransaction();
             Groups group = (Groups)session.get(Groups.class, gid);
             session.delete(group);
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
    public void deleteMember(UsersGroupsId ugid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
