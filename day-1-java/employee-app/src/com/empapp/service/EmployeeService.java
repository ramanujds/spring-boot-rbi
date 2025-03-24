package com.empapp.service;

import com.empapp.model.Employee;

public interface EmployeeService {
   void saveEmployee(Employee employee);
   Employee getEmployee(String name);
}
