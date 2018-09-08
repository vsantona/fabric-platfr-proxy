package com.platfr.apiClient.dto.createSTCOrder;

import java.io.Serializable;

/**
 * Request of AccountBalance service
 *
 */
public class CreateSTCOrderRequest implements Serializable {
    private String receiverName;
    private String description;
    private String currency;
    private String amount;
    private String executionDate;

    public CreateSTCOrderRequest() {
    }

    public CreateSTCOrderRequest(String receiverName, String amount, String currency, String description, String executionDate) {
        this.receiverName = receiverName;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.executionDate = executionDate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }
}
