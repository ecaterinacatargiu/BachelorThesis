package com.example.wally.repository;

import com.example.wally.domain.Subscription;
import com.example.wally.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT sub FROM Subscription sub WHERE sub.subscriptionName=?1")
    List<Subscription> findByName(String subName);

    @Query("SELECT sub FROM Subscription sub WHERE sub.paid=?1")
    List<Subscription> findPaid(Boolean paid);
}
