package com.example.wally.validators;

import com.example.wally.exceptions.ValidatorException;


public interface Validator<T>
{
    void validate(T entity) throws ValidatorException;
}