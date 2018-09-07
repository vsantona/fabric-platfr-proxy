package com.platfr.apiClient.dto.accountBalance;

import java.util.List;

public class AccountBalanceResponse implements Serializable{


    String status;
    List errors;
    AccountBalance payload;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List getErrors() {
        return errors;
    }

    public void setErrors(List errors) {
        this.errors = errors;
    }

    public AccountBalance getPayload() {
        return payload;
    }

    public void setPayload(AccountBalance payload) {
        this.payload = payload;
    }
}
