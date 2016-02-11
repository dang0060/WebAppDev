
import dao.UsersDAO;
import hibernate.dataobjects.Users;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Protostar
 */
/*public class TestDB {
    
    public static void main(String[] args) {
        
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml")) {
            UsersDAO usersDAO = context.getBean(UsersDAO.class);
            
            //User x = new User();
            //x.setUserId(150);
            //x.setUsername("Some Guy");
            //x.setPassword("Some Password");
            //x.setEmail("Someguy@someplace.com");
            
            //userDAO.save(x);
            
            List<Users> list = usersDAO.listUsers();
            
            for(Users u : list) {
                System.out.println("User Name: " + u.getUsername());
            }
        }
    }
    
}*/
