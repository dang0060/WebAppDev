/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import hibernate.dataobjects.Users;
import java.util.*;

/**
 *
 * @author Protostar
 */
public interface UsersService {

    public List<Users> listUsers();

    public Users getUserById(int id);
    
    public Users getUserByName(String username);

    public List<Users> getUsersByName(String username);
    
    public void updateUserInfo(Users u);
    
    public void deleteUser(int id);
    
    /*for user creation @yawei*/
    public void addUser(Users u);
    
   /*check existing user, can be used with addUser to prevent duplicate username @yawei*/
    public boolean userCheck(String userName);
}
