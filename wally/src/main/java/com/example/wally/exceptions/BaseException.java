package com.example.wally.exceptions;


import com.sun.xml.bind.v2.model.runtime.RuntimeElement;

public class BaseException extends RuntimeException {

    public BaseException(String message)
    {
        super(message);
    }

    public BaseException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BaseException(Throwable cause)
    {
        super(cause);
    }
}
