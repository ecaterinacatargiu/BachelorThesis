package com.example.wally.domain.dto;

import com.example.wally.domain.Subscription;
import com.example.wally.domain.Transaction;

import java.util.List;

public class SimpleUserDTO extends BaseEntityDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long balance;
    private List<TransactionDTO> transactionList;
    private List<SubscriptionDTO> subscriptionList;
    private List<GoalDTO> goals;

    public SimpleUserDTO(String firstName, String lastName, String email, String password, Long balance, List<TransactionDTO> transactions, List<SubscriptionDTO> subscriptionList, List<GoalDTO> goals)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.transactionList = transactions;
        this.subscriptionList = subscriptionList;
        this.goals = goals;
    }

    public SimpleUserDTO() {}

    @Override
    public Long getId() {
        return super.getId();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getBalance() {
        return balance;
    }

    public List<TransactionDTO> getTransactionList() {
        return transactionList;
    }

    public List<SubscriptionDTO> getSubscriptionList() {
        return subscriptionList;
    }

    public List<GoalDTO> getGoals() {
        return goals;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setTransactionList(List<TransactionDTO> transactionList) {
        this.transactionList = transactionList;
    }

    public void setSubscriptionList(List<SubscriptionDTO> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    public void setGoals(List<GoalDTO> goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        return "SimpleUserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", transactionList=" + transactionList +
                ", subscriptionList=" + subscriptionList +
                ", goals=" + goals +
                '}';
    }
}
