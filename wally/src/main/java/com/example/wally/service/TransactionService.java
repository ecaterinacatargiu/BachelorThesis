package com.example.wally.service;

import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Transaction;
import com.example.wally.repository.TransactionRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean checkTransactionExists(Long id)
    {
        return transactionRepository.findById(id).isPresent();
    }
    public Optional<Transaction> getTransactionById(Long id)
    {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getByType(Boolean type)
    {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> getByCategory(String category)
    {
        return transactionRepository.findByCategory(category);
    }

}
