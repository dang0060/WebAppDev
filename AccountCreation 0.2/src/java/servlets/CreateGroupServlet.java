/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.GroupsDAO;
import dao.UsersDAO;
import hibernate.dataobjects.Groups;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Alayna
 */
public class CreateGroupServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
	
        @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException { 
            RequestDispatcher rd;
            HttpSession session=request.getSession();
		
            response.setContentType("text/html");  
            try (PrintWriter out = response.getWriter()) {
                String g=request.getParameter("groupname");
                String p=request.getParameter("userpass");
                String n=(String)session.getAttribute("name");
                
                if(n==null){  
                    rd=request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
                    out.print("<p style=\"color:red\">Not logged in.</p>");
                    rd.forward(request,response);
                    return;
                }
            
                try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml")) {
                    GroupsDAO groupsDAO = context.getBean(GroupsDAO.class);
                    Groups newGroup=new Groups(g);
                    groupsDAO.addGroup(newGroup);
                    rd=request.getRequestDispatcher("/WEB-INF/jsp/deleteaccount.jsp");
                    rd.forward(request,response);
                }
            }
        }
}
