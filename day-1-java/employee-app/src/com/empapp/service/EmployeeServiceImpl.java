package com.empapp.service;

import com.empapp.model.Employee;


public class EmployeeServiceImpl implements EmployeeService {


    @Override
    public void saveEmployee(Employee employee) {
        System.out.println(employee.getName()+" is saved");
    }

    public Employee getEmployee(String name){
        return null;
    }


}
