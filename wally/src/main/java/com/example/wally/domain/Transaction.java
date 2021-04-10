package com.example.wally.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import javassist.Loader;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="transaction")
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    //@NotNull
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="simple_user_id", referencedColumnName = "_id")
    @JsonIgnore
    private SimpleUser simple_user;

    private String description;
    private Boolean type;
    private String category;
    private Long amount;
    private Date transactionDate;

    public Transaction(SimpleUser simpleUser, String description, Boolean type, String category, Long amount, Date transactionDate)
    {
        this.simple_user = simpleUser;
        this.description = description;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction() {

        this.simple_user = null;
        this.description = null;
        this.type = null;
        this.category = null;
        this.amount = null;
        this.transactionDate = null;
    }


    public Long getID() {
        return id;
    }

    public SimpleUser getSimpleUser() {
        return simple_user;
    }

    public Long getSimpleUserId()
    {
        return  simple_user.getID();
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

    public Long getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setSimpleUser(SimpleUser simpleUserID) {
        this.simple_user = simpleUserID;
    }

    public void setID(Long id) {
        this.id = id;
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

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", simple_user=" + simple_user.toString() +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(simple_user, that.simple_user) &&
                Objects.equals(description, that.description) &&
                Objects.equals(type, that.type) &&
                Objects.equals(category, that.category) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simple_user, description, type, category, amount, transactionDate);
    }
}
