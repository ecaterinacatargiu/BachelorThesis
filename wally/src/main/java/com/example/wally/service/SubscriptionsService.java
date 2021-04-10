package com.example.wally.service;

import com.example.wally.domain.Subscription;
import com.example.wally.domain.Transaction;
import com.example.wally.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionsService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionsService(SubscriptionRepository subscriptionRepository)
    {
        this.subscriptionRepository = subscriptionRepository;
    }

    public boolean checkSubscriptionExists(Long id)
    {
        return subscriptionRepository.findById(id).isPresent();
    }

    public List<Subscription> getByName(String name)
    {
        return subscriptionRepository.findByName(name);
    }

    public List<Subscription> getPaid(Boolean paid)
    {
        return subscriptionRepository.findPaid(paid);
    }

    public List<Subscription> getAll()
    {
        return this.subscriptionRepository.findAll();
    }

}
