package servlets;

import dao.UsersDAO;
import hibernate.dataobjects.Users;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class LoginServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        response.setContentType("text/html");  
        try (PrintWriter out = response.getWriter()) {
            String n=request.getParameter("username");
            String p=request.getParameter("userpass");
            
            try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml")) {
                UsersDAO usersDAO = context.getBean(UsersDAO.class);
                Users loginUser=usersDAO.login(n, p);
                if(loginUser!=null){
                    HttpSession session = request.getSession();
                    session.setAttribute("name", loginUser.getUsername());
                    session.setAttribute("userid", loginUser.getUserId());
                    RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/jsp/loginwelcome.jsp");
                    rd.forward(request,response);
                }
                else{
                out.print("<p style=\"color:red\">Incorrect username or password.</p>");
                RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
                rd.include(request,response);
                }
             }
            out.close();
            
        }  
    }
    

} 