package com.empapp;

import com.empapp.model.Developer;
import com.empapp.model.Employee;
import com.empapp.model.Manager;
import com.empapp.model.Person;
import com.empapp.service.EmployeeService;
import com.empapp.service.EmployeeServiceDBBasedImpl;
import com.empapp.service.EmployeeServiceImpl;

public class EmployeeApp {

    public static void main(String[] args) {

        Manager manager = new Manager("Robert",80000,10);

        Developer dev = new Developer("Mike",60000,"Java");

        EmployeeService service = new EmployeeServiceImpl();

        service.saveEmployee(dev);
        service.saveEmployee(manager);







    }

}
