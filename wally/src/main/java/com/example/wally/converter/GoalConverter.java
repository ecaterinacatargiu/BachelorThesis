package com.example.wally.converter;

import com.example.wally.domain.Goal;
import com.example.wally.domain.Subscription;
import com.example.wally.domain.dto.GoalDTO;
import com.example.wally.domain.dto.SubscriptionDTO;
import com.example.wally.repository.SimpleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoalConverter implements Converter<Goal, GoalDTO>{

    @Autowired
    SimpleUserRepository simpleUserRepository;

    @Override
    public Goal convertDtoToModel(GoalDTO dto) {
        Goal newGoal = new Goal(simpleUserRepository.findById(dto.getClientID()).orElse(null), dto.getGoalName(), dto.getPrice(), dto.getStartLine(), dto.getDeadline(), dto.getAccomplished());
        newGoal.setId(dto.getId());
        return newGoal;
    }

    @Override
    public GoalDTO convertModelToDto(Goal goal) {
        GoalDTO newGoal = new GoalDTO(goal.getGoalName(), goal.getPrice(), goal.getStartLine(), goal.getDeadline(), goal.getAccomplished());
        newGoal.setId(goal.getId());
        newGoal.setClientID(goal.getSimpleUser().getID());
        return newGoal;
    }
}
