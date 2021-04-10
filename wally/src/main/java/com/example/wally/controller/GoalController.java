package com.example.wally.controller;

import com.example.wally.converter.GoalConverter;
import com.example.wally.converter.SubscriptionConverter;
import com.example.wally.domain.Goal;
import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.Subscription;
import com.example.wally.domain.dto.GoalDTO;
import com.example.wally.domain.dto.SubscriptionDTO;
import com.example.wally.repository.GoalRepository;
import com.example.wally.repository.SimpleUserRepository;
import com.example.wally.repository.SubscriptionRepository;
import com.example.wally.service.GoalService;
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
@RequestMapping("goals")
public class GoalController {

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    GoalService goalService;

    @Autowired
    SimpleUserService simpleUserService;

    @Autowired
    SimpleUserRepository simpleUserRepository;

    @Autowired
    GoalConverter goalConverter;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Goal addGoal(@RequestBody GoalDTO goalDTO) throws Exception {

        Goal addedGoal = new Goal();
        addedGoal = this.simpleUserService.addGoal(goalDTO.getClientID(), goalDTO.getGoalName(), goalDTO.getPrice(), goalDTO.getStartLine(), goalDTO.getDeadline(), goalDTO.getAccomplished());

        addedGoal.setId(goalDTO.getId());
        goalRepository.save(addedGoal);

        return addedGoal;
    }

    @Transactional
    @RequestMapping(path="simpleUserId={simpleUserId}&goalId={id}")
    public Goal editGoal(@PathVariable("simpleUserId") Long simpleUserId, @PathVariable("id") Long id, @RequestBody GoalDTO goalDTO) throws Exception {

        SimpleUser user = simpleUserRepository.findById(simpleUserId)
                .orElse(null);

        Goal goal = goalRepository.findById(id)
                .orElse(null);

        Goal editedGoal = new Goal();
        editedGoal = this.simpleUserService.updateGoal(user, goal, goalDTO.getGoalName(), goalDTO.getPrice(), goalDTO.getStartLine(), goalDTO.getDeadline(), goalDTO.getAccomplished());

        editedGoal.setSimpleUser(user);
        editedGoal.setId(id);

        goalRepository.delete(goal);
        goalRepository.save(editedGoal);

        return editedGoal;
    }

    @Transactional
    @RequestMapping(path="simpleUserId={simpleUserId}&goalId={id}", method = RequestMethod.DELETE)
    public List<GoalDTO> deleteGoal(@PathVariable("simpleUserId") Long simpleUserId, @PathVariable("id") Long id)
    {
        List<Goal> newGoalList = new ArrayList<>();
        if(simpleUserService.checkUserExists(simpleUserId) && goalService.checkGoalExists(id))
        {
            newGoalList = simpleUserService.removeGoal(simpleUserId,id);
            goalRepository.delete(Objects.requireNonNull(this.goalRepository.findById(id).orElse(null)));
        }
        return newGoalList.stream().map(goalConverter::convertModelToDto).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path="userId={userId}", method = RequestMethod.GET)
    public List<GoalDTO> getGoalsForUser(@PathVariable("userId") Long id) throws Exception
    {
        SimpleUser myClient = this.simpleUserRepository.findById(id).orElse(null);
        List<Goal> goals = new ArrayList<>();

        if(myClient !=null)
        {
            goals = this.simpleUserService.getGoalsByUser(id);
        }
        else
        {
            throw new Exception("Client not found. :(");
        }

        return goals.stream().map(goalConverter::convertModelToDto).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<GoalDTO> getGoals()
    {
        Iterable<SimpleUser> clienti = this.simpleUserService.getAll();
        List<Goal> toate = new ArrayList<>();

        clienti.forEach(x-> toate.addAll(x.getGoals()));

        return toate.stream().map(goalConverter::convertModelToDto).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path="goalNme={subName}", method = RequestMethod.GET)
    public List<GoalDTO> getGoalsByName(@PathVariable("subName") String name) throws Exception
    {
        List<Goal> goals = new ArrayList<>();
        goals = goalService.getByName(name);

        return goals.stream().map(goalConverter::convertModelToDto).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path="isAccomplished={accomplished}", method = RequestMethod.GET)
    public List<GoalDTO> getGoalsAccomplished(@PathVariable("accomplished") Boolean isAccomplished) throws Exception
    {
        List<Goal> goals = new ArrayList<>();
        goals = goalService.getAccomplished(isAccomplished);

        return goals.stream().map(goalConverter::convertModelToDto).collect(Collectors.toList());
    }


}
