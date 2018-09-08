package com.platfr.apiClient.dto.accountBalance;


import java.io.Serializable;

/**
 * Request of AccountBalance service
 *
 */
public class AccountBalanceRequest implements Serializable {

    private Long accountId;

    public AccountBalanceRequest(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
