/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Payable;

public class Client extends Person implements Payable {

    public static final int MEMBER_ID = 456;
    public static final double BALANCE = 50.00;

    private int memberId;
    private double balance;

    public Client(String name) {
        super(name);
        this.memberId = MEMBER_ID;
        this.balance = BALANCE;
    }

    @Override
    public boolean pay(double amount) {
        balance -= amount;
        return balance >= 0;
    }

    public double getBalance() {
        return balance;
    }
}

