package com.aninfo.integration.cucumber;

import com.aninfo.Memo1BankApp;
import com.aninfo.model.Account;
import com.aninfo.model.TransType;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = Memo1BankApp.class)
@WebAppConfiguration
public class AccountIntegrationServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    Account createAccount(Double balance) {
        return accountService.createAccount(new Account(balance));
    }

    Account withdraw(Account account, Double sum) {
        transactionService.createTransactionFromValues(account.getCbu(), sum, TransType.WITHDRAW);
        return accountService.findById(account.getCbu());
    }

    Account deposit(Account account, Double sum) {
        transactionService.createTransactionFromValues(account.getCbu(), sum, TransType.DEPOSIT);
        return accountService.findById(account.getCbu());
    }

}
