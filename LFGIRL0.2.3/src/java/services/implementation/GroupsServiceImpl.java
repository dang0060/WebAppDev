/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.GroupsDAO;
import hibernate.dataobjects.Groups;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
}
