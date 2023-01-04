package com.slb;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
	EmployeeDaoImpl empDaoImpl=new EmployeeDaoImpl();
	public void connectionDb() {
		empDaoImpl.connectionDb();
	}
	public void createEmpRecord(Employee emp) {
		empDaoImpl.createEmpRecord(emp);
	}
	public List<Employee> selectEmpRecords(){
		List<Employee> emp= new ArrayList<>();
		emp=empDaoImpl.selectEmpRecords();
		return emp;
	}
	public Employee selectEmp(int empId) {
		Employee emp=new Employee();
		emp=empDaoImpl.selectEmp(empId);
		return emp;
		
	}
	public void updateEmp(Employee emp) {
		empDaoImpl.updateEmp(emp);
	}
	public boolean deleteEmpRecord(int empId) {
		return empDaoImpl.deleteEmpRecord(empId);
	}
	public boolean addressExists(int addressId) {
		boolean isExists=empDaoImpl.addressExists(addressId);
		return isExists;
	}
	public void insertAddressRecord(int empId,String address) {
		empDaoImpl.insertAddressRecord(empId, address);
	}
	public List<Employee>selectEmpByName(String firstName) {
		List<Employee> emp= new ArrayList<>();
		emp=empDaoImpl.selectEmpByName(firstName);
		return emp;
	}
}
