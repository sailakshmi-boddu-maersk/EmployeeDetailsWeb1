package com.slb;

import java.util.List;

public interface EmployeeDao {
	void connectionDb();
	void createEmpRecord(Employee emp);
	List<Employee>selectEmpRecords();
	Employee selectEmp(int empId);
	void updateEmp(Employee emp);
	boolean deleteEmpRecord(int empId);
	boolean addressExists(int addressId);
	void insertAddressRecord(int empId,String address);
	public List<Employee>selectEmpByName(String firstName);
}
