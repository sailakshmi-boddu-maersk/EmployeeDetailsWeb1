package com.slb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/")
public class EmployeeControllerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private EmployeeServiceImpl empServiceImpl;
	
	public void init() {
		
		empServiceImpl= new EmployeeServiceImpl();
		empServiceImpl.connectionDb();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String action = request.getServletPath();
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertEmployee(request, response);
				break;
			case "/delete":
				deleteEmployee(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateEmployee(request, response);
				break;
			case "/empRec":
				getEmpRec(request,response);
			case "/getEmps":
				getEmpByName(request,response);
			default:
				listEmployee(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		List<Employee> listEmployee = empServiceImpl.selectEmpRecords();
		request.setAttribute("listEmployee", listEmployee);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Employee emp = empServiceImpl.selectEmp(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		request.setAttribute("empId",emp.id);
		request.setAttribute("firstName",emp.firstName);
		request.setAttribute("lastName",emp.LastName);
		request.setAttribute("salary",emp.salary);
		request.setAttribute("addressId",emp.addressId);
		request.setAttribute("address", emp.address);
//		request.setAttribute("employee", emp);
		dispatcher.forward(request, response);

	}

	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Float salary = Float.parseFloat(request.getParameter("salary"));
		int addressId = Integer.parseInt(request.getParameter("addressId"));
		String address=request.getParameter("address");
		Employee newEmp = new Employee(firstName,lastName,salary,addressId,address);
		empServiceImpl.createEmpRecord(newEmp);
		response.sendRedirect("list");
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Float salary = Float.parseFloat(request.getParameter("salary"));
		int addressId = Integer.parseInt(request.getParameter("addressId"));
		String address=request.getParameter("address");
		Employee emp = new Employee(id,firstName,lastName,salary,addressId,address);
		empServiceImpl.updateEmp(emp);
		response.sendRedirect("list");	
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		boolean isExists=empServiceImpl.deleteEmpRecord(id);
		if(isExists==false) {
			request.setAttribute("msg","Error,unable to find employee details!!"+"\n"+"please provide correct employee id");
			RequestDispatcher dispatcher = request.getRequestDispatcher("employeeId.jsp");
			dispatcher.forward(request, response);
		}
		else {
		response.sendRedirect("list");
		}
	}
	private void getEmpRec(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String action=request.getParameter("action");
		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeName.jsp");
		request.setAttribute("actionEditDelete",action);
		dispatcher.forward(request, response);	
	}
	private void getEmpByName(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String action=request.getParameter("action");
		String firstName=request.getParameter("firstName");
		List<Employee> listEmployee =empServiceImpl.selectEmpByName(firstName);
		if(listEmployee.isEmpty()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("employeeName.jsp");
			request.setAttribute("actionEditDelete",action);
			request.setAttribute("msg","Error,unable to find employee details!!"+"\n"+"please provide correct employee name..");
			dispatcher.forward(request, response);
		}
		else {
		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeListByName.jsp");
		request.setAttribute("actionEditDelete",action);
		request.setAttribute("listEmployeeByName", listEmployee);
		dispatcher.forward(request, response);
		}
	}
}
