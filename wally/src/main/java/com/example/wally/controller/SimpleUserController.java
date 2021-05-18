package com.example.wally.controller;


import com.example.wally.converter.SimpleUserConverter;
import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.dto.SimpleUserDTO;
import com.example.wally.domain.dto.SimpleUserListDTO;
import com.example.wally.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemEventListener;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("simpleUsers")
@CrossOrigin
public class SimpleUserController {

    @Autowired
    private SimpleUserService simpleUserService;

    @Autowired
    private SimpleUserConverter simpleUserConverter;

    @RequestMapping(method = RequestMethod.GET)
    public SimpleUserListDTO getUsers()
    {
        Iterable<SimpleUser> users = this.simpleUserService.getAll();
        Set<SimpleUserDTO> urs = StreamSupport.stream(users.spliterator(), false).map(simpleUserConverter::convertModelToDto).collect(Collectors.toSet());
        return new SimpleUserListDTO(urs);
    }

    @RequestMapping(path = "/{email}", method = RequestMethod.GET)
    public Long getIdByUser(@PathVariable("email") String email)
    {
        System.out.println("Am ajuns aiki");
        return this.simpleUserService.getIdByEmail(email);
    }

    @RequestMapping(path = "/userId={id}", method = RequestMethod.GET)
    public Double getBalanceByUser(@PathVariable("id") Long id)
    {
        return this.simpleUserService.getBalanceForUser(id);
    }

    @RequestMapping(path="/expenses/userId={id}", method = RequestMethod.GET)
    public Double getTotalExpenses(@PathVariable("id") Long id)
    {
        return this.simpleUserService.getTotalExpense(id);
    }

    @RequestMapping(path="/income/userId={id}", method = RequestMethod.GET)
    public Double getTotalIncome(@PathVariable("id") Long id)
    {
        return this.simpleUserService.getTotalIncome(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public void addUser(@RequestBody SimpleUserDTO userDTO) throws Exception
    {
        this.simpleUserService.addSimpleUser(simpleUserConverter.convertDtoToModel(userDTO));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateUser(@RequestBody SimpleUserDTO userDTO) throws Exception
    {
        this.simpleUserService.editSimpleUser(simpleUserConverter.convertDtoToModel(userDTO));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void removeUser(@PathVariable("id") Long id) throws Exception
    {
        this.simpleUserService.deleteSimpleUser(id);
    }

}
