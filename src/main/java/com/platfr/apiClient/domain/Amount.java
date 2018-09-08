package com.platfr.apiClient.domain;

import java.io.Serializable;

public class Amount implements Serializable {

    private String receiverAmount;
    private String receiverCurrency;
    private String exchangeRate;

    public String getReceiverAmount() {
        return receiverAmount;
    }

    public void setReceiverAmount(String receiverAmount) {
        this.receiverAmount = receiverAmount;
    }

    public String getReceiverCurrency() {
        return receiverCurrency;
    }

    public void setReceiverCurrency(String receiverCurrency) {
        this.receiverCurrency = receiverCurrency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
