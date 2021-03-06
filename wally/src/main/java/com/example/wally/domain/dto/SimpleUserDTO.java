package com.example.wally.domain.dto;

import com.example.wally.converter.GoalConverter;
import com.example.wally.converter.SubscriptionConverter;
import com.example.wally.converter.TransactionConverter;
import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Subscription;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleUserDTO extends BaseEntityDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double balance;
    private List<TransactionDTO> transactionList;
    private List<SubscriptionDTO> subscriptionList;
    private List<GoalDTO> goals;

    private TransactionConverter transactionConverter;
    private SubscriptionConverter subscriptionConverter;
    private GoalConverter goalConverter;

    public SimpleUserDTO(String firstName, String lastName, String email, String password, Double balance, List<TransactionDTO> transactions, List<SubscriptionDTO> subscriptionList, List<GoalDTO> goals)
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

    public Double getBalance() {
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

    public void setBalance(Double balance) {
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

    public SimpleUserDTO toDTO(final SimpleUser user)
    {
        return new SimpleUserDTO(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getBalance(),
                user.getTransactions().stream().map(transactionConverter::convertModelToDto).collect(Collectors.toList()),
                user.getSubscriptions().stream().map(subscriptionConverter::convertModelToDto).collect(Collectors.toList()),
                user.getGoals().stream().map(goalConverter::convertModelToDto).collect(Collectors.toList()));
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
