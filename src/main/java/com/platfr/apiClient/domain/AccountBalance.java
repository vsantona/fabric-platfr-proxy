package com.platfr.apiClient.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccountBalance {

    private String date;
    private Double balance;
    private Double availableBalance;
    private String currency;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString(){
        return "Date : " + date + "Balance : " + balance + "AvailableBalance : " + availableBalance + "Currency : " + currency;
    }
}
