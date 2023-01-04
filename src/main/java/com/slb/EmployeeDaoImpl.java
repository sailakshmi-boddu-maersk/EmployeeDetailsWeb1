package com.slb;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDaoImpl implements EmployeeDao{
	public static Connection connection=null;
	public ResultSet resultSet;
	Employee emp;
	public void connectionDb() {
		try {
					
			Class.forName("org.postgresql.Driver");
			connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","2323");
			if(connection!=null) {
				System.out.println("connection established succesfully");	
			}
			else {
				System.out.println("Failed  to connect");
			}
		}
		catch(Exception e) {
			System.out.println(e);
	    }
	}
	
	public void createEmpRecord(Employee emp) {
		try {
			  
			if(!addressExists(emp.addressId)){
				insertAddressRecord(emp.addressId,emp.address);
			}
			String query="insert into employees(first_name,last_name,salary,address_id) values(?,?,?,?)";
	        PreparedStatement preparedStatement=connection.prepareStatement(query);
//	        preparedStatement.setInt(1,emp.getId());
			preparedStatement.setString(1,emp.getFirstName());
			preparedStatement.setString(2,emp.getLastName());
			preparedStatement.setFloat(3,emp.getSalary());
			preparedStatement.setInt(4,emp.getAddressId());
			
            
			int rows=preparedStatement.executeUpdate();
			if(rows>0) {
				System.out.println("Record inserted sucessfully!!");
			}
			else {
				System.out.println("unable to insert");
			}
	        
			
		}
			catch(Exception e) {
				System.out.println(e);
			}
			
		
		
	}
	public List<Employee>selectEmpRecords() {
		List<Employee> empList=new ArrayList<>();
		try {
			String s="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
					+ "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id";
				
			Statement statement=connection.createStatement();
			resultSet=statement.executeQuery(s);
			
			while(resultSet.next()) {
				emp=new Employee();
			emp.setId(Integer.parseInt(resultSet.getString(1)));
			emp.setFirstName(resultSet.getString(2));
			emp.setLastName(resultSet.getString(3));
			emp.setSalary(Float.parseFloat(resultSet.getString(4)));
			emp.setAddressId(Integer.parseInt(resultSet.getString(5)));
			empList.add(emp);
			} 
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return empList;
		
	}
	public Employee selectEmp(int empId) {
		try {
			String s="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
					+ "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id where emp_id="+empId;
		    Statement statement=connection.createStatement();
			resultSet=statement.executeQuery(s);
				
			if(resultSet.next()) {
				emp=new Employee();
				emp.setId(Integer.parseInt(resultSet.getString(1)));
				emp.setFirstName(resultSet.getString(2));
				emp.setLastName(resultSet.getString(3));
				emp.setSalary(Float.parseFloat(resultSet.getString(4)));
				emp.setAddressId(Integer.parseInt(resultSet.getString(5)));
				emp.setAddress(resultSet.getString(6));
				
			}
			else {
				return null;
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return emp;	
	}
	public void updateEmp(Employee emp) {
		
		try {
			if(!addressExists(emp.addressId)){
				insertAddressRecord(emp.addressId,emp.address);
			}
			PreparedStatement statement = connection.prepareStatement("update employees set first_Name = ?,last_name= ?, salary =? ,address_id=? where emp_id = ?;"); 
			statement.setString(1, emp.getFirstName());
			statement.setString(2, emp.getLastName());
			statement.setFloat(3, emp.getSalary());
			statement.setInt(4, emp.getAddressId());
			statement.setInt(5, emp.getId());
			boolean rowUpdated = statement.executeUpdate() > 0;
			if(rowUpdated)
				System.out.println("Updated User:"+emp.getId());
			else
				System.out.println("Unable to upload user");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
	}
	public boolean deleteEmpRecord(int empId) {
		try {
			String sql="delete from employees where emp_id= "+empId;
			PreparedStatement preparedstatement=connection.prepareStatement(sql);
		    int rows=preparedstatement.executeUpdate();
		    if(rows>0) {
		    	System.out.println("record deleted successfully!!");
		    }
		    else {
		    	System.out.println("something went wrong!!");
		    	return false;
		    }
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return true;
	 }
	public boolean addressExists(int addressId) {
		try {
			String s="select * from address where ad_id="+addressId;
		    Statement statement=connection.createStatement();
			resultSet=statement.executeQuery(s);
				
			if(resultSet.next()) {
				return true;
			}
				
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return false;
		
	}
	public void insertAddressRecord(int empId,String address) {
		try {
			String query="insert into address(ad_id,address) values(?,?)";
	        PreparedStatement preparedStatement=connection.prepareStatement(query);
	        preparedStatement.setInt(1,empId);
			preparedStatement.setString(2,address);
			int rows=preparedStatement.executeUpdate();
			if(rows>0) 
				System.out.println("address row inserted successfully!!");
			else
				System.out.println("unable to insert!!");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	
	}
	public List<Employee>selectEmpByName(String firstName) {
		List<Employee> empList=new ArrayList<>();
		try {
		    String s="select emp_id,first_name,last_name,salary,address_id from employees where employees.first_name='"+firstName+"';";
//			String s="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
//					+ "FROM employee emp LEFT JOIN address ad ON emp.address_id =ad.ad_id where emp.first_name= "+firstName;

			Statement statement=connection.createStatement();
//		    PreparedStatement statement = connection.prepareStatement(s);
//		    statement.setString(1, firstName);
			resultSet=statement.executeQuery(s);
			System.out.println("result record ids:");
			while(resultSet.next()) {
				emp=new Employee();
			System.out.println(Integer.parseInt(resultSet.getString(1)));
			emp.setId(Integer.parseInt(resultSet.getString(1)));
			emp.setFirstName(resultSet.getString(2));
			emp.setLastName(resultSet.getString(3));
			emp.setSalary(Float.parseFloat(resultSet.getString(4)));
			emp.setAddressId(Integer.parseInt(resultSet.getString(5)));
			empList.add(emp);
			} 
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return empList;
		
	} 
}
