package com.platfr.apiClient.controllers;

import com.platfr.apiClient.util.TestUtil;
import com.platfr.apiClient.domain.AccountBalance;
import com.platfr.apiClient.domain.Amount;
import com.platfr.apiClient.domain.STCOrder;
import com.platfr.apiClient.domain.Status;
import com.platfr.apiClient.dto.accountBalance.AccountBalanceRequest;
import com.platfr.apiClient.dto.accountBalance.AccountBalanceResponse;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderRequest;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderResponse;
import com.platfr.apiClient.services.ApiClientService;
import com.platfr.apiClient.services.validation.ValidationService;
import config.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.ws.http.HTTPException;
import static org.mockito.Matchers.any;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@WebMvcTest(ApiClientController.class)
public class ApiClientControllerTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MockMvc mvc;

    @InjectMocks
    ApiClientController controllerUnderTest;

    @MockBean
    private ApiClientService callerService;

    @MockBean
    private ValidationService validationService;

    //**************** Util Methods *************

    private AccountBalanceResponse getAccountBalanceResponse() {
        AccountBalanceResponse response =  new AccountBalanceResponse();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAvailableBalance(500.0);
        accountBalance.setBalance(100.0);
        accountBalance.setCurrency("EUR");
        accountBalance.setDate("12-07-2010");
        response.setPayload(accountBalance);
        response.setErrors(new ArrayList());
        response.setStatus("200");
        return response;
    }

    private CreateSTCOrderResponse getSTCOrderResponse() {
        CreateSTCOrderResponse response =  new CreateSTCOrderResponse();
        STCOrder order = new STCOrder();
        Amount amount = new Amount();
        amount.setReceiverAmount("test");
        amount.setExchangeRate("test");
        amount.setReceiverCurrency("test");
        order.setCro("cro");
        order.setAmount(amount);
        order.setOrderId("test");
        order.setDate("12-07-2010");
        order.setFeeType("test");
        order.setFees(new ArrayList<>());
        STCOrder [] orders = {order};
        Status status = new Status();
        status.setCode("200");
        response.setPayload(orders);
        response.setErrors(new ArrayList());
        response.setStatus(status);
        return response;
    }


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void clientStartUp()  throws Exception{
        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Client Startup OK"));
    }

    // ********** Account Balance Test ***************

    @Test
    public void givenValidAccountId_whenGetAccountBalance_thenReturnValidData()
            throws Exception {
        Long accountId = 111L;
        AccountBalanceResponse response = getAccountBalanceResponse();
        given(
                callerService.getAccountBalance(any(AccountBalanceRequest.class))
        ).willReturn(
                new ResponseEntity<>(response, HttpStatus.OK)
        );
        mvc.perform(get("/getAccountBalance/"+accountId)
                .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(response.getStatus())))
                .andExpect(jsonPath("$.errors", is(response.getErrors())))
                .andExpect(jsonPath("$.payload.date", is(response.getPayload().getDate())))
                .andExpect(jsonPath("$.payload.availableBalance", is(response.getPayload().getAvailableBalance())))
                .andExpect(jsonPath("$.payload.balance", is(response.getPayload().getBalance())))
                .andExpect(jsonPath("$.payload.currency", is(response.getPayload().getCurrency())));
    }

    @Test
    public void givenValidationErrors_whenGetAccountBalance_thenReturnBadRequest()
            throws Exception {
        Long accountId = 111L;
        List<String> errors = new ArrayList<>(Arrays.asList("Validation Error - AccountId not found or not valid"));
        given(
                validationService.validate(any(AccountBalanceRequest.class))
        ).willReturn(
                errors
        );
        mvc.perform(get("/getAccountBalance/"+accountId)
                .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenException_whenGetAccountBalance_thenInternalError()
            throws Exception {
        Long accountId = 111L;

        given(
                callerService.getAccountBalance(any(AccountBalanceRequest.class))
        ).willThrow(
                new HTTPException(404)
        );
        mvc.perform(get("/getAccountBalance/"+accountId)
                .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isInternalServerError());
    }

    // ********** Create Src Order Test ***************

    @Test
    public void givenValidRequest_whenPOSTCreateSTCOrder_thenReturnValidData()
            throws Exception {
        Long accountId = 111L;
        CreateSTCOrderResponse response = getSTCOrderResponse();
        CreateSTCOrderRequest request = new CreateSTCOrderRequest("foo", "100", "EUR", "Desc", "10-10-2018");
        given(
                callerService.createSTCOrder(any(Long.class),any(CreateSTCOrderRequest.class))
        ).willReturn(
                new ResponseEntity<>(response, HttpStatus.OK)
        );
        mvc.perform(post("/createSTCOrder/"+accountId)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.status.code", is(response.getStatus().getCode())))
                        .andExpect(jsonPath("$.errors", is(response.getErrors())))
                        .andExpect(jsonPath("$.payload[0].amount.receiverAmount", is(response.getPayload()[0].getAmount().getReceiverAmount())))
                        .andExpect(jsonPath("$.payload[0].amount.exchangeRate", is(response.getPayload()[0].getAmount().getExchangeRate())))
                        .andExpect(jsonPath("$.payload[0].amount.receiverCurrency", is(response.getPayload()[0].getAmount().getReceiverCurrency())))
                        .andExpect(jsonPath("$.payload[0].cro", is(response.getPayload()[0].getCro())))
                        .andExpect(jsonPath("$.payload[0].orderId", is(response.getPayload()[0].getOrderId())))
                        .andExpect(jsonPath("$.payload[0].date", is(response.getPayload()[0].getDate())))
                        .andExpect(jsonPath("$.payload[0].feeType", is(response.getPayload()[0].getFeeType())));
    }

    @Test
    public void givenValidRequest_whenGETCreateSTCOrder_thenReturnValidData()
            throws Exception {
        Long accountId = 111L;
        CreateSTCOrderResponse response = getSTCOrderResponse();
        String receiverName = "foo";
        String amount = "100";
        String currency = "EUR";
        String description = "Desc";
        String executionDate = "10-10-2018";
        given(
                callerService.createSTCOrder(any(Long.class),any(CreateSTCOrderRequest.class))
        ).willReturn(
                new ResponseEntity<>(response, HttpStatus.OK)
        );
        mvc.perform(get("/createSTCOrder/"+accountId+"/"+ receiverName + "/" + amount +"/" + currency + "/" + description + "/" + executionDate)
                .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status.code", is(response.getStatus().getCode())))
                .andExpect(jsonPath("$.errors", is(response.getErrors())))
                .andExpect(jsonPath("$.payload[0].amount.receiverAmount", is(response.getPayload()[0].getAmount().getReceiverAmount())))
                .andExpect(jsonPath("$.payload[0].amount.exchangeRate", is(response.getPayload()[0].getAmount().getExchangeRate())))
                .andExpect(jsonPath("$.payload[0].amount.receiverCurrency", is(response.getPayload()[0].getAmount().getReceiverCurrency())))
                .andExpect(jsonPath("$.payload[0].cro", is(response.getPayload()[0].getCro())))
                .andExpect(jsonPath("$.payload[0].orderId", is(response.getPayload()[0].getOrderId())))
                .andExpect(jsonPath("$.payload[0].date", is(response.getPayload()[0].getDate())))
                .andExpect(jsonPath("$.payload[0].feeType", is(response.getPayload()[0].getFeeType())));
    }


    @Test
    public void givenValidationErrors_whenPOSTCreateSTCOrder_thenReturnBadRequest()
            throws Exception {
        Long accountId = 111L;
        List<String> errors = new ArrayList<>(Arrays.asList("Validation Error - AccountId not found or not valid"));
        CreateSTCOrderResponse response = getSTCOrderResponse();
        CreateSTCOrderRequest request = new CreateSTCOrderRequest("foo", "100", "EUR", "Desc", "10-10-2018");
        given(
                validationService.validate(any(CreateSTCOrderRequest.class),any(Long.class))
        ).willReturn(
                errors
        );
        mvc.perform(post("/createSTCOrder/"+accountId)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenException_whenPOSTCreateSTCOrder_thenInternalError()
            throws Exception {
        Long accountId = 111L;
        CreateSTCOrderRequest request = new CreateSTCOrderRequest("foo", "100", "EUR", "Desc", "10-10-2018");
        given(
                callerService.createSTCOrder(any(Long.class),any(CreateSTCOrderRequest.class))
        ).willThrow(
                new HTTPException(404)
        );
        mvc.perform(post("/createSTCOrder/"+accountId)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isInternalServerError());
    }
}