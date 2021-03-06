/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.GroupsDAO;
import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Tags;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import other.dataobjects.SearchResult;
import services.interfaces.GroupsService;

/**
 *
 * @author Protostar
 */
@Primary
@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsDAO groupsDAO;
    
    public void setGroupsDAO(GroupsDAO groupsDAO) {
        this.groupsDAO = groupsDAO;
    }
    
    @Override
    @Transactional
    public Groups findGroupById(int id) {
        return groupsDAO.findGroupById(id);
    }
    
    @Override
    public List<Groups> findGroupsByUserId(int userId) {
        return groupsDAO.findGroupsByUserId(userId);
    }

    @Override
    public List<Groups> findGroupsByName(String name) {
        return groupsDAO.findGroupsByName(name);
    }

    @Override
    public List<Groups> findGroupByDescription(String description) {
        return groupsDAO.findGroupsByDesc(description);
    }
    
    @Override
    @Transactional
    public void updateGroupInfo(Groups group){
        groupsDAO.updateGroup(group);
    }
    
    /*for group creation  @yawei*/
    @Override
     public void addGroup(Groups g){
       groupsDAO.addGroup(g);
     }  
    /*for group name check @yawei*/
     @Override
    public boolean groupCheck(String groupName){
      return groupsDAO.groupCheck(groupName);
    }

    @Override
    public void deleteGroup(int gid) {
        groupsDAO.deleteGroup(gid);
    }

    @Override
    public void addMember(UsersGroups ug) {
        groupsDAO.addMember(ug);
    }

    //try to remove member from group @yawei
    @Override
    public void deleteMember(Users user, Groups group) {
        groupsDAO.deleteMember(user, group); 
    }
    
    //try to implement group leader search
    @Override
    public  String findGroupLeader(int gid){
      return groupsDAO.findGroupLeader(gid);
    }
    
    //find members of a group 
    @Override
      public List<Users> findGroupMembers(int gid){
        return groupsDAO.findGroupMembers(gid);
      }
      
    @Override
        public List<SearchResult> findNearestGroups(float lati, float longi, float maxDistance){
            return groupsDAO.findNearestGroups(lati, longi, maxDistance);
        }

    @Override
    public List<SearchResult> findNearestGroupsbyName(float lati, float longi, float maxDistance, String name) {
        return groupsDAO.findNearestGroupsByName(lati, longi, maxDistance, name);
    }

    @Override
    public List<SearchResult> findNearestGroupsbyDesc(float lati, float longi, float maxDistance, String description) {
        return groupsDAO.findNearestGroupsByDesc(lati, longi, maxDistance, description);
    }

    @Override
    public String getSecretKey(String name) {
        return groupsDAO.getSecretKey(name);
    }

    @Override
    public Tags findTagByName(String name) {
        return groupsDAO.findTagByName(name);
    }

    @Override
    public Tags addNewTag(Tags tag) {
        return groupsDAO.addNewTag(tag);
    }

    @Override
    public List<SearchResult> findNearestGroupsByTag(float latitude, float longitude, float maxDistance, String tag) {
        return groupsDAO.findNearestGroupsByTag(latitude, longitude, maxDistance, tag);
    }

    @Override
    public List<Groups> findGroupsByTagName(String searchTerm) {
        return groupsDAO.findGroupsByTagName(searchTerm);
    }

    @Override
    public List<Groups> findGroupsByTagID(int id) {
        return groupsDAO.findGroupsByTagID(id);
    }

    @Override
    public Tags findTagByID(int id) {
        return groupsDAO.findTagByID(id);
    }
}
