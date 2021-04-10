package com.example.wally.Scheduler;

import com.example.wally.domain.Subscription;
import com.example.wally.domain.Transaction;
import com.example.wally.repository.SimpleUserRepository;
import com.example.wally.repository.TransactionRepository;
import com.example.wally.service.SimpleUserService;
import com.example.wally.service.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private SubscriptionsService subscriptionsService;

    @Autowired
    private SimpleUserService simpleUserService;

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional
    public void paySubscription() throws Exception {

        System.out.println("A minute has passed");
        List<Subscription> subscriptionList = this.subscriptionsService.getAll();
        Date now = new Date();

        for(Subscription subscription: subscriptionList)
        {
            if(now.compareTo(subscription.getPaymentDate()) > 0 && !subscription.getPaid())
            {
                if(this.simpleUserRepository.findById(subscription.getSimple_user().getID()).isPresent() && subscription.getSimple_user().getBalance() > subscription.getAmount())
                {
                    Transaction transaction = new Transaction();
                    transaction.setSimpleUser(subscription.getSimple_user());
                    transaction.setDescription(subscription.getSubscriptionName());
                    transaction.setType(subscription.getType());
                    transaction.setCategory(subscription.getCategory());
                    transaction.setAmount(subscription.getAmount());
                    transaction.setTransactionDate(subscription.getPaymentDate());

                    subscription.getSimple_user().getTransactions().add(transaction);

                    transactionRepository.save(transaction);
                    subscription.setPaid(true);

                }
                else
                {
                    throw new Exception("No such client to add transaction to or the amount of the transaction exceeds the limits of the user balance. :(");
                }
                System.out.println("transaction added");
            }

            System.out.println("task finished");
        }
    }}
