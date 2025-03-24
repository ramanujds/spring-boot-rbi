package com.empapp.service;

import com.empapp.exception.InvalidEmployeeException;
import com.empapp.model.Employee;

public interface EmployeeService {
   void saveEmployee(Employee employee) throws InvalidEmployeeException;
   Employee getEmployee(String name);
}
