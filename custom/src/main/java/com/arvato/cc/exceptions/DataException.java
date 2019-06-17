package com.arvato.cc.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-8-7
 * Time: 下午12:58
 * To change this template use File | Settings | File Templates.
 */
public class DataException extends Exception
{
    private static final long serialVersionUID = 1L;

    private String message;

    public DataException()
    {
        super("data exception");
    }

    public DataException(String message)
    {
        this.message = message;
    }


    public String getMessage()
    {
        return this.message;
    }

}
