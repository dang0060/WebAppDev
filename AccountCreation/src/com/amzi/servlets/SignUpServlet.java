package com.amzi.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amzi.dao.UserDao;

public class SignUpServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
		int status;
		RequestDispatcher rd=null;
		
        response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        String n=request.getParameter("username");  
        String p=request.getParameter("userpass"); 
        String c=request.getParameter("confirmpass");
        String e=request.getParameter("useremail");
        
        if(p.compareTo(c)!=0){/*check if user entered the same password twice*/
        	out.print("<p style=\"color:red\">Passwords do not match.</p>"); 
        	rd=request.getRequestDispatcher("createaccount.jsp");  
            rd.include(request,response);
            out.close(); 
            return;
        }
        
        
        status=UserDao.insertNewUser(n, p, e);
        
        switch(status){
        case 0:
        	out.print("<p style=\"color:red\">Account created</p>"); 
        	rd=request.getRequestDispatcher("login.jsp");  
            rd.forward(request,response);  
            break;
        case 1:
        	out.print("<p style=\"color:red\">Exception thrown, try again.</p>"); 
        	rd=request.getRequestDispatcher("createaccount.jsp");  
            rd.include(request,response); 
        	break;
        case 2:
        	out.print("<p style=\"color:red\">Username already in use.</p>"); 
        	rd=request.getRequestDispatcher("createaccount.jsp");  
            rd.include(request,response);
            break;
        case 3:
        	out.print("<p style=\"color:red\">Account already assigned to this email address.</p>"); 
        	rd=request.getRequestDispatcher("createaccount.jsp");  
            rd.include(request,response);
        }
        
        

        out.close();  
    }  
}
