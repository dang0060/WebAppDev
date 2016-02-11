/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hibernate.dataobjects.Users;
import java.util.*;

/**
 *
 * @author Protostar
 */
public interface UsersDAO {
    
    public List<Users> listUsers();
    
    public Users findUserById(int id);
    
    public Users findUserByUserName(String userName);
    
    public void addUser(Users u);
    
    public void deleteUser(int userId);
    
    public boolean userCheck(String userName);
    
    public boolean emailCheck(String email);
    
    public Users login(String username, String password);
    
}
