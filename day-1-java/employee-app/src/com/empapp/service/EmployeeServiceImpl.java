package com.empapp.service;

import com.empapp.exception.InvalidEmployeeException;
import com.empapp.model.Employee;


public class EmployeeServiceImpl implements EmployeeService {


    @Override
    public void saveEmployee(Employee employee) throws InvalidEmployeeException {
        if (employee.getName().length()<3){
            throw new InvalidEmployeeException("Employee name must be at least 3 characters");
        }
        if (employee.getSalary()<=0){
            throw new InvalidEmployeeException("Salary must be greater than 0");
        }
        System.out.println(employee.getName()+" is saved");
    }

    public Employee getEmployee(String name){
        return null;
    }


}
