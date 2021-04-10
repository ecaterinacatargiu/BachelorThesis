package com.example.wally.controller;


import com.example.wally.converter.SimpleUserConverter;
import com.example.wally.domain.SimpleUser;
import com.example.wally.domain.dto.SimpleUserDTO;
import com.example.wally.domain.dto.SimpleUserListDTO;
import com.example.wally.service.SimpleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("simpleUsers")
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
