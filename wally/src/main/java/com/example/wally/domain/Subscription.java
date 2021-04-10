package com.example.wally.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="simple_user_id", referencedColumnName = "_id")
    @JsonIgnore
    private SimpleUser simple_user;

    private String subscriptionName;
    private Boolean type;
    private String category;
    private Long amount;
    private Date paymentDate;
    private Boolean paid;

    public Subscription(SimpleUser simpleUser, String subscriptionName, Long amount, Date paymentDate,  Boolean paid)
    {
        this.simple_user = simpleUser;
        this.subscriptionName = subscriptionName;
        this.type = true;
        this.category ="Extra";
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paid = paid;
    }

    public Subscription(){}

    public Long getId() {
        return Id;
    }

    public SimpleUser getSimple_user() {
        return simple_user;
    }

    public String getSubscriptionName() {
        return subscriptionName;
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setSimple_user(SimpleUser simple_user) {
        this.simple_user = simple_user;

    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
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

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "Id=" + Id +
                ", simple_user=" + simple_user.toString() +
                ", subscriptionName='" + subscriptionName + '\'' +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paid=" + paid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(Id, that.Id) &&
                Objects.equals(simple_user, that.simple_user) &&
                Objects.equals(subscriptionName, that.subscriptionName) &&
                Objects.equals(type, that.type) &&
                Objects.equals(category, that.category) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(paymentDate, that.paymentDate) &&
                Objects.equals(paid, that.paid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, simple_user, subscriptionName, type, category, amount, paymentDate, paid);
    }
}
