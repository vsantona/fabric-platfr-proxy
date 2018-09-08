package com.platfr.apiClient.services;

import com.platfr.apiClient.dto.accountBalance.AccountBalanceRequest;
import com.platfr.apiClient.dto.accountBalance.AccountBalanceResponse;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderRequest;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Service to invoke the Fabrick Platform API
 *
 */
@Service
public class ApiClientService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${fabricl.platform.service.accountBalance}")
    private String accountBalancePath;

    @Value("${fabricl.platform.service.createSTCOrder}")
    private String createSTCOrderPath;

    /**
     * Invoke Account Balance service to retrieves the balance of a cash account
     * @param request
     * @return
     */
    public ResponseEntity<AccountBalanceResponse> getAccountBalance(AccountBalanceRequest request) {
        RestTemplate rt = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", getHeaders(null));
        String url = accountBalancePath;
        return rt.exchange(url, HttpMethod.GET, entity, AccountBalanceResponse.class ,request.getAccountId());
    }

    /**
     * Invoke Create STC Order service to creates a SCT payment order
     * @param request
     * @param accountId
     * @return
     */
    public ResponseEntity<CreateSTCOrderResponse> createSTCOrder(Long accountId, CreateSTCOrderRequest request){
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = getHeaders(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(request,headers);
        String url = createSTCOrderPath;
        return rt.exchange(url, HttpMethod.POST, entity, CreateSTCOrderResponse.class, accountId);
    }


    /**
     * Populate Headers to HTTP request
     * @param contentType: if the value is not null I put it in the headerss
     * @return
     */
    private HttpHeaders getHeaders(MediaType contentType){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Auth-Schema", "S2S");
        if(contentType != null){
            headers.setContentType(contentType);
        }
        return headers;
    }

}
