/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.implementation;

import dao.UsersDAO;
import hibernate.dataobjects.Users;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.interfaces.UsersService;

/**
 *
 * @author Protostar
 */
@Service
@ManagedBean(name="UsersService")
@SessionScoped
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
    
}
