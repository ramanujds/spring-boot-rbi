package com.empapp;

import com.empapp.model.Employee;
import com.empapp.model.Manager;

public class EmployeeApp {

    public static void main(String[] args) {
        Employee employee = new Employee("John",50000);
        employee.printEmployee();

        Employee manager = new Manager("Robert",80000,10);
        manager.printEmployee();


    }

}
