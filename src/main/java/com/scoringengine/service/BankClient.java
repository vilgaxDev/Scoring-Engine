package com.scoringengine.service;

import io.credable.cbs.customer.*;
import io.credable.cbs.transaction.*;
import org.springframework.stereotype.Service;

import javax.xml.ws.BindingProvider;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;


@Service
public class BankClient {


    public List<TransactionData> getTransactions(String customerNumber) {
        io.credable.cbs.transaction.ObjectFactory factory = new io.credable.cbs.transaction.ObjectFactory();
        TransactionsRequest request = factory.createTransactionsRequest();
        request.setCustomerNumber(customerNumber);
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "pwd123".toCharArray());
            }
        });
        TransactionDataPortService transactionDataPortService = new TransactionDataPortService();
        TransactionDataPort transactionPort = transactionDataPortService.getTransactionDataPortSoap11();
        BindingProvider provider = (BindingProvider) transactionPort;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"admin");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,"pwd123");
        List<TransactionData> response = transactionPort.transactions(request).getTransactions();
        return response;
    }


    public Customer getCustomer(String customerNumber) {
        io.credable.cbs.customer.ObjectFactory factory = new io.credable.cbs.customer.ObjectFactory();
        io.credable.cbs.customer.CustomerRequest request = factory.createCustomerRequest();
        request.setCustomerNumber(customerNumber);
        CustomerPortService customerPortService = new CustomerPortService();
        CustomerPort customerPort = customerPortService.getCustomerPortSoap11();

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "pwd123".toCharArray());
            }
        });
        BindingProvider provider = (BindingProvider) customerPort;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "admin");
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "pwd123");
        io.credable.cbs.customer.CustomerResponse response = customerPort.customer(request);
        return response.getCustomer();
    }


}

