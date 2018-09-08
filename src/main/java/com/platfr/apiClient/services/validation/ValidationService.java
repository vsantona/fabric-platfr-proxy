package com.platfr.apiClient.services.validation;

import com.platfr.apiClient.dto.accountBalance.AccountBalanceRequest;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to validate request to Fabrick API
 *
 */
@Service
public class ValidationService {

    /**
     * Validate Request To Account Balance WS
     * @param request
     * @return
     */
    public List<String> validate (AccountBalanceRequest request){
        List<String> errors = new ArrayList<>();
        if (request.getAccountId() == null || request.getAccountId() == 0){
            errors.add("Validation Error - AccountId not found or not valid");
        }
        return errors;
    }

    /**
     * Validate Request To Create STC Order
     * @param request
     * @param accountId
     * @return
     */
    public List<String> validate (CreateSTCOrderRequest request, Long accountId){
        List<String> errors = new ArrayList<>();
        if (accountId == null || accountId == 0){
            errors.add("Validation Error - AccountId not found or not valid");
        }
        if (request.getDescription() == null || request.getDescription().isEmpty()){
            errors.add("Validation Error - Description not found or not valid");
        }
        if (request.getReceiverName() == null || request.getReceiverName().isEmpty()){
            errors.add("Validation Error - Receiver Name not found or not valid");
        }
        if (request.getAmount() == null || request.getAmount().isEmpty()){
            errors.add("Validation Error - Amount not found or not valid");
        }
        if (request.getCurrency() == null || request.getCurrency().isEmpty()){
            errors.add("Validation Error - Currency not found or not valid");
        }
        if (request.getExecutionDate() == null || request.getExecutionDate().isEmpty()){
            errors.add("Validation Error - Execution Date not found or not valid");
        }
        return errors;
    }
}
