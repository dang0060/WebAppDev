
import dao.UsersDAO;
import dao.GroupsDAO;
import java.util.Scanner;
import hibernate.dataobjects.Users;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import hibernate.dataobjects.Groups;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Protostar
 */
public class TestDB {
    
    public static void main(String[] args) {
    
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml")) {
            UsersDAO usersDAO = context.getBean(UsersDAO.class);
            GroupsDAO groupDao = context.getBean(GroupsDAO.class);
            Scanner inputScanner = new Scanner(System.in);
            
            //User x = new User();
            //x.setUserId(150);
            //x.setUsername("Some Guy");
            //x.setPassword("Some Password");
            //x.setEmail("Someguy@someplace.com");
            
            //userDAO.save(x);
            
            /*List<Users> list = usersDAO.listUsers();
            
            for(Users u : list) {
                System.out.println("User Name: " + u.getUsername());
            }*/
        // while(true){
            System.out.println("Enter user name:");
            Users foundUser = new Users();
            foundUser = usersDAO.findUserByName(inputScanner.nextLine());
            System.out.println(foundUser.getUsername());
            System.out.println(foundUser.getUserId());
            //System.out.println(foundGroup.getDescription());
            
          /* List<Groups> groupList = groupDao.listGroups();
            for(Groups g : groupList) {
                System.out.println("Group Name: " + g.getGroupname());
            }*/
            System.out.println("Enter group name:");
            Groups foundGroup = new Groups();
            foundGroup = groupDao.findGroupByName(inputScanner.nextLine());
            System.out.println(foundGroup.getGroupname());
            System.out.println(foundGroup.getGroupId());
          //}
        }
    }
   
}
