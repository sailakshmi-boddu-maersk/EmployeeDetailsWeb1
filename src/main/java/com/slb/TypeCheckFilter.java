package com.slb;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class TypeCheckFilter extends HttpFilter implements Filter {  
    public TypeCheckFilter() {
        super();
        
        // TODO Auto-generated constructor stub
    }
    
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		System.out.println("in type check filter");
		HttpServletRequest req=(HttpServletRequest)request;
	    HttpServletResponse res=(HttpServletResponse)response;
	    String action = req.getServletPath();
	    
	    if(action.equals("/edit")||action.equals("/delete")) {
//	    	System.out.println("in edit condition of typecheck filter");
	    	String msg=isInt(req.getParameter("id"));
	    	if(msg=="")
	    		chain.doFilter(request, response);
	    	else {
	    		RequestDispatcher dispatcher = req.getRequestDispatcher("employeeId.jsp");
	    		req.setAttribute("msg",msg);
	    		dispatcher.forward(req, res);
	    	}
	    }
	    else if(action.equals("/insert")|| action.equals("/update")) {
//    	System.out.println("in inserrt /update condition od type check filter");
//	    	String id=isInt(req.getParameter("id"));
			String firstName =isString( req.getParameter("firstName"));
			String lastName = isString(req.getParameter("lastName"));
			String salary = isFloat(req.getParameter("salary"));
		    String addressId =isInt(req.getParameter("addressId"));
		    String address=isString(req.getParameter("address"));
	        if(firstName=="" && lastName=="" && salary=="" && addressId=="" && address=="") {
	        	chain.doFilter(request, response);
	        }
	        else {
//	        	System.out.println("in else cond of update filter");
	        	RequestDispatcher dispatcher = req.getRequestDispatcher("employee-form.jsp");
//	        	if(id=="")
//	        		req.setAttribute("empId",Integer.parseInt(request.getParameter("id")));
	        	if(firstName=="")
	        		req.setAttribute("firstName",request.getParameter("firstName"));
	        	if(lastName=="")
	        		req.setAttribute("lastName",request.getParameter("lastName"));
	        	if(salary=="")
	        		req.setAttribute("salary",Float.parseFloat(request.getParameter("salary")));
	        	if(addressId=="")
            		req.setAttribute("addressId",Integer.parseInt(request.getParameter("addressId")));
	        	if(address=="")
	        		req.setAttribute("address",request.getParameter("address"));
	        	req.setAttribute("empId",request.getParameter("id"));
	    		req.setAttribute("msg","please enter valid data in above empty fields");
	    		dispatcher.forward(request, response);
	        }
			
	    }
	    else if(action.equals("/getEmps")) {
	    	String msg=isString(req.getParameter("firstName"));
	    	String actionEditDelete=req.getParameter("action");
	    	if(msg=="")
	    		chain.doFilter(request, response);
	    	else {
	    		RequestDispatcher dispatcher = req.getRequestDispatcher("employeeName.jsp");
	    		req.setAttribute("msg",msg);
	    		req.setAttribute("actionEditDelete",actionEditDelete);
	    		dispatcher.forward(req, res);
	    	}
	    }
	    else
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	public String isInt(String i) {
        String msg="";
		
	try {
		int num1=Integer.parseInt(i);
		if(num1>0) {
			
		}
		else {
			msg=msg+"please enter positive value..";
		}
	}
	catch(Exception e){
		   msg=msg+"please enter numeric value..";
		System.out.println(e);
	}
		return msg;
    }
    
    public String isString(String name) {
    	String msg="";
    	try {
    	String s=name;
    		for (int i = 0; i < s.length(); i++)
            {
                char c = s.charAt(i);
                if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                   msg= "please enter string of alpha characters only";
                }
            }
    	}
    	catch(Exception e) {
    		msg="please enter valid name";
    	}
    	return msg;
    }
    
    public String isFloat(String i) {
        String msg="";
		
	try {
		float num1=Float.parseFloat(i);
		if(num1>0) {
			
		}
		else {
			msg=msg+"please enter positive value..";
		}
	}
	catch(Exception e){
		   msg=msg+"please enter numeric value..";
		System.out.println(e);
	}
		return msg;
    }
	

}
