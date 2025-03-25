package com.empapp.model;

public final class Developer extends Employee {

    private String programmingLanguage;

    public Developer(String name, double salary, String programmingLanguage) {
        super(name, salary);
        this.programmingLanguage = programmingLanguage;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public void printEmployee() {
        System.out.println("Developer Details");
        System.out.println("Name: " + getName());
        System.out.println("Salary: " + getSalary());
        System.out.println("Programming Language: " + getProgrammingLanguage());
    }
}
