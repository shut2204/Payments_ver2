package com.my.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class Payment implements Serializable {

    private static final long serialVersionUID = 65675768372648127L;
    private int idcustomer;

    private int idcustomer2;

    private int id_payment;
    private String id_card;
    private int amount;
    private String status;
    private String to_card;
    private String date_of_payment;

    public int getIdcustomer2() {
        return idcustomer2;
    }

    public void setIdcustomer2(int idcustomer2) {
        this.idcustomer2 = idcustomer2;
    }

    public int getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(int idcustomer) {
        this.idcustomer = idcustomer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTo_card() {
        return to_card;
    }

    public void setTo_card(String to_card) {
        this.to_card = to_card;
    }

    public int getId_payment() {
        return id_payment;
    }

    public void setId_payment(int id_payment) {
        this.id_payment = id_payment;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate_of_payment() {
        return date_of_payment;
    }

    public void setDate_of_payment(String date_of_payment) {
        this.date_of_payment = date_of_payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id_payment == payment.id_payment && status.equals(payment.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_payment, status);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "idcustomer=" + idcustomer +
                ", idcustomer2=" + idcustomer2 +
                ", id_payment=" + id_payment +
                ", id_card='" + id_card + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", to_card='" + to_card + '\'' +
                ", date_of_payment='" + date_of_payment + '\'' +
                '}';
    }
}
