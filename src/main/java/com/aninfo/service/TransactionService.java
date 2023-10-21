package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionIdException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.TransType;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transRepository;
    @Autowired
    private AccountService accountService;

    public void save(Transaction transaction) {
        transRepository.save(transaction);
    }
    public Collection<Transaction> getTransactionsForAccount(Long accCbu) {
        return transRepository.findTransactionsByAccCbu(accCbu);
    }

    public Double transValueWithPromoApplied(Double value){
        if (value < 2000){
            return value;
        }
        return value + Math.min(value * 0.1, 500);
    }
    public Transaction createDeposit(Transaction transaction){
        Double valWithPromo = transValueWithPromoApplied(transaction.getValue());
        accountService.deposit(transaction.getAccCbu(), valWithPromo);

        transaction.setValue(transValueWithPromoApplied(transaction.getValue()));
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

    public void createTransactionFromValues(Long accCbu, Double value, TransType type){
        Transaction transaction = new Transaction(value, accCbu, type);
        createTransaction(transaction);
    }

    // al borrar una cuenta, deberia borrar sus transacciones asociadas
    public void deleteTransactionsForAccount(Long accCbu) {
        Collection<Transaction> transactions = getTransactionsForAccount(accCbu);
        for (Transaction transaction : transactions) {
            deleteById(transaction.getId());
        }
    }
    public Transaction findById(Long txid) {
        return transRepository.findById(txid).orElseThrow(() -> new InvalidTransactionIdException("No transaction found with that ID"));
    }
    public void deleteById(Long txid) {
        transRepository.deleteById(txid);
    }
}
