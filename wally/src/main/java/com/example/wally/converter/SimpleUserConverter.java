package com.example.wally.converter;

import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.dto.SimpleUserDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SimpleUserConverter implements Converter<SimpleUser, SimpleUserDTO> {

    @Override
    public SimpleUser convertDtoToModel(SimpleUserDTO dto)
    {
        TransactionConverter tcov = new TransactionConverter();
        SubscriptionConverter scov = new SubscriptionConverter();
        GoalConverter gcov = new GoalConverter();
        SimpleUser newSimpleUser = new SimpleUser(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword(), dto.getBalance(),
                dto.getTransactionList().stream().map(tcov::convertDtoToModel).collect(Collectors.toList()), dto.getSubscriptionList().stream().map(scov::convertDtoToModel).collect(Collectors.toList()), dto.getGoals().stream().map(gcov::convertDtoToModel).collect(Collectors.toList()));
        newSimpleUser.setID(dto.getId());
        return newSimpleUser;
    }

    @Override
    public SimpleUserDTO convertModelToDto(SimpleUser simpleUser)
    {
        TransactionConverter tcov = new TransactionConverter();
        SubscriptionConverter scov = new SubscriptionConverter();
        GoalConverter gcov = new GoalConverter();
        SimpleUserDTO newSimpleUserDTO = new SimpleUserDTO(simpleUser.getFirstName(), simpleUser.getLastName(), simpleUser.getEmail(), simpleUser.getPassword(), simpleUser.getBalance(),
                simpleUser.getTransactions().stream().map(tcov::convertModelToDto).collect(Collectors.toList()), simpleUser.getSubscriptions().stream().map(scov::convertModelToDto).collect(Collectors.toList()), simpleUser.getGoals().stream().map(gcov::convertModelToDto).collect(Collectors.toList()));
        newSimpleUserDTO.setId(simpleUser.getID());
        return newSimpleUserDTO;
    }
}
