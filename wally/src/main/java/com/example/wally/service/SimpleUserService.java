package com.example.wally.service;


import com.example.wally.domain.Goal;
import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Subscription;
import com.example.wally.domain.Transaction;
import com.example.wally.repository.SimpleUserRepository;
import com.example.wally.repository.TransactionRepository;
import javassist.Loader;
import org.apache.naming.TransactionRef;
import org.aspectj.util.IStructureModel;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.dsig.SignatureMethod;
import java.util.*;

@Service
public class SimpleUserService {

    private static final Logger log = LoggerFactory.getLogger(SimpleUserService.class);


    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public SimpleUserService(SimpleUserRepository simpleUserRepository, TransactionRepository transactionRepository)
    {
        this.simpleUserRepository = simpleUserRepository;
        this.transactionRepository = transactionRepository;
    }

    public Optional<SimpleUser> getUserById(Long id)
    {
        return simpleUserRepository.findById(id);
    }

    public Optional<SimpleUser> getUserByEmail(String email)
    {
        return simpleUserRepository.findByEmail(email);
    }

    public SimpleUser lookUpUser(String email)
    {
        return simpleUserRepository.findByEmail(email).orElse(null);
    }

    public boolean chechUserByEmail(String email)
    {
        return simpleUserRepository.findByEmail(email).isPresent();
    }

    public boolean checkUserExists(Long id)
    {
        return simpleUserRepository.findById(id).isPresent();
    }

    public void addSimpleUser(SimpleUser simpleUser)
    {
        if(simpleUser.getTransactions() == null)
        {
            simpleUser.setTransactions(Collections.emptyList());
        }
        if(simpleUser.getSubscriptions() == null)
        {
            simpleUser.setSubscriptions(Collections.emptyList());
        }
        if(simpleUser.getGoals() == null)
        {
            simpleUser.setGoals(Collections.emptyList());
        }
        if(simpleUser.getBalance() == null)
        {
            simpleUser.setBalance(0.0);
        }
        simpleUserRepository.save(simpleUser);
    }

    public void deleteSimpleUser(Long id)
    {
        if(simpleUserRepository.findById(id).isPresent())
        {
            simpleUserRepository.delete(simpleUserRepository.findById(id).get());
        }
    }

    public SimpleUser getUserAfterValidation(String email)
    {
        if(null == email)
        {
            throw new ServiceException("[ERROR] Null email address given");
        }
        Optional<SimpleUser> opUser = this.simpleUserRepository.findByEmail(email);
        if(opUser.isEmpty())
        {
            throw new ServiceException("[ERROR] No such user exists.");
        }
        return opUser.get();
    }

    public void editSimpleUser(SimpleUser simpleUser)
    {

        if(simpleUser.getFirstName() == null || simpleUser.getFirstName().strip().equals(""))
        {
            if(checkUserExists(simpleUser.getID()))
            {
                SimpleUser old = this.getUserById(simpleUser.getID()).get();
                simpleUser.setFirstName(old.getFirstName());
            }
        }

        if(simpleUser.getLastName() == null || simpleUser.getLastName().strip().equals(""))
        {
            if(checkUserExists(simpleUser.getID()))
            {
                SimpleUser old = this.getUserById(simpleUser.getID()).get();
                simpleUser.setLastName(old.getLastName());
            }
        }

        if(simpleUser.getEmail() == null || simpleUser.getEmail().strip().equals(""))
        {
            if(checkUserExists(simpleUser.getID()))
            {
                SimpleUser old = this.getUserById(simpleUser.getID()).get();
                simpleUser.setEmail(old.getEmail());
            }
        }

        if(simpleUser.getPassword() == null || simpleUser.getPassword().strip().equals(""))
        {
            if(checkUserExists(simpleUser.getID()))
            {
                SimpleUser old = this.getUserById(simpleUser.getID()).get();
                simpleUser.setPassword(old.getPassword());
            }
        }

        if(simpleUser.getBalance() == null)
        {
            if(checkUserExists(simpleUser.getID()))
            {
                SimpleUser old = this.getUserById(simpleUser.getID()).get();
                simpleUser.setBalance(old.getBalance());

                simpleUserRepository.delete(old);

            }
        }
        simpleUserRepository.save(simpleUser);
    }

    public List<SimpleUser> getAll()
    {
        return simpleUserRepository.findAll();
    }


    @Transactional
    public Transaction addTransaction(Long simpleUserID, String description, Boolean type, String category, Double amount, Date transactionDate) throws Exception
    {
        Transaction res;
        if(this.simpleUserRepository.findById(simpleUserID).isPresent())
        {
            res = this.simpleUserRepository.findById(simpleUserID).get().addTransaction(description, type, category, amount, transactionDate);
            if(type)
            {
                if(this.simpleUserRepository.findById(simpleUserID).get().getBalance() > amount)
                {
                    this.simpleUserRepository.findById(simpleUserID).get().setBalance(this.simpleUserRepository.findById(simpleUserID).get().getBalance() - amount);
                }
                else
                {
                    throw new Exception("Not enough money.");
                }
            }
            else
            {
                this.simpleUserRepository.findById(simpleUserID).get().setBalance(this.simpleUserRepository.findById(simpleUserID).get().getBalance() + amount);
            }
        }
        else
        {
            throw new Exception("No such client to add transaction to or the amount of the transaction exceeds the limits of the user balance. :(");
        }
        return res;
    }

