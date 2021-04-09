package com.example.wally.controller;


import com.example.wally.converter.SubscriptionConverter;
import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Subscription;
import com.example.wally.domain.Transaction;
import com.example.wally.domain.dto.SubscriptionDTO;
import com.example.wally.domain.dto.TransactionDTO;
import com.example.wally.repository.SimpleUserRepository;
import com.example.wally.repository.SubscriptionRepository;
import com.example.wally.service.SimpleUserService;
import com.example.wally.service.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("subscriptions")
public class SubscriptionsController {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    SubscriptionsService subscriptionsService;

    @Autowired
    SimpleUserService simpleUserService;

    @Autowired
    SimpleUserRepository simpleUserRepository;

    @Autowired
    SubscriptionConverter subscriptionConverter;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Subscription addSubscription(@RequestBody SubscriptionDTO subscriptionDTO) throws Exception {

        Subscription addedSubscription = new Subscription();
        addedSubscription = this.simpleUserService.addSubscription(subscriptionDTO.getClientID(), subscriptionDTO.getSubscriptionName(), subscriptionDTO.getAmount(), subscriptionDTO.getPaymentDate(), subscriptionDTO.getPaid());

        addedSubscription.setId(subscriptionDTO.getId());
        subscriptionRepository.save(addedSubscription);

        return addedSubscription;
    }

    @Transactional
    @RequestMapping(path="simpleUserId={simpleUserId}&subscriptionId={id}")
    public Subscription editTransaction(@PathVariable("simpleUserId") Long simpleUserId, @PathVariable("id") Long id, @RequestBody SubscriptionDTO subscriptionDTO) throws Exception {

        SimpleUser user = simpleUserRepository.findById(simpleUserId)
                .orElse(null);

        Subscription sub = subscriptionRepository.findById(id)
                .orElse(null);

        Subscription editedSub = new Subscription();
        editedSub = this.simpleUserService.updateSubscription(user, sub, subscriptionDTO.getSubscriptionName(), subscriptionDTO.getAmount(), subscriptionDTO.getPaymentDate(), subscriptionDTO.getPaid());

        editedSub.setSimple_user(user);
        editedSub.setId(id);

        subscriptionRepository.delete(sub);
        subscriptionRepository.save(editedSub);


        return editedSub;
    }


    @Transactional
    @RequestMapping(path="simpleUserId={simpleUserId}&transactionId={id}", method = RequestMethod.DELETE)
    public List<SubscriptionDTO> deleteTransaction(@PathVariable("simpleUserId") Long simpleUserId, @PathVariable("id") Long id)
    {
        List<Subscription> newSubList = new ArrayList<>();
        if(simpleUserService.checkUserExists(simpleUserId) && subscriptionsService.checkSubscriptionExists(id))
        {
            newSubList = simpleUserService.removeSubscription(simpleUserId,id);
            subscriptionRepository.delete(Objects.requireNonNull(this.subscriptionRepository.findById(id).orElse(null)));
        }
        return newSubList.stream().map(subscriptionConverter::convertModelToDto).collect(Collectors.toList());
    }
}