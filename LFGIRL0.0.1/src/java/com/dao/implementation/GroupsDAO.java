/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao.implementation;

import com.dao.interfaces.IGroupsDAO;
import com.hibernate.dataobjects.Groups;
import com.hibernate.utilities.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

/**
 *
 * @author Protostar
 */
public class GroupsDAO implements IGroupsDAO{
    
    @Override
    public List<Groups> listGroups() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Groups> groupList = session.createQuery("from Groups G left join fetch G.usersGroupses").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE).list();
        session.close();
        return groupList;
    }

    @Override
    public Groups findGroupById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.id = :group_id");
        query.setParameter("group_id", id);
        Groups group = (Groups)query.uniqueResult();
        session.close();
        return group;
    }

    @Override
    public Groups findGroupByName(String groupName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.name = :groupname");
        query.setParameter("group_name", groupName);
        Groups group = (Groups)query.uniqueResult();
        session.close();
        return group;
    }

    @Override
    public List<Groups> findGroupByDesc(String desc) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses where G.description like concat('%', :desc , '%')").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);
        query.setParameter("desc", desc);
        List<Groups> groups = query.list();
        session.close();
        return groups;
    }

    @Override
    public void addGroup(Groups g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean groupCheck() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