    @Transactional
    public List<Transaction> removeTransaction(Long simpleUserId, Long transactionId)
    {
        List<Transaction> newTransList = this.simpleUserRepository.findById(simpleUserId).get().removeTransaction(simpleUserId, transactionId);
        if(this.transactionRepository.findById(transactionId).get().getType())
        {
            this.simpleUserRepository.findById(simpleUserId).get().setBalance(this.simpleUserRepository.findById(simpleUserId).get().getBalance() + this.transactionRepository.findById(transactionId).get().getAmount());
        }
        else
        {
            this.simpleUserRepository.findById(simpleUserId).get().setBalance(this.simpleUserRepository.findById(simpleUserId).get().getBalance() - this.transactionRepository.findById(transactionId).get().getAmount());
        }
        return newTransList;
    }

    @Transactional
    public Transaction updateTransaction(SimpleUser simpleUser, Transaction transaction, String description, Boolean type, String category, Double amount, Date transactionDate) throws Exception {

        Transaction res;
        if(this.simpleUserRepository.findById(simpleUser.getID()).isPresent())
        {
            res = this.simpleUserRepository.findById(simpleUser.getID()).get().updateTransaction(simpleUser, transaction, description, type, category, amount, transactionDate);
            if(type)
            {
                if(this.simpleUserRepository.findById(simpleUser.getID()).get().getBalance() > amount)
                {
                    this.simpleUserRepository.findById(simpleUser.getID()).get().setBalance(this.simpleUserRepository.findById(simpleUser.getID()).get().getBalance() - amount);
                }
                else
                {
                    throw new Exception("Not enough money.");
                }
            }
            else
            {
                this.simpleUserRepository.findById(simpleUser.getID()).get().setBalance(this.simpleUserRepository.findById(simpleUser.getID()).get().getBalance() + amount);
            }
        }
        else
        {
            throw new Exception("No such entity to update the transaction.");
        }

        return res;
    }

    @Transactional
    public List<Transaction> getTransactionsByUser(Long id) throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        if(this.checkUserExists(id))
        {
            transactions =  this.getUserById(id).get().getTransactions();
        }
        else
        {
            throw new Exception("The user does not exist.");
        }
        return transactions;
    }

    @Transactional
    public List<Subscription> getSubscriptionsByUser(Long id) throws Exception {
        List<Subscription> subscriptions = new ArrayList<>();
        if(this.checkUserExists(id))
        {
            subscriptions =  this.getUserById(id).get().getSubscriptions();
        }
        else
        {
            throw new Exception("The user does not exist.");
        }
        return subscriptions;
    }

    @Transactional
    public List<Subscription> removeSubscription(Long simpleUserId, Long subscriptionId)
    {
        List<Subscription> newSubList = this.simpleUserRepository.findById(simpleUserId).get().removeSubscription(simpleUserId, subscriptionId);

        return newSubList;
    }

    @Transactional
    public Subscription addSubscription(Long simpleUserID, String subscriptionName, Double amount, Date paymentDate, Boolean paid) throws Exception
    {
        Subscription res;
        if(this.simpleUserRepository.findById(simpleUserID).isPresent())
        {
            res = this.simpleUserRepository.findById(simpleUserID).get().addSubscription(subscriptionName, amount, paymentDate, paid);
        }
        else
        {
            throw new Exception("No such client to add subscription to or the amount of the subscription exceeds the limits of the user balance. :(");
        }
        return res;
    }


    @Transactional
    public Subscription updateSubscription(SimpleUser simpleUser, Subscription subscription, String subscriptionName, Double amount, Date paymentDate, Boolean paid) throws Exception {

        Subscription res;
        if(this.simpleUserRepository.findById(simpleUser.getID()).isPresent())
        {
            res = this.simpleUserRepository.findById(simpleUser.getID()).get().updateSubscription(simpleUser, subscription, subscriptionName, amount, paymentDate, paid);
        }
        else
        {
            throw new Exception("No such entity to update the goal to.");
        }
        return res;
    }

    @Transactional
    public Goal addGoal(Long simpleUserID, String goalName, Double price, Date startLine, Date deadLine, Boolean accomplished) throws Exception
    {
        Goal res;
        if(this.simpleUserRepository.findById(simpleUserID).isPresent())
        {
            res = this.simpleUserRepository.findById(simpleUserID).get().addGoal(goalName, price, startLine, deadLine, accomplished);
        }
        else
        {
            throw new Exception("No such client to add goal to :(");
        }
        return res;
    }

    @Transactional
    public List<Goal> removeGoal(Long simpleUserId, Long goalId)
    {
        List<Goal> newGoalList = this.simpleUserRepository.findById(simpleUserId).get().removeGoal(simpleUserId, goalId);

        return newGoalList;
    }

    @Transactional
    public Goal updateGoal(SimpleUser simpleUser, Goal goal, String goalName, Double price, Date startLine, Date deadLine, Boolean accomplished) throws Exception {

        Goal res;
        if(this.simpleUserRepository.findById(simpleUser.getID()).isPresent())
        {
            res = this.simpleUserRepository.findById(simpleUser.getID()).get().updateGoal(simpleUser, goal, goalName, price, startLine, deadLine, accomplished);
        }
        else
        {
            throw new Exception("No such entity to update the goal to.");
        }
        return res;
    }

    @Transactional
    public List<Goal> getGoalsByUser(Long id) throws Exception {
        List<Goal> goals = new ArrayList<>();
        if(this.checkUserExists(id))
        {
            goals =  this.getUserById(id).get().getGoals();
        }
        else
        {
            throw new Exception("The user does not exist.");
        }
        return goals;
    }

}
