package com.example.wally.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class SubscriptionDTO extends BaseEntityDTO {

        private Long clientID;
        private String subscriptionName;
        private Boolean type;
        private String category;
        private Long amount;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        private Date paymentDate;
        private Boolean paid;

        public SubscriptionDTO(String subscriptionName, Long amount, Date paymentDate, Boolean paid) {
            this.subscriptionName = subscriptionName;
            this.type = true;
            this.category = "Extra";
            this.amount = amount;
            this.paymentDate = paymentDate;
            this.paid = paid;
        }

        public SubscriptionDTO() {}

    @Override
    public Long getId() {
        return super.getId();
    }

    public Long getClientID() {
        return clientID;
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

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
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
        return "SubscriptionDTO{" +
                "clientID=" + clientID +
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
        if (!(o instanceof SubscriptionDTO)) return false;
        SubscriptionDTO that = (SubscriptionDTO) o;
        return Objects.equals(clientID, that.clientID) &&
                Objects.equals(subscriptionName, that.subscriptionName) &&
                Objects.equals(type, that.type) &&
                Objects.equals(category, that.category) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(paymentDate, that.paymentDate) &&
                Objects.equals(paid, that.paid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientID, subscriptionName, type, category, amount, paymentDate, paid);
    }
}
