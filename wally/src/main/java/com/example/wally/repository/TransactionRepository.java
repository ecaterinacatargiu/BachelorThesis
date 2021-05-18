package com.example.wally.repository;

import com.example.wally.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT tr FROM Transaction tr WHERE tr.type=?1")
    List<Transaction> findByType(Boolean type);

    @Query("SELECT tr FROM Transaction tr WHERE tr.category=?1")
    List<Transaction> findByCategory(String category);

    @Query("SELECT tr from Transaction tr where tr.simple_user.ID=?1 and tr.id=?2")
    Transaction getTransaction(Long id, Long tid);
}

