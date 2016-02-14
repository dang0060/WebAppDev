/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import hibernate.dataobjects.Groups;
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
    
}
