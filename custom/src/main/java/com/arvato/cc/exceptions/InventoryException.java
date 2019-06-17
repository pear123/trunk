package com.arvato.cc.exceptions;


public class InventoryException
    extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String message;
    
    public InventoryException()
    {
        super("InventoryException");
    }
    
    public InventoryException(String message)
    {
        this.message = message;
    }


    public String getMessage()
    {
        return this.message;
    }

}
