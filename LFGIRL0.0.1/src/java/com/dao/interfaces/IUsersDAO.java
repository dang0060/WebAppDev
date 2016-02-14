/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao.interfaces;

import com.hibernate.dataobjects.Users;
import java.util.List;

/**
 *
 * @author Protostar
 */
public interface IUsersDAO {
    
    public List<Users> listUsers();
    
    public Users findUserById(int id);
    
    public Users findUserByName(String userName);
    
    public List<Users> findUsersByUserName(String userName);
    
    public void addUser(Users u);
    
    public boolean userCheck(String userName);
    
}
