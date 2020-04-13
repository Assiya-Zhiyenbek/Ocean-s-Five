package com.qcb.financemanage;


import androidx.annotation.Keep;

// POJO class for Account
@Keep
public class Account {

    private String account_id;
    private String account_name;
    private String open_date;
    private String customer_id;
    private int balance;
    private int account_number;



    public Account(String account_id, String account_name, String open_date, String customer_id, int balance, int account_number){
        this.setAccount_id(account_id);
        this.setAccount_name(account_name);
        this.setOpen_date(open_date);
        this.setCustomer_id(customer_id);
        this.setBalance(balance);
        this.setAccount_number(account_number);
    }

    public Account(){

    }


    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void setAccount_number(int account_number) {
        this.account_number = account_number;
    }
}
