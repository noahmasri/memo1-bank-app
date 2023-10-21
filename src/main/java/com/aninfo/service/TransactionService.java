package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.model.TransType;
import com.aninfo.model.Transaction;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transRepository;
    private AccountService accountService;
    
    public void save(Transaction transaction) {
        transRepository.save(transaction);
    }
    public Collection<Transaction> getTransactionsForAccount(Long accCbu) {
        return transRepository.findTransactionsByAccCbu(accCbu);
    }

    public Transaction createDeposit(Transaction transaction){
        // promo
        Double value = transaction.getValue() + Math.min(transaction.getValue() * 0.1, 500);
        transaction.setValue(value);
        accountService.deposit(transaction.getAccCbu(), transaction.getValue());
        return transRepository.save(transaction);
    }

    public Transaction createWithdraw(Transaction transaction){
        accountService.withdraw(transaction.getAccCbu(), transaction.getValue());
        return transRepository.save(transaction);
    }
    public Transaction createTransaction(Transaction transaction){

        switch (transaction.getType()) {
            case DEPOSIT:
                return createDeposit(transaction);
            case WITHDRAW:
                return createWithdraw(transaction);
            default:
                throw new InvalidTransactionTypeException("Transaction types are Deposit or Withdraw");
        }
    }

    // al borrar una cuenta, deberia borrar sus transacciones asociadas
    public void deleteTransactionsForAccount(Long accCbu) {
        Collection<Transaction> transactions = getTransactionsForAccount(accCbu);
        for (Transaction transaction : transactions) {
            deleteById(transaction.getId());
        }
    }
    public Optional<Transaction> findById(Long txid) {
        return transRepository.findById(txid);
    }
    public void deleteById(Long txid) {
        transRepository.deleteById(txid);
    }
}
