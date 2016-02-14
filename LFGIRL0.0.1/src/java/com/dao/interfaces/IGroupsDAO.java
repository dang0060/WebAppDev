/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao.interfaces;

import com.hibernate.dataobjects.Groups;
import java.util.List;

/**
 *
 * @author Protostar
 */
public interface IGroupsDAO {
    
    public List<Groups> listGroups();
    
    public Groups findGroupById(int id);
    
    public Groups findGroupByName(String groupName);
    
    public List<Groups> findGroupByDesc(String desc);
    
    public void addGroup(Groups g);
    
    public boolean groupCheck();
    
}
