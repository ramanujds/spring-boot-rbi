package com.empapp.service;

import com.empapp.exception.EmployeeNotFoundException;
import com.empapp.exception.InvalidEmployeeException;
import com.empapp.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeServiceMapBasedImpl implements EmployeeService {


    Map<String, Employee> employees = new HashMap<>();

    @Override
    public void saveEmployee(Employee employee) throws InvalidEmployeeException {
        if (employee.getName().length()<3){
            throw new InvalidEmployeeException("Employee name must be at least 3 characters");
        }
        if (employee.getSalary()<=0){
            throw new InvalidEmployeeException("Salary must be greater than 0");
        }
        employees.put(employee.getName(), employee);
        System.out.println(employee.getName()+" is saved");
    }

    public Employee getEmployee(String name){
        var emp = employees.get(name);
        if (emp == null){
            throw new EmployeeNotFoundException("Employee not found");
        }
        return emp;
    }

    @Override
    public void deleteEmployee(String name) {
        employees.remove(name);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }


}
