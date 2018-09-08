package com.platfr.apiClient.dto.createSTCOrder;

import com.platfr.apiClient.domain.STCOrder;
import com.platfr.apiClient.domain.Status;

import java.io.Serializable;
import java.util.List;

/**
 * Response of AccountBalance service
 *
 */
public class CreateSTCOrderResponse implements Serializable {
    Status status;
    List errors;
    STCOrder [] payload;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List getErrors() {
        return errors;
    }

    public void setErrors(List errors) {
        this.errors = errors;
    }

    public STCOrder [] getPayload() {
        return payload;
    }

    public void setPayload(STCOrder [] payload) {
        this.payload = payload;
    }
}
