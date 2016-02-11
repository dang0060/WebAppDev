package servlets;

import hibernate.dataobjects.Users;
import dao.UsersDAO;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SignUpServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
        @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
		RequestDispatcher rd;
		
        response.setContentType("text/html");  
            try (PrintWriter out = response.getWriter()) {
                String n=request.getParameter("username");
                String p=request.getParameter("userpass");
                String c=request.getParameter("confirmpass");
                String e=request.getParameter("useremail");
                
                if(p.compareTo(c)!=0){/*check if user entered the same password twice*/
                    out.print("<p style=\"color:red\">Passwords do not match.</p>");
                    rd=request.getRequestDispatcher("/WEB-INF/jsp/createaccount.jsp");
                    rd.include(request,response);
                    out.close();
                    return;
                }
                
                try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml")) {
            UsersDAO usersDAO = context.getBean(UsersDAO.class);
                
                    Boolean mayExist=usersDAO.userCheck(n);

                    if(mayExist==true){
                        out.print("<p style=\"color:red\">Username already in use.</p>");
                        rd=request.getRequestDispatcher("/WEB-INF/jsp/createaccount.jsp");
                        rd.include(request,response);    
                        out.close();
                        return;
                    }

                    mayExist = usersDAO.emailCheck(e);

                    if(mayExist==true){
                        out.print("<p style=\"color:red\">Account already assigned to this email address.</p>");
                        rd=request.getRequestDispatcher("/WEB-INF/jsp/createaccount.jsp");
                        rd.include(request,response);
                        out.close();
                        return;
                    }
                    
                    Users newUser=new Users();
                    newUser.setUsername(n);
                    newUser.setPassword(p);
                    newUser.setEmail(e);
                    usersDAO.addUser(newUser);

                    
                    rd=request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
                    out.print("<p style=\"color:red\">Account created</p>");
                    rd.forward(request,response);
                    

                }//end try context  
                out.close();
            }//end try PrintWriter
        }//end doPost
}//end class