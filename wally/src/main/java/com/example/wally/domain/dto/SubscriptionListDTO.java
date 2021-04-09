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
public class SubscriptionListDTO {

    private List<SubscriptionDTO> subscriptionList;

    public SubscriptionListDTO()
    {

    }
    public SubscriptionListDTO(List<SubscriptionDTO> set)
    {
        this.subscriptionList = set;
    }

    public List<SubscriptionDTO> getSubscriptions()
    {
        return this.subscriptionList;
    }

    public void setSubscriptions(List<SubscriptionDTO> DTOS)
    {
        this.subscriptionList = DTOS;
    }

}
