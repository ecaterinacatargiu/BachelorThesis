package com.example.wally.repository;

import com.example.wally.domain.Goal;
import com.example.wally.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @Query("SELECT goal FROM Goal goal WHERE goal.goalName=?1")
    List<Goal> findByName(String goalName);

    @Query("SELECT goal FROM Goal goal WHERE goal.accomplished=?1")
    List<Goal> findAccomplished(Boolean accomplished);
}
