package com.example.wally.converter;

import com.example.wally.domain.Subscription;
import com.example.wally.domain.dto.SubscriptionDTO;
import com.example.wally.repository.SimpleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionConverter implements Converter<Subscription, SubscriptionDTO>{

    @Autowired
    SimpleUserRepository simpleUserRepository;

    @Override
    public Subscription convertDtoToModel(SubscriptionDTO dto) {
        Subscription newSubscription = new Subscription(simpleUserRepository.findById(dto.getClientID()).orElse(null), dto.getSubscriptionName(), dto.getAmount(), dto.getPaymentDate(), dto.getPaid());
        newSubscription.setId(dto.getId());
        return newSubscription;
    }

    @Override
    public SubscriptionDTO convertModelToDto(Subscription subscription) {
        SubscriptionDTO newSub = new SubscriptionDTO(subscription.getSubscriptionName(), subscription.getAmount(), subscription.getPaymentDate(), subscription.getPaid());
        newSub.setId(subscription.getId());
        newSub.setClientID(subscription.getSimple_user().getID());
        return newSub;
    }
}
