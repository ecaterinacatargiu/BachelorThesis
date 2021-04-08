package com.example.wally.domain.dto;

import com.example.wally.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BaseEntityDTO implements Serializable {

    @JsonProperty
    private Long id;

    public BaseEntityDTO(Long id)
    {
        this.id = id;
    }

    public BaseEntityDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
