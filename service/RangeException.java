package pl.polsl.lab.service;

/**
 * Exception class for object thrown when attempting to create palindrome
 * 
 * @author Dawid Kampa
 * @version 1.1
 */
public class RangeException extends Exception{
    
    /**
     * Non-parameter constructor
     */
    public RangeException(){
    }
        /**
     * Constructor
     *
     * @param message display message
     */
    public RangeException(String message){
        super(message);
    }
}