package com.routeone.interview.exception;

public class ItemNotFoundException extends Exception
{
    private static final long serialVersionUID = 1L;

    //Parameterless Constructor
    public ItemNotFoundException() {}

    //Constructor that accepts a message
    public ItemNotFoundException(String message)
    {
       super(message);
    }
}
