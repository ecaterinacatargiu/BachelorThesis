package com.example.wally.exceptions;

public class ValidatorException extends BaseException
{
    public ValidatorException(String message)
    {
        super(message);
    }

    public ValidatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ValidatorException(Throwable cause)
    {
        super(cause);
    }
}