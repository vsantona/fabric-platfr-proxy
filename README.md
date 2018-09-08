# APICLientPlatfrTest

These APIs allow to manage the following operations on the account:
 - Balance reading;
 - Transfer;
 
The application uses the APIs exposed by Platfr.io.

 ### TECHNICAL DETAILS

The used technologies are:

- Spring-Boot version 2.0.4
- Java version 1.8

There is the "ApiClientController" controller which manages the two operations.
The Trasfer Operation is exsposed both in GET and in POST (to allow the invocation of the service also from the Browser).

ValidationService checks if the request passed is Valid.

ApiClientService takes care of invoke the APIs exposed by Platfr.io.

The endpoint exposed by Api Client are: 

- /getAccountBalance/{accountId}

- /createSTCOrder/{accountId}/{receiverName}/{amount}/{currency}/{description}/{executionDate}

 ### How To Use

To use the Api client, open the Demo link on Heroku and use the following test links:

Account Balance : https://apiclient.herokuapp.com/getAccountBalance/11111

Create STC Order : https://apiclient.herokuapp.com/createSTCOrder/111111/John Doe/800.00/EUR/Payment invoice 75/2017-26-10

To call createSTCOrder with the POST operation you can use a postman collection test (ApiClientPlatfr.postman_collection.json) present in the project root.


### Demo on Heroku

https://apiclient.herokuapp.com
