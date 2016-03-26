/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImpl;

import dao.GroupsDAO;
import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Tags;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import hibernate.dataobjects.UsersGroupsId;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.*;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import other.dataobjects.SearchResult;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@Repository
public class GroupsDAOImpl implements GroupsDAO {
    
    private SessionFactory sFac;
    private UsersService usersService; 
    
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
        Query query = session.createQuery("from Groups G left join fetch G.usersGroupses left join fetch G.tagses where G.id = :group_id");
        query.setParameter("group_id", id);
        Groups group = (Groups)query.uniqueResult();
        session.close();
        return group;
    }

    //get the name of the leader for one group, based on group id  @yawei
    @Override
    public String findGroupLeader(int gid) {
        Session session = sFac.openSession();
        Query query = session.createQuery("SELECT UG.users from UsersGroups UG where UG.isLeader= '1' AND UG.groups.groupId = :group_id");
        query.setParameter("group_id", gid);
        Users user = (Users)query.uniqueResult();
        session.close();
        if(user != null)
            return (user.getUsername());
        return ""; //member is deleted, no name to return
    }  
    //end of group leader search function 
    
    //get members of a group   @yawei
    @Override
    public List<Users> findGroupMembers(int gid) {
        Session session = sFac.openSession();
        Query query = session.createQuery("SELECT UG.users from UsersGroups UG where UG.groups.groupId = :group_id").setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);      
        query.setParameter("group_id", gid);
        List<Users> groupUsers = query.list();
        session.close();
        return groupUsers;
    }  
    //end of group member search function 
    
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
             session.update(g);
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

    //trying to implment leaving group function @yawei
    @Override
    public void deleteMember(Users user, Groups group) 
    {   Session session = sFac.openSession();
        Transaction tx=null;
        //create a new UsersGroupId entity
        UsersGroupsId uGId = new UsersGroupsId(user.getUserId(),group.getGroupId());
        
        try {
            tx = session.beginTransaction();
            UsersGroups oldGroup= (UsersGroups)session.get(UsersGroups.class, uGId);
            //set entity's fields to match the row in database
             oldGroup.setUsers(user);
             oldGroup.setGroups(group);
             session.delete(oldGroup);
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
    public List<SearchResult> findNearestGroups(float lati, float longi, float maxDistance) {
        Session session = sFac.openSession();
        SQLQuery getDistances=session.createSQLQuery("Select groups.group_id, "
                + "(6371 * acos( cos( radians(:lati )) * cos( radians( groups.latitude ) ) * cos( radians( groups.longitude ) - radians(:longi) ) + sin( radians(:lati) ) * sin( radians( groups.latitude ) ) ) ) as distance "
                + "from groups having distance < :maxDistance order by distance;");
        getDistances.setParameter("lati", lati);
        getDistances.setParameter("longi", longi);
        getDistances.setParameter("maxDistance", maxDistance);
        getDistances.addScalar("group_id",StandardBasicTypes.INTEGER);
        getDistances.addScalar("distance", StandardBasicTypes.FLOAT);
        
        List<Object[]> closestGroups=getDistances.list();
        
        if(closestGroups.isEmpty())
            return new ArrayList<>();
        
        ArrayList groupsToGet = new ArrayList<>();
        
        for(Object[] r:closestGroups){
            int i=(int)r[0];
            groupsToGet.add(i);
        }
        
        Query getGroups = session.createQuery("from Groups G left join fetch G.tagses where G.groupId in (:ids)");
        getGroups.setParameterList("ids", groupsToGet);
        getGroups.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Groups> groupsFound = getGroups.list();
        
        ArrayList<SearchResult> results=new ArrayList<>();
        
        for(int i=0; i<groupsFound.size();i++){
            SearchResult s = new SearchResult(groupsFound.get(i), (float)(closestGroups.get(i)[1]));
            results.add(s);
        }
        
        return results;
    }
    
    @Override
    public List<SearchResult> findNearestGroupsByName(float lati, float longi, float maxDistance, String searchTerm) {
        Session session = sFac.openSession();
        SQLQuery getDistances=session.createSQLQuery("Select groups.group_id, "
                + "(6371 * acos( cos( radians(:lati )) * cos( radians( groups.latitude ) ) * cos( radians( groups.longitude ) - radians(:longi) ) + sin( radians(:lati) ) * sin( radians( groups.latitude ) ) ) ) as distance "
                + "from groups where groups.groupname like concat('%', :name , '%') having distance < :maxDistance order by distance;");
        getDistances.setParameter("lati", lati);
        getDistances.setParameter("longi", longi);
        getDistances.setParameter("maxDistance", maxDistance);
        getDistances.setParameter("name", searchTerm);
        getDistances.addScalar("group_id",StandardBasicTypes.INTEGER);
        getDistances.addScalar("distance", StandardBasicTypes.FLOAT);
        List<Object[]> closestGroups=getDistances.list();
        
        if(closestGroups.isEmpty())
            return new ArrayList<>();
        
        ArrayList groupsToGet = new ArrayList<>();
        
        for(Object[] r:closestGroups){
            int i=(int)r[0];
            groupsToGet.add(i);
        }
        
        Query getGroups = session.createQuery("from Groups G left join fetch G.tagses where G.groupId in (:ids)");
        getGroups.setParameterList("ids", groupsToGet);
        getGroups.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Groups> groupsFound = getGroups.list();
        
        ArrayList<SearchResult> results=new ArrayList<>();
        
        for(int i=0; i<groupsFound.size();i++){
            SearchResult s = new SearchResult(groupsFound.get(i), (float)(closestGroups.get(i)[1]));
            results.add(s);
        }
        
        return results;
    }
    
    @Override
    public List<SearchResult> findNearestGroupsByDesc(float lati, float longi, float maxDistance, String description) {
        Session session = sFac.openSession();
        SQLQuery getDistances=session.createSQLQuery("Select groups.group_id, "
                + "(6371 * acos( cos( radians(:lati )) * cos( radians( groups.latitude ) ) * cos( radians( groups.longitude ) - radians(:longi) ) + sin( radians(:lati) ) * sin( radians( groups.latitude ) ) ) ) as distance "
                + "from groups where groups.description like concat('%', :name , '%') having distance < :maxDistance order by distance;");
        getDistances.setParameter("lati", lati);
        getDistances.setParameter("longi", longi);
        getDistances.setParameter("maxDistance", maxDistance);
        getDistances.setParameter("name", description);
        getDistances.addScalar("group_id",StandardBasicTypes.INTEGER);
        getDistances.addScalar("distance", StandardBasicTypes.FLOAT);
        List<Object[]> closestGroups=getDistances.list();
        
        if(closestGroups.isEmpty())
            return new ArrayList<>();
        
        ArrayList groupsToGet = new ArrayList<>();
        
        for(Object[] r:closestGroups){
            int i=(int)r[0];
            groupsToGet.add(i);
        }
        
        Query getGroups = session.createQuery("from Groups G left join fetch G.tagses where G.groupId in (:ids)");
        getGroups.setParameterList("ids", groupsToGet);
        getGroups.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Groups> groupsFound = getGroups.list();
        
        ArrayList<SearchResult> results=new ArrayList<>();
        
        for(int i=0; i<groupsFound.size();i++){
            SearchResult s = new SearchResult(groupsFound.get(i), (float)(closestGroups.get(i)[1]));
            results.add(s);
        }
        
        return results;
    }
    
    @Override
    public String getSecretKey(String name){
        Session session = sFac.openSession();
        SQLQuery getKey=session.createSQLQuery("Select key_value from secret_keys where key_name=:name");
        getKey.setParameter("name", name);
        String result=(String)getKey.uniqueResult();
        return result;
    
    }

    @Override
    public Tags findTagByName(String name) {
        Session session=sFac.openSession();
        Query query=session.createQuery("from Tags T where T.tagName = :name");
        query.setParameter("name", name.toLowerCase());
        Tags result=(Tags)query.uniqueResult();
        return result;
    }

    @Override
    public Tags addNewTag(Tags tag) {
        Session session=sFac.openSession();
        Transaction tx = session.beginTransaction();        
        session.persist(tag);
        tx.commit();
        session.close();
        Tags results=findTagByName(tag.getTagName());
        
        return results;
    }

    @Override
    public List<SearchResult> findNearestGroupsByTag(float latitude, float longitude, float maxDistance, String tag) {
        Session session = sFac.openSession();
        SQLQuery query = session.createSQLQuery("Select groups.group_id, (6371 * acos( cos( radians(:lati )) * cos( radians( groups.latitude ) ) * cos( radians( groups.longitude ) - radians(:longi) ) + sin( radians(:lati) ) * sin( radians( groups.latitude ) ) ) ) as distance from groups join groups_tags on (groups.group_id=groups_tags.group_id_fk) join tags on (groups_tags.tag_id_fk=tags.tag_id) where tags.tag_name = :tagname having distance < :dist order by distance;");
        query.setParameter("lati", latitude);
        query.setParameter("longi", longitude);
        query.setParameter("tagname", tag);
        query.setParameter("dist", maxDistance);
        query.addScalar("group_id", StandardBasicTypes.INTEGER);
        query.addScalar("group_id", StandardBasicTypes.FLOAT);

        List<Object[]> closestGroups=query.list();
        
        if(closestGroups.isEmpty())
            return new ArrayList<>();
        
        ArrayList groupsToGet = new ArrayList<>();
        
        for(Object[] r:closestGroups){
            int i=(int)r[0];
            groupsToGet.add(i);
        }
        
        Query getGroups = session.createQuery("from Groups G left join fetch G.tagses where G.groupId in (:ids)");
        getGroups.setParameterList("ids", groupsToGet);
        getGroups.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Groups> groupsFound = getGroups.list();
        
        ArrayList<SearchResult> results=new ArrayList<>();
        
        for(int i=0; i<groupsFound.size();i++){
            SearchResult s = new SearchResult(groupsFound.get(i), (float)(closestGroups.get(i)[1]));
            results.add(s);
        }
        
        return results;
    }

    @Override
    public List<Groups> findGroupByTag(String searchTerm) {
        Session session = sFac.openSession();
        Query query = session.createQuery("select from Groups G left join fetch G.tagses T where T.tagName = :tag");
        query.setParameter("tag", searchTerm);
        List<Groups> groups = query.list();
        session.close();
        return groups;
    }

    
    
    
    
    }
    
