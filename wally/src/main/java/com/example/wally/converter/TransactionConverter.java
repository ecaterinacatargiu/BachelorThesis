package com.example.wally.converter;


import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Transaction;
import com.example.wally.domain.dto.TransactionDTO;
import com.example.wally.repository.SimpleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TransactionConverter implements Converter<Transaction, TransactionDTO> {

    @Autowired
    SimpleUserRepository simpleUserRepository;

    @Override
    public Transaction convertDtoToModel(TransactionDTO dto)
    {
        Transaction newTransaction = new Transaction(simpleUserRepository.findById(dto.getClientID()).orElse(null), dto.getDescription(), dto.getType(), dto.getCategory(), dto.getAmount(), dto.getTransactionDate());
        newTransaction.setID(dto.getId());
        return newTransaction;
    }

    @Override
    public TransactionDTO convertModelToDto(Transaction transaction)
    {
        TransactionDTO newTrans = new TransactionDTO(transaction.getDescription(), transaction.getType(), transaction.getCategory(), transaction.getAmount(), transaction.getTransactionDate());
        newTrans.setId(transaction.getID());
        newTrans.setClientID(transaction.getSimpleUser().getID());
        return newTrans;
    }
}
