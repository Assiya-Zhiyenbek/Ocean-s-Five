package com.qcb.financemanage;

// A POJO class for Transaction

import com.google.firebase.Timestamp;

public class Transaction {

    private String sender;
    private String receiver;
    private Timestamp date;
    private int amount;

    public Transaction(String sender, String receiver, Timestamp date, int amount) {
        this.setSender(sender);
        this.setReceiver(receiver);
        this.setDate(date);
        this.setAmount(amount);
    }

    public Transaction() {
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
