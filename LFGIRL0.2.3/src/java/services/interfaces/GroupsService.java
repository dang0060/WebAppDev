/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import hibernate.dataobjects.Groups;
import hibernate.dataobjects.Tags;
import hibernate.dataobjects.Users;
import hibernate.dataobjects.UsersGroups;
import java.util.List;

/**
 *
 * @author Protostar
 */
public interface GroupsService {
    
    public Groups findGroupById(int id);
    
    public List<Groups> findGroupsByUserId(int userId);
    
    public List<Groups> findGroupsByName(String name);
    
    public List<Groups> findGroupByDescription(String description);
    
    void updateGroupInfo(Groups groups);
    
     /*for group creation  @yawei*/
    public void addGroup(Groups g);
    
    public void deleteGroup(int gid);
    
    public void addMember(UsersGroups ug);
   
    //try to remove member
    public void deleteMember(Users user, Groups group);
    
    /*for group name check @yawei*/
    public boolean groupCheck(String s);
    
    //try to implment leader search @yawei
    public String findGroupLeader(int gid);
    
    //searches for group members @yawei
    public List<Users> findGroupMembers(int gid);
    
    public List<Object[]> findNearestGroups(float lati, float longi, float maxDistance);
    
    public List<Object[]> findNearestGroupsbyName(float lati, float longi, float maxDistance, String name);
    
    public List<Object[]> findNearestGroupsbyDesc(float lati, float longi, float maxDistance, String description);
    
    public Tags findTagByName(String name);
    
    public Tags addNewTag(Tags tags);

    public String getSecretKey(String name);
}
