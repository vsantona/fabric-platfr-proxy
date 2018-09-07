package com.platfr.apiClient.services;

import com.platfr.apiClient.dto.accountBalance.AccountBalanceResponse;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ApiClientService {

    @Value("${fabricl.platform.service.accountBalance}")
    private String accountBalancePath;

    @Value("${fabricl.platform.service.createSTCOrder}")
    private String createSTCOrderPath;

    public ResponseEntity<AccountBalanceResponse> getAccountBalance(Long accountId) {
        RestTemplate rt = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", getHeaders());
        String url = accountBalancePath;
        return rt.exchange(url, HttpMethod.GET, entity, AccountBalanceResponse.class ,accountId);
    }

    public void createSTCOrder(CreateSTCOrderRequest request){
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(request,headers);
        String url = createSTCOrderPath;
        ResponseEntity<String> out = rt.exchange(url, HttpMethod.POST, entity, String.class);

    }




    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Auth-Schema", "S2S");
        return headers;
    }

}
