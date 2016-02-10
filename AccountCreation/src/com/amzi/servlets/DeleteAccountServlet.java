package com.amzi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amzi.dao.UserDao;

public class DeleteAccountServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        HttpSession session = request.getSession();
        

        String n=(String)session.getAttribute("name");
        String p=request.getParameter("userpass"); 
        if(n==null){
        	out.print("<p style=\"color:red\">Not logged in.</p>"); 
        	RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
        	rd.forward(request,response);  
        	return;
        }
        
        
        
        if(UserDao.deleteUser(n, p)){  
        	session.removeAttribute("name");
        	out.printf("<p style=\"color:red\">%s deleted. Create new user?</p>",n);  
            RequestDispatcher rd=request.getRequestDispatcher("createaccount.jsp");  
            rd.forward(request,response);  
        }  
        else{  
            out.print("<p style=\"color:red\">Incorrect password.</p>");  
            RequestDispatcher rd=request.getRequestDispatcher("deleteaccount.jsp");  
            rd.include(request,response);  
        }  

        out.close();  
    }  
}
