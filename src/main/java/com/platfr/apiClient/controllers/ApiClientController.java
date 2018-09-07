package com.platfr.apiClient.controllers;

import com.platfr.apiClient.dto.accountBalance.AccountBalance;
import com.platfr.apiClient.dto.accountBalance.AccountBalanceResponse;
import com.platfr.apiClient.services.ApiClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiClientController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApiClientService callerService;

    @RequestMapping("/")
    public String index() {
        return "Client Startup OK";
    }

    @GetMapping("/getAccountBalance/{accountId}")
    public ResponseEntity<?> call(@PathVariable("accountId")  Long accountId){
        try{
            return callerService.getAccountBalance(accountId);
        }catch (Exception e){
            log.error("Error during invoke Get Account Balance WS" , e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

