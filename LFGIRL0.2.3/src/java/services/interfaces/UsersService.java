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

    List<Users> listUsers();

    Users getUserById(int id);
    
    Users getUserByName(String username);

    List<Users> getUsersByName(String username);
    
    String registerNewUser(Users u);
    
    void updateUserInfo(Users u);
    
    void deleteUser(int id);
    
    /*for user creation, to be used in UsersView or a bean @yawei*/
    public void addUser(Users u);
    
   /*check existing user, to be used in UsersView or a bean @yawei*/
    public boolean userNameDupCheck(String userName);
    
   /*check existing email, to be used in UsersView or a bean @yawei*/
    public boolean userEmailDupCheck(String email);

}
