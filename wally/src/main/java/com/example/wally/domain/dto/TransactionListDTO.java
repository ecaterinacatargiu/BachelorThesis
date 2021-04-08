package com.example.wally.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class TransactionListDTO {

    private List<TransactionDTO> transactions;

    public TransactionListDTO()
    {

    }
    public TransactionListDTO(List<TransactionDTO> set)
    {
        this.transactions = set;
    }

    public List<TransactionDTO> getTransactions()
    {
        return this.transactions;
    }

    public void setTransaction(List<TransactionDTO> DTOS)
    {
        this.transactions = DTOS;
    }

}
