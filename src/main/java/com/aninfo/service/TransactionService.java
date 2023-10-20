package com.aninfo.service;

import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transRepository.save(transaction);
    }
    public void save(Transaction transaction) {
        transRepository.save(transaction);
    }
    public Collection<Transaction> getTransactionsForAccount(Long accCbu) {
        return transRepository.findTransactionsByAccCbu(accCbu);
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
