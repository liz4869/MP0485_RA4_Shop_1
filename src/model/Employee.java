/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Logable;

public class Employee extends Person implements Logable {

    public static final int EMPLOYEE_ID = 123;
    public static final String PASSWORD = "test";

    private int employeeId;
    private String password;

    public Employee(String name, int employeeId, String password) {
        super(name);
        this.employeeId = employeeId;
        this.password = password;
    }

    @Override
    public boolean login(int user, String password) {
        return user == EMPLOYEE_ID && password.equals(PASSWORD);
    }
}
