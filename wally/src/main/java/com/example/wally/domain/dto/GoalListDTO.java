package com.example.wally.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class GoalListDTO {

    private List<GoalDTO> goals;

    public GoalListDTO()
    {

    }
    public GoalListDTO(List<GoalDTO> set)
    {
        this.goals = set;
    }

    public List<GoalDTO> getSubscriptions()
    {
        return this.goals;
    }

    public void setSubscriptions(List<GoalDTO> DTOS)
    {
        this.goals = DTOS;
    }

}
