package com.example.wally.domain.dto;

import com.example.wally.domain.SimpleUser;
import com.example.wally.repository.SimpleUserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

public class TransactionDTO extends BaseEntityDTO{

    private Long clientID;
    private String description;
    private Boolean type;
    private String category;
    private Double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date transactionDate;

    public TransactionDTO(String description, Boolean type, String category, Double amount, Date transactionDate) {

        this.description = description;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public TransactionDTO() {}

    @Override
    public Long getId() {
        return super.getId();
    }

    public Long getClientID() {
        return clientID;
    }


    public String getDescription() {
        return description;
    }

    public Boolean getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "clientID=" + clientID +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
