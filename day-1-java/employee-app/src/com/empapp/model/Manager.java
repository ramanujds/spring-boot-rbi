package com.empapp.model;

import com.empapp.model.Employee;

public class Manager extends Employee {

    private int teamSize;

    public Manager(String name, double salary, int teamSize) {
        super(name, salary);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }


    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }


    @Override
    public void printEmployee() {
        super.printEmployee();
        System.out.println("Team Size: " + teamSize);
    }
}
