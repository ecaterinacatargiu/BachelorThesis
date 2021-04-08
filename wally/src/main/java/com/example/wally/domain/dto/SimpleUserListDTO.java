package com.example.wally.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class SimpleUserListDTO {

    private Set<SimpleUserDTO> users;

    public SimpleUserListDTO()
    {

    }
    public SimpleUserListDTO(Set<SimpleUserDTO> set)
    {
        this.users = set;
    }

    public Set<SimpleUserDTO> getUsers()
    {
        return this.users;
    }

    public void setUsers(Set<SimpleUserDTO> DTOS)
    {
        this.users = DTOS;
    }

}
