package com.example.wally.domain;


import com.example.wally.repository.TransactionRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name="simple_user")
public class SimpleUser extends BaseEntity<Long> implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long balance;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "simple_user")
    @JsonIgnore
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "simple_user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "simple_user")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Goal> goals;

    public SimpleUser(String firstName, String lastName, String email, String password, Long balance, List<Transaction> transactions, List<Subscription> subscriptions, List<Goal> goals)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.transactions = transactions;
        this.subscriptions = subscriptions;
        this.goals = goals;
    }

    public SimpleUser() {}

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

    @Override
    public Long getID() {
        return super.getID();
    }

    public Long getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    @Override
    public void setID(Long ID) {
        super.setID(ID);
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

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    @Transactional
    public Transaction addTransaction(String description, Boolean type, String category, Long amount, Date transactionDate)
    {
        Transaction transaction = new Transaction();
        transaction.setSimpleUser(this);
        transaction.setDescription(description);
        transaction.setType(type);
        transaction.setCategory(category);
        transaction.setAmount(amount);
        transaction.setTransactionDate(transactionDate);

        transactions.add(transaction);

        return transaction;
    }

    @Transactional
    public List<Transaction> removeTransaction(Long simpleUserId, Long transactionId)
    {

        transactions.removeIf(t -> t.getSimpleUser().getID().equals(simpleUserId) && t.getID().equals(transactionId));

        return transactions;
    }

    @Transactional
    public Transaction updateTransaction(SimpleUser simpleUser, Transaction transaction, String description, Boolean type, String category, Long amount, Date transactionDate)
    {
        transactions = transactions.stream().filter(x -> x.getSimpleUser() != simpleUser && x.getID() != transaction.getID()).collect(Collectors.toList());

        Transaction trans = new Transaction();
        trans.setDescription(description);
        trans.setType(type);
        trans.setCategory(category);
        trans.setAmount(amount);
        trans.setTransactionDate(transactionDate);

        transactions.add(trans);

        return trans;
    }

    @Transactional
    public Subscription addSubscription(String subscriptionName, Long ampunt, Date paymentDate, Boolean paid)
    {
        Subscription newSubscription = new Subscription();
        newSubscription.setSimple_user(this);
        newSubscription.setSubscriptionName(subscriptionName);
        newSubscription.setType(true);
        newSubscription.setCategory("Extra");
        newSubscription.setAmount(ampunt);
        newSubscription.setPaymentDate(paymentDate);
        newSubscription.setPaid(paid);

        subscriptions.add(newSubscription);

        return newSubscription;
    }

    @Transactional
    public List<Subscription> removeSubscription(Long simpleUserId, Long subscriptionId)
    {
        subscriptions.removeIf(t -> t.getSimple_user().getID().equals(simpleUserId) && t.getId().equals(subscriptionId));

        return subscriptions;
    }

    @Transactional
    public Subscription updateSubscription(SimpleUser simpleUser, Subscription subscription, String subscriptionName, Long amount, Date paymentDate, Boolean paid)
    {
        subscriptions = subscriptions.stream().filter(x -> x.getSimple_user() != simpleUser && !x.getId().equals(subscription.getId())).collect(Collectors.toList());

        Subscription sub = new Subscription();
        sub.setSubscriptionName(subscriptionName);
        sub.setType(true);
        sub.setCategory("Extra");
        sub.setAmount(amount);
        sub.setPaymentDate(paymentDate);
        sub.setPaid(paid);

        subscriptions.add(sub);

        return sub;
    }

    @Transactional
    public Goal addGoal(String goalName, Long price, Date startLine, Date deadLine, Boolean accomplished)
    {
        Goal newGoal = new Goal();
        newGoal.setSimpleUser(this);
        newGoal.setGoalName(goalName);
        newGoal.setType(true);
        newGoal.setCategory("Extra");
        newGoal.setPrice(price);
        newGoal.setStartLine(startLine);
        newGoal.setDeadline(deadLine);
        newGoal.setAccomplished(accomplished);

        goals.add(newGoal);

        return newGoal;
    }

    @Transactional
    public List<Goal> removeGoal(Long simpleUserId, Long goalId)
    {
        goals.removeIf(t -> t.getSimpleUser().getID().equals(simpleUserId) && t.getId().equals(goalId));

        return goals;
    }

    @Transactional
    public Goal updateGoal(SimpleUser simpleUser, Goal goal, String goalName, Long price, Date startLine, Date deadLine, Boolean accomplished)
    {
        goals = goals.stream().filter(x -> x.getSimpleUser() != simpleUser && !x.getId().equals(goal.getId())).collect(Collectors.toList());

        Goal newGoal = new Goal();
        newGoal.setGoalName(goalName);
        newGoal.setType(true);
        newGoal.setCategory("Wishlist");
        newGoal.setPrice(price);
        newGoal.setStartLine(startLine);
        newGoal.setDeadline(deadLine);
        newGoal.setAccomplished(accomplished);

        goals.add(newGoal);

        return newGoal;
    }

    @Override
    public String toString() {
        return "SimpleUser{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                ", subscriptions=" + subscriptions +
                ", goals=" + goals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleUser)) return false;
        SimpleUser that = (SimpleUser) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(transactions, that.transactions) &&
                Objects.equals(subscriptions, that.subscriptions) &&
                Objects.equals(goals, that.goals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password, balance, transactions, subscriptions, goals);
    }
}
