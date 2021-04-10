package com.example.wally.service;

import com.example.wally.domain.Goal;
import com.example.wally.domain.Subscription;
import com.example.wally.repository.GoalRepository;
import com.example.wally.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository)
    {
        this.goalRepository = goalRepository;
    }

    public boolean checkGoalExists(Long id)
    {
        return goalRepository.findById(id).isPresent();
    }

    public List<Goal> getByName(String name)
    {
        return goalRepository.findByName(name);
    }

    public List<Goal> getAccomplished(Boolean paid)
    {
        return goalRepository.findAccomplished(paid);
    }

    public List<Goal> getAll()
    {
        return this.goalRepository.findAll();
    }
}
