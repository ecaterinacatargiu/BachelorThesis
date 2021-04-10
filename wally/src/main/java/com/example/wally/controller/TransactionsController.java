package com.example.wally.controller;


import com.example.wally.converter.TransactionConverter;
import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Transaction;
import com.example.wally.domain.dto.TransactionDTO;
import com.example.wally.repository.SimpleUserRepository;
import com.example.wally.repository.TransactionRepository;
import com.example.wally.service.SimpleUserService;
import com.example.wally.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("system")
public class TransactionsController {

    private static final Logger log = LoggerFactory.getLogger(TransactionsController.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SimpleUserRepository simpleUserRepository;

    @Autowired
    private SimpleUserService simpleUserService;

    @Autowired
    private TransactionConverter transactionConverter;

    @Autowired
    private TransactionService transactionService;


    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Transaction addTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {

        Transaction addedTransation = new Transaction();
        addedTransation = this.simpleUserService.addTransaction(transactionDTO.getClientID(), transactionDTO.getDescription(), transactionDTO.getType(), transactionDTO.getCategory(), transactionDTO.getAmount(), transactionDTO.getTransactionDate());

        addedTransation.setID(transactionDTO.getId());
        transactionRepository.save(addedTransation);

        return addedTransation;
    }

    @Transactional
    @RequestMapping(path="simpleUserId={simpleUserId}&transactionId={id}")
    public Transaction editTransaction(@PathVariable("simpleUserId") Long simpleUserId, @PathVariable("id") Long id, @RequestBody TransactionDTO transactionDTO) throws Exception {

        SimpleUser user = simpleUserRepository.findById(simpleUserId)
                .orElse(null);

        Transaction tran = transactionRepository.findById(id)
                .orElse(null);

        Transaction editedTrans = new Transaction();
        editedTrans = this.simpleUserService.updateTransaction(user, tran, transactionDTO.getDescription(), transactionDTO.getType(), transactionDTO.getCategory(), transactionDTO.getAmount(), transactionDTO.getTransactionDate());

        editedTrans.setSimpleUser(user);
        editedTrans.setID(id);

        transactionRepository.delete(tran);
        transactionRepository.save(editedTrans);

        return editedTrans;
    }

    @Transactional
    @RequestMapping(path="simpleUserId={simpleUserId}&transactionId={id}", method = RequestMethod.DELETE)
    public List<TransactionDTO> deleteTransaction(@PathVariable("simpleUserId") Long simpleUserId, @PathVariable("id") Long id)
    {
        List<Transaction> newTransList = new ArrayList<>();
        if(simpleUserService.checkUserExists(simpleUserId) && transactionService.checkTransactionExists(id))
        {
            newTransList = simpleUserService.removeTransaction(simpleUserId,id);
            transactionRepository.delete(Objects.requireNonNull(this.transactionRepository.findById(id).orElse(null)));

        }

         return newTransList.stream().map(transactionConverter::convertModelToDto).collect(Collectors.toList());
    }


    @Transactional
    @RequestMapping(path="userId={userId}", method = RequestMethod.GET)
    public List<TransactionDTO> getTransactionForUser(@PathVariable("userId") Long id) throws Exception
    {
        SimpleUser myClient = this.simpleUserRepository.findById(id).orElse(null);
        List<Transaction> trans = new ArrayList<>();

        if(myClient !=null)
        {
            trans = this.simpleUserService.getTransactionsByUser(id);
        }
        else
        {
            throw new Exception("Client not found. :(");
        }

        return trans.stream().map(transactionConverter::convertModelToDto).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path="type={transactionType}", method = RequestMethod.GET)
    public List<TransactionDTO> getTransactionsByType(@PathVariable("transactionType") Boolean type) throws Exception
    {
        List<Transaction> trans = new ArrayList<>();
        trans = transactionService.getByType(type);

        return trans.stream().map(transactionConverter::convertModelToDto).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path="category={cat}", method = RequestMethod.GET)
    public List<TransactionDTO> getTransactionsByCategory(@PathVariable("cat") String cat) throws Exception
    {
        List<Transaction> trans = new ArrayList<>();
        trans = transactionService.getByCategory(cat);

        return trans.stream().map(transactionConverter::convertModelToDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TransactionDTO> getTransactions()
    {
        Iterable<SimpleUser> clienti = this.simpleUserService.getAll();
        List<Transaction> toate = new ArrayList<>();

        clienti.forEach(x-> toate.addAll(x.getTransactions()));

        return toate.stream().map(transactionConverter::convertModelToDto).collect(Collectors.toList());
    }



}
