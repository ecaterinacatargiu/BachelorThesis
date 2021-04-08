package com.example.wally.service;


import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Transaction;
import com.example.wally.repository.SimpleUserRepository;
import com.example.wally.repository.TransactionRepository;
import javassist.Loader;
import org.apache.naming.TransactionRef;
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

    public boolean checkUserExists(Long id)
    {
        return simpleUserRepository.findById(id).isPresent();
    }

    public void addSimpleUser(SimpleUser simpleUser)
    {
        simpleUserRepository.save(simpleUser);
    }

    public void deleteSimpleUser(Long id)
    {
        if(simpleUserRepository.findById(id).isPresent())
        {
            simpleUserRepository.delete(simpleUserRepository.findById(id).get());
        }
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
    public Transaction addTransaction(Long simpleUserID, String description, Boolean type, String category, Long amount, Date transactionDate) throws Exception
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


    public Transaction updateTransaction(SimpleUser simpleUser, Transaction transaction, String description, Boolean type, String category, Long amount, Date transactionDate) throws Exception {

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

//    public Iterable<Transaction> getAllTransactionWithDescription(String description)
//    {
//        Iterable<SimpleUser> clienti = this.simpleUserRepository.findAllWithTransactionsAndBook();
//        HashSet<Transaction> aleaBune = new HashSet<>();
//        for (SimpleUser client : clienti) {
//            client.getTransactions().stream().filter(x -> x.getDescription() == description).forEach(aleaBune::add);
//        }
//        return aleaBune;
//    }

}
