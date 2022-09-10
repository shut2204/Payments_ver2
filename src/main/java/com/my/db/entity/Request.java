package com.my.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {

    private static final long serialVersionUID = 8238127948129L;
    private  int idCustomer;

    private String idCard;

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return idCustomer == request.idCustomer && idCard.equals(request.idCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCustomer, idCard);
    }

    @Override
    public String toString() {
        return "Request{" +
                "idCustomer=" + idCustomer +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
