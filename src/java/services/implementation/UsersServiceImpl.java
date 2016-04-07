/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.UsersDAO;
import hibernate.dataobjects.Users;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@Primary
@Service
public class UsersServiceImpl implements UsersService {
    
    @Autowired
    private UsersDAO usersDAO;
    
    public void setUsersDAO(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    @Override
    @Transactional
    public List<Users> listUsers() {
        List<Users> usersList = usersDAO.listUsers();
        return usersList;
    }

    @Override
    @Transactional
    public Users getUserById(int id) {
        return usersDAO.findUserById(id);
    }
    
    @Override
    @Transactional
    public Users getUserByName(String username) {
        return usersDAO.findUserByName(username);
    }

    @Override
    @Transactional
    public List<Users> getUsersByName(String username) {
        return usersDAO.findUsersByUserName(username);
    }   

    @Override
    @Transactional
    public String registerNewUser(Users u) {
        if (userNameCheck(u)) {
            return "User Name already exists.";
        }
        
        if (userEmailCheck(u)) {
            return "The email address already has an account associated to it";
        }
        usersDAO.addUser(u);
        return null;
    }
    
    @Override
    @Transactional
    public void updateUserInfo(Users u) {
        usersDAO.updateUser(u);
    }

    @Override
    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }

    private boolean userNameCheck(Users u) {
       return usersDAO.userNameCheck(u.getUsername());
    }
    
    private boolean userEmailCheck(Users u) {
        return usersDAO.userEmailCheck(u.getEmail());
    }

    /*for user creation, to be used in UsersView or a bean
    As the user userName and email will be checked inside the View or bean,
    before addUser is called.  And a User object will not be given attributes before those
    checks are done, this differs from the registerNewUser way of implementation, which
    is having the User object been assigned all the attributes, then decide to add it or not.
    @yawei*/
    @Override
    @Transactional
    public void addUser(Users u){
       usersDAO.addUser(u);
    }
    
   /*check existing user, to be used in UsersView or a bean rather than locally@yawei*/
    @Override
    @Transactional
    public boolean userNameDupCheck(String userName){
      return usersDAO.userNameCheck(userName);
    }
    
     /*check existing email, to be used in UsersView or a bean rather than locally
      This differs from the local version, as it takes email as argument,
      which will be passed inside the UsersView or bean, as no User object will be
      used unless all the checks are passed @yawei*/
    @Override
    @Transactional
    public boolean userEmailDupCheck(String email) {
        return usersDAO.userEmailCheck(email);
    }
    
    /*find username through userId*/
    @Override
    @Transactional
    public String findUserNameById(int id) {
      return usersDAO.findUserNameById(id);
    }
}
