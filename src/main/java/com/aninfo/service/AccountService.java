package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.InvalidAccountCBUException;
import com.aninfo.exceptions.PreviouslyExistingCBUException;
import com.aninfo.model.Account;
import com.aninfo.model.TransType;
import com.aninfo.model.Transaction;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account findById(Long cbu) {
        return accountRepository.findById(cbu).orElseThrow(() -> new InvalidAccountCBUException("No account found with that CBU"));
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public Account updateAccountCbu(Account account, Long newCbu){
        Optional<Account> accountOptional = accountRepository.findById(newCbu);

        if (accountOptional.isPresent()) {
            throw new PreviouslyExistingCBUException("Cannot change account CBU due to another account with desired CBU already existing");
        }
        deleteById(account.getCbu());
        account.setCbu(newCbu);
        save(account);
        return account;

    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        account.setBalance(account.getBalance() - sum);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }

        Account account = accountRepository.findAccountByCbu(cbu);
        System.out.println(String.format("sum: %.2f", sum));
        account.setBalance(account.getBalance() + sum);
        accountRepository.save(account);

        return account;
    }
}
