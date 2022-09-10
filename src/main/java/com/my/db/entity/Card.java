package com.my.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class Card implements Serializable {

    private static final long serialVersionUID = 231242342341234L;

    private long idcard;
    private int idcustomer;
    private int balance;
    private String name_card;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber_card() {
        String number_card = String.valueOf(idcard);
        return   number_card.substring(0,4)
               + " "
               + number_card.substring(4,8)
               + " "
               + number_card.substring(8,12)
               + " "
               + number_card.substring(12);
    }
    public long getIdcard() {
        return idcard;
    }

    public void setIdcard(long idcard) {
        this.idcard = idcard;
    }

    public int getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(int idcustomer) {
        this.idcustomer = idcustomer;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName_card() {
        return name_card;
    }

    public void setName_card(String name_card) {
        this.name_card = name_card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return idcard == card.idcard && status.equals(card.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcard, status);
    }

    @Override
    public String toString() {
        return "Card{" +
                "idcard=" + idcard +
                ", idcustomer=" + idcustomer +
                ", balance=" + balance +
                ", name_card='" + name_card + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
