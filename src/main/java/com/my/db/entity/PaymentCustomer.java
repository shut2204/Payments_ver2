package com.my.db.entity;

public class PaymentCustomer {

    private int id_payment;
    private int id_card;
    private int amount;
    private int status_payment;

    private String date_of_payment;

    public int getId_payment() {
        return id_payment;
    }

    public void setId_payment(int id_payment) {
        this.id_payment = id_payment;
    }

    public int getId_card() {
        return id_card;
    }

    public void setId_card(int id_card) {
        this.id_card = id_card;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus_payment() {
        return status_payment;
    }

    public void setStatus_payment(int status_payment) {
        this.status_payment = status_payment;
    }

    public String getDate_of_payment() {
        return date_of_payment;
    }

    public void setDate_of_payment(String date_of_payment) {
        this.date_of_payment = date_of_payment;
    }
}
