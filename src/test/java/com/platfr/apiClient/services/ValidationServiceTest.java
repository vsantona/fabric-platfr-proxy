package com.platfr.apiClient.services;

import com.platfr.apiClient.dto.accountBalance.AccountBalanceRequest;
import com.platfr.apiClient.dto.createSTCOrder.CreateSTCOrderRequest;
import com.platfr.apiClient.services.validation.ValidationService;
import config.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class ValidationServiceTest {

    @Autowired
    public ValidationService validationService;

    @MockBean
    ApiClientService apiClientService;

    // ********** Account Balance Test ***************
    @Test
    public void givenValidAccountId_whenValidate_thenReturnNoErrrors()
            throws Exception {
        AccountBalanceRequest request;
        request =  new AccountBalanceRequest(111L);
        List errors = validationService.validate(request);
        assertThat(errors.size(), is(equalTo(0)));
    }

    @Test
    public void givenNotValidAccountId_whenValidate_thenReturnErrrors()
            throws Exception {
        AccountBalanceRequest request;
        request =  new AccountBalanceRequest(0L);
        List errors = validationService.validate(request);
        assertThat(errors.size(), is(equalTo(1)));
    }

    @Test
    public void givenNullAccountId_whenValidate_thenReturnErrrors()
            throws Exception {
        AccountBalanceRequest request;
        request =  new AccountBalanceRequest(null);
        List errors = validationService.validate(request);
        assertThat(errors.size(), is(equalTo(1)));
    }

    // ********** Create Src Order Test ***************
    @Test
    public void givenValidRequest_whenValidate_thenReturnNoErrrors()
            throws Exception {
        CreateSTCOrderRequest request;
        request =  new CreateSTCOrderRequest("test","100","EUR","Desc","10-12-2018");
        List errors = validationService.validate(request, 111L);
        assertThat(errors.size(), is(equalTo(0)));
    }

    @Test
    public void givenRequestWithoutReceiverName_whenValidate_thenReturnErrrors()
            throws Exception {
        CreateSTCOrderRequest request;
        request =  new CreateSTCOrderRequest(null,"100","EUR","Desc","10-12-2018");
        List errors = validationService.validate(request, 111L);
        assertThat(errors.size(), is(equalTo(1)));
    }

    @Test
    public void givenRequestWithoutAmount_whenValidate_thenReturnErrrors()
            throws Exception {
        CreateSTCOrderRequest request;
        request =  new CreateSTCOrderRequest("test",null,"EUR","Desc","10-12-2018");
        List errors = validationService.validate(request, 111L);
        assertThat(errors.size(), is(equalTo(1)));
    }

    @Test
    public void givenRequestWithoutCurrency_whenValidate_thenReturnErrrors()
            throws Exception {
        CreateSTCOrderRequest request;
        request =  new CreateSTCOrderRequest("test","100",null,"Desc","10-12-2018");
        List errors = validationService.validate(request, 111L);
        assertThat(errors.size(), is(equalTo(1)));
    }

    @Test
    public void givenRequestWithoutDescription_whenValidate_thenReturnErrrors()
            throws Exception {
        CreateSTCOrderRequest request;
        request =  new CreateSTCOrderRequest("test","100","EUR",null,"10-12-2018");
        List errors = validationService.validate(request, 111L);
        assertThat(errors.size(), is(equalTo(1)));
    }

    @Test
    public void givenRequestWithoutExecutionDate_whenValidate_thenReturnErrrors()
            throws Exception {
        CreateSTCOrderRequest request;
        request =  new CreateSTCOrderRequest("test","100","EUR","Desc",null);
        List errors = validationService.validate(request, 111L);
        assertThat(errors.size(), is(equalTo(1)));
    }

    @Test
    public void givenRequestWithoutManyRequiredFields_whenValidate_thenManyReturnErrrors()
            throws Exception {
        CreateSTCOrderRequest request;
        request =  new CreateSTCOrderRequest(null,null,"EUR","Desc",null);
        List errors = validationService.validate(request, 111L);
        assertThat(errors.size(), is(equalTo(3)));
    }
}

