package com.slb;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebFilter("/*")
public class loginFilter extends HttpFilter implements Filter {
       
    public loginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
//	    System.out.println("in login filter");
	    
		HttpServletRequest req=(HttpServletRequest)request;
	    HttpServletResponse res=(HttpServletResponse)response;
	    String action = req.getServletPath();
		HttpSession session=req.getSession();
//		System.out.println(req.getRequestURI());
		System.out.println("action: "+action);
		if(action.equals("/index.jsp")) {
			chain.doFilter(request, response);
		}
		else {
	    if(action.equals("/login")) {
	    	String uname=req.getParameter("uname");
			String pass=req.getParameter("password");
			session.setAttribute("uname",uname );
			session.setAttribute("pass",pass);
//			System.out.println(uname);
//			System.out.println(pass);
			res.sendRedirect("homePage.jsp");
	    }
	    else if(action.equals("/logout")) {
	    	session.setAttribute("uname","");
	    	session.setAttribute("pass","");
	    	res.sendRedirect("index.jsp");
	    }
	    else {
//	     System.out.println("in else condition");
	     String uname="";
	     String pass="";
		 uname=(String) session.getAttribute("uname");
		 pass=(String) session.getAttribute("pass");
		 
		if(uname.equals("sai") && pass.equals("1234")) {
		chain.doFilter(request, response);
		}
		else 
			res.sendRedirect("index.jsp");
	    }
		} 
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
