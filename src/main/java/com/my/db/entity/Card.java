package com.my.db.entity;

public class Card {

    private long idcard;
    private int idcustomer;
    private int balance;
    private String name_card;
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
}
