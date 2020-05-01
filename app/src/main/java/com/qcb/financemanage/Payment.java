package com.qcb.financemanage;


import com.google.firebase.Timestamp;

// A POJO class for Payment
public class Payment {

    private String payer;
    private String pay_for;
    private String type;
    private Timestamp date;
    private int amount;

    public Payment(String payer, String pay_for, String type, Timestamp date, int amount) {
        this.setPayer(payer);
        this.setPay_for(pay_for);
        this.setType(type);
        this.setDate(date);
        this.setAmount(amount);
    }

    public Payment() {
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPay_for() {
        return pay_for;
    }

    public void setPay_for(String pay_for) {
        this.pay_for = pay_for;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
