package com.empapp.service;

import com.empapp.exception.InvalidEmployeeException;
import com.empapp.model.Employee;

import java.util.List;

public interface EmployeeService {
   void saveEmployee(Employee employee) throws InvalidEmployeeException;
   Employee getEmployee(String name);
   void deleteEmployee(String name);
   List<Employee> getAllEmployees();
}
