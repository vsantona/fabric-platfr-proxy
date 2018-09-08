package com.platfr.apiClient.controllers;

import com.platfr.apiClient.dto.accountBalance.AccountBalanceRequest;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderRequest;
import com.platfr.apiClient.services.ApiClientService;
import com.platfr.apiClient.services.validation.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiClientController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApiClientService callerService;

    @Autowired
    ValidationService validationService;

    @RequestMapping("/")
    public String index() {
        return "Client Startup OK";
    }

    @GetMapping("/getAccountBalance/{accountId}")
    public ResponseEntity<?> getAccountBalance(@PathVariable("accountId")  Long accountId){
        AccountBalanceRequest request = new AccountBalanceRequest(accountId);
        List errors = validationService.validate(request);
        if (errors.size() > 0){
           return new ResponseEntity<List<String>>(errors,HttpStatus.BAD_REQUEST);
        }
        try{
            return callerService.getAccountBalance(request);
        }catch (Exception e){
            log.error("Error during invoke Get Account Balance WS" , e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/createSTCOrder/{accountId}/{receiverName}/{amount}/{currency}/{description}/{executionDate}")
    public ResponseEntity<?> createSTCOrder(@PathVariable("accountId")  Long accountId, @PathVariable("receiverName")  String receiverName,
                                  @PathVariable("amount")  String amount,  @PathVariable("currency")  String currency,
                                  @PathVariable("description")  String description, @PathVariable("executionDate")  String executionDate)
    {
        try{
            CreateSTCOrderRequest request = new CreateSTCOrderRequest(receiverName,amount,currency,description,executionDate);
            List errors = validationService.validate(request, accountId);
            if (errors.size() > 0){
                return new ResponseEntity<List<String>>(errors,HttpStatus.BAD_REQUEST);
            }
            return callerService.createSTCOrder(accountId,request);
        }catch (Exception e){
            log.error("Error during invoke Get Account Balance WS" , e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createSTCOrder/{accountId}")
    public ResponseEntity<?> createSTCOrder(@PathVariable("accountId")  Long accountId, @RequestBody CreateSTCOrderRequest request)
    {
        try{
            List errors = validationService.validate(request, accountId);
            if (errors.size() > 0){
                return new ResponseEntity<List<String>>(errors,HttpStatus.BAD_REQUEST);
            }
            return callerService.createSTCOrder(accountId,request);
        }catch (Exception e){
            log.error("Error during invoke Get Account Balance WS" , e);
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

