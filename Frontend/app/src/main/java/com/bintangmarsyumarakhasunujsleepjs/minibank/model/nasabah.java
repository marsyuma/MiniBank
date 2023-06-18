package com.bintangmarsyumarakhasunujsleepjs.minibank.model;

public class nasabah {
    private int user_id;
    private String name;
    private String address;
    private String phonenumber;
    private String email;
    private String password;
    private int balance;
    private String job;

    /**
     * Get the value of user_id
     * @return the value of user_id
     *
     */
    public int getUserId() {
        return user_id;
    }

    /**
     * Get the value of password
     *
     */
    public String getBalance() {
        // parse balance to string
        return Integer.toString(balance);
    }

    public String toString() {
        return "User ID: " + user_id + "\nBalance: " + balance + "\nName: " + name + "\nAddress: " + address + "\nPhone Number: " + phonenumber + "\nEmail: " + email + "\nPassword: " + password + "\nJob: " + job;
    }
}
